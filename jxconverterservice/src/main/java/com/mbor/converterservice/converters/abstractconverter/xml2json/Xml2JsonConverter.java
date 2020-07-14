package com.mbor.converterservice.converters.abstractconverter.xml2json;

import com.mbor.converterservice.components.AbstractNode;
import com.mbor.converterservice.components.Node;
import com.mbor.converterservice.components.NodeList;
import com.mbor.converterservice.components.ValueObject.AbstractValueObject;
import com.mbor.converterservice.converters.abstractconverter.AbstractConverter;
import com.mbor.converterservice.converters.abstractconverter.InputExtractionResult;
import com.mbor.converterservice.converters.abstractconverter.xml2json.valueobjects.JsonEmptyValueObject;
import com.mbor.converterservice.converters.abstractconverter.xml2json.valueobjects.JsonNullValueObject;
import com.mbor.converterservice.converters.abstractconverter.xml2json.valueobjects.JsonValueObject;
import com.mbor.converterservice.factories.nodes.NodeFactory;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Xml2JsonConverter extends AbstractConverter<XmlInputExtractionResult> {

    private static final String XML_ONE_LINE = "(<(.+)\\s?([^<>].+?)?>)(.+?)?<\\/\\2>|(<([\\w\\d]+)\\s?((?=\\s)(.+?))?\\s?\\/>?)";
    private static final String ELEMENT_ATTRIBUTES = "([\\w]+)\\s?=\\s?([\\w\\\"]+)";

    private static int currentIndentation = 0;
    private static int indentationOffset = 4;

    public Xml2JsonConverter(NodeFactory nodeFactory) {
        super(nodeFactory);
    }

    public static int getCurrentIndentation() {
        return currentIndentation;
    }

    public static void incrementCurrentIndentation() {
        Xml2JsonConverter.currentIndentation += indentationOffset;
    }

    public static void decrementCurrentIndentation() {
        Xml2JsonConverter.currentIndentation -= indentationOffset;
    }

    @Override
    public String convert(String input) {
        AbstractNode resultTree = prepareStructure(trimInput(input));
        return prepareComponentNode(resultTree).print();
    }

    private AbstractNode prepareStructure(String input) {
        String elementName;
        AbstractValueObject valueObject;
        List<XmlInputExtractionResult> resultList = extractInput(input);
        if (isInputExtractionResultTheSameLevelList(resultList)) {
            elementName = "root";
            NodeList nodeList = getNodeFactory().getNodeList(elementName);
            resultList.forEach(element ->
                    nodeList.addAbstractElement(prepareStructure(element.getWholeLine()))
            );
            return nodeList;
        } else {
            XmlInputExtractionResult result = resultList.get(0);
            elementName = result.getName();
            if (isInputExtractionResultLeaf(result)) {
                Node node;
                valueObject = prepareValueObject(result);
                if (result.getAttributes().isPresent()) {
                    Map<String, String> attributes = new LinkedHashMap<>(getElementAttributes(result.getAttributes().get(), ELEMENT_ATTRIBUTES));
                    node = getNodeFactory().getNodeWithAttributes(elementName, valueObject);
                    node.setAttributes(attributes);
                    return node;
                } else {
                    node = getNodeFactory().getNode(elementName, valueObject);
                    return node;
                }
            } else {
                String extractedValue = result.getValue().get();
                NodeList nodeList;
                AbstractNode abstractNode = prepareStructure(extractedValue);
                if (abstractNode instanceof NodeList) {
                    nodeList = (NodeList) abstractNode;
                    nodeList.setNodeName(elementName);
                    if (isListWithEqualElement(nodeList.getList())) {
                        if (result.getAttributes().isPresent()) {
                            Map<String, String> attributes = new LinkedHashMap<>(getElementAttributes(result.getAttributes().get(), ELEMENT_ATTRIBUTES));
                            nodeList = getNodeFactory().getEqualNodeListWithAttributes(nodeList);
                            nodeList.setAttributes(attributes);
                        } else {
                            nodeList = getNodeFactory().getEqualNodeList(nodeList);
                        }
                    } else {
                        if (result.getAttributes().isPresent()) {
                            Map<String, String> attributes = new LinkedHashMap<>(getElementAttributes(result.getAttributes().get(), ELEMENT_ATTRIBUTES));
                            nodeList = getNodeFactory().getNodeListWithAttributes(nodeList);
                            nodeList.setAttributes(attributes);
                        }
                    }
                } else {
                    if (result.getAttributes().isPresent()) {
                        Map<String, String> attributes = new LinkedHashMap<>(getElementAttributes(result.getAttributes().get(), ELEMENT_ATTRIBUTES));
                        nodeList = getNodeFactory().getNodeListWithAttributes(elementName);
                        nodeList.setAttributes(attributes);
                    } else {
                        nodeList = getNodeFactory().getNodeList(elementName);
                    }
                    nodeList.addAbstractElement(abstractNode);
                }
                return nodeList;
            }
        }
    }


    protected List<XmlInputExtractionResult> extractInput(String input) {
        Pattern elementNamePattern = Pattern.compile(XML_ONE_LINE);
        Matcher elementNameMatcher = elementNamePattern.matcher(input);
        List<XmlInputExtractionResult> resultList = new LinkedList<>();
        if (!elementNameMatcher.find()) {
            throw new RuntimeException("Invalid input line: " + input);
        }
        elementNameMatcher.reset();
        while (elementNameMatcher.find()) {
            XmlInputExtractionResult xmlInputExtractionResult = new XmlInputExtractionResult();
            xmlInputExtractionResult.setWholeLine(elementNameMatcher.group(0));
            if (elementNameMatcher.group(5) == null) {
                xmlInputExtractionResult.setName(elementNameMatcher.group(2));
                if (elementNameMatcher.group(3) != null) {
                    xmlInputExtractionResult.setAttributes(elementNameMatcher.group(3));
                }
                if (elementNameMatcher.group(4) != null) {
                    xmlInputExtractionResult.setValue(elementNameMatcher.group(4));
                } else {
                    xmlInputExtractionResult.setValue("");
                }
            } else {
                xmlInputExtractionResult.setName(elementNameMatcher.group(6));
                if (elementNameMatcher.group(7) != null) {
                    xmlInputExtractionResult.setAttributes(elementNameMatcher.group(7));
                }
            }
            resultList.add(xmlInputExtractionResult);
        }
        return resultList;
    }

    private Map<String, String> getElementAttributes(String input, String regexp) {
        Map<String, String> attributes;
        Pattern attributesPattern = Pattern.compile(regexp);
        Matcher attributesMatcher = attributesPattern.matcher(input);
        attributes = new LinkedHashMap<>();
        try {
            while (attributesMatcher.find()) {
                attributes.put(attributesMatcher.group(1), attributesMatcher.group(2).replace("\"", ""));
            }
        } catch (Exception e) {
            throw new RuntimeException("Enexpected error during processing attributtes:" + e.getMessage());
        }
        return attributes;
    }

    private String trimInput(String input) {
        return input.replaceAll("\\n", "").replaceAll("\\s{2,}", "");
    }

    @Override
    protected boolean isInputExtractionResultLeaf(InputExtractionResult inputExtractionResult) {
        if (!(inputExtractionResult instanceof XmlInputExtractionResult)) {
            throw new RuntimeException("Result should be XmlInputExtractionResult");
        }
        XmlInputExtractionResult xmlInputExtractionResult = (XmlInputExtractionResult) inputExtractionResult;
        String[] xmlSigns = {"<", ">", "/"};
        if (xmlInputExtractionResult.getValue().isPresent()) {
            String value = xmlInputExtractionResult.getValue().get();
            if (value.isEmpty()) {
                return true;
            } else {
                return Arrays.stream(xmlSigns).noneMatch(value::contains);
            }
        } else {
            return true;
        }
    }

    private boolean isListWithEqualElement(List<AbstractNode> resultList) {
        List<String> nodeNames = resultList.stream().map(AbstractNode::getNodeName).collect(Collectors.toList());
        return new HashSet<>(nodeNames).size() != resultList.size();
    }

    protected AbstractValueObject prepareValueObject(XmlInputExtractionResult result) {
        AbstractValueObject valueObject;
        if (result.getValue().isPresent()) {
            String value = result.getValue().get();
            if (!value.isEmpty()) {
                valueObject = new JsonValueObject(value);
            } else {
                valueObject = new JsonEmptyValueObject();
            }
        } else {
            valueObject = new JsonNullValueObject();
        }
        return valueObject;
    }
}

