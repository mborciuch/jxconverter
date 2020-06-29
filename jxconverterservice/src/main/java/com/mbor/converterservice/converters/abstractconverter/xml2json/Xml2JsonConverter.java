package com.mbor.converterservice.converters.abstractconverter.xml2json;

import com.mbor.converterservice.components.AbstractNode;
import com.mbor.converterservice.components.ComponentNode;
import com.mbor.converterservice.components.Node;
import com.mbor.converterservice.components.NodeList;
import com.mbor.converterservice.components.ValueObject.AbstractValueObject;
import com.mbor.converterservice.components.ValueObject.EmptyValueObject;
import com.mbor.converterservice.components.ValueObject.NullValueObject;
import com.mbor.converterservice.components.ValueObject.ValueObject;
import com.mbor.converterservice.converters.abstractconverter.AbstractConverter;
import com.mbor.converterservice.factories.nodes.NodeFactory;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Xml2JsonConverter extends AbstractConverter {

    private static final String XML_ONE_LINE = "(<(.+)\\s?([^<>].+?)?>)(.+)?<\\/\\2>|(<([\\w\\d]+)\\s?((?=\\s)(.+?))?\\s?\\/>?)";
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
        List<InputExtractionResult> resultList = extractInput(input);
        if(isInputExtractionResultTheSameLevelList(resultList)){
                elementName = "root";
                NodeList nodeList = getNodeFactory().getNodeList(elementName);
                resultList.forEach(element ->
                        nodeList.addAbstractElement(prepareStructure(element.getWholeLine())
                        ));
                return nodeList;

        } else {
            InputExtractionResult result = resultList.get(0);
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
                    AbstractNode abstractNode =  prepareStructure(extractedValue);
                    if(abstractNode instanceof NodeList) {
                        nodeList = (NodeList) abstractNode;
                        nodeList.setNodeName(elementName);
                        if (isListWithEqualElement(nodeList.getList())) {
                            if(result.getAttributes().isPresent()){
                                Map<String, String> attributes = new LinkedHashMap<>(getElementAttributes(result.getAttributes().get(), ELEMENT_ATTRIBUTES));
                                nodeList = getNodeFactory().getEqualNodeListWithAttributes(nodeList);
                                nodeList.setAttributes(attributes);
                            } else {
                                nodeList = getNodeFactory().getEqualNodeList(nodeList);
                            }
                        } else {
                            if(result.getAttributes().isPresent()){
                                Map<String, String> attributes = new LinkedHashMap<>(getElementAttributes(result.getAttributes().get(), ELEMENT_ATTRIBUTES));
                                nodeList = getNodeFactory().getNodeListWithAttributes(nodeList);
                                nodeList.setAttributes(attributes);
                            }
                        }
                    } else {
                        if(result.getAttributes().isPresent()){
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

    private List<InputExtractionResult> extractInput(String input) {
        Pattern elementNamePattern = Pattern.compile(XML_ONE_LINE);
        Matcher elementNameMatcher = elementNamePattern.matcher(input);
        List<InputExtractionResult> resultList = new LinkedList<>();
        if (!elementNameMatcher.find()) {
            throw new RuntimeException("Invalid input line: " + input);
        }
        elementNameMatcher.reset();
        while (elementNameMatcher.find()) {
            InputExtractionResult inputExtractionResult = new InputExtractionResult();
            inputExtractionResult.setWholeLine(elementNameMatcher.group(0));
            if (elementNameMatcher.group(5) == null) {
                inputExtractionResult.setName(elementNameMatcher.group(2));
                if (elementNameMatcher.group(3) != null) {
                    inputExtractionResult.setAttributes(elementNameMatcher.group(3));
                }
                if (elementNameMatcher.group(4) != null) {
                    inputExtractionResult.setValue(elementNameMatcher.group(4));
                } else {
                    inputExtractionResult.setValue("");
                }
            } else {
                inputExtractionResult.setName(elementNameMatcher.group(6));
                if (elementNameMatcher.group(7) != null) {
                    inputExtractionResult.setAttributes(elementNameMatcher.group(7));
                }
            }
            resultList.add(inputExtractionResult);
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
                attributes.put(attributesMatcher.group(1), attributesMatcher.group(2).replace("\"",""));
            }
        }catch (Exception e){
            throw new RuntimeException("Enexpected error during processing attributtes:" + e.getMessage());
        }
        return attributes;
    }

    private String trimInput(String input) {
        return input.replaceAll("\\n", "").replaceAll("\\s{2,}", "");
    }

    private ComponentNode prepareComponentNode(AbstractNode abstractNode) {
        ComponentNode componentNode;
        if (abstractNode instanceof Node) {
            if(abstractNode.hasAttributes()){
                componentNode = getNodeFactory().getComponentNodeWithNodeList();
                componentNode.setAbstractNode(abstractNode);
            } else {
                componentNode = getNodeFactory().getComponentNodeWithNode();
                componentNode.setAbstractNode(abstractNode);
            }
        } else {
            componentNode = getNodeFactory().getComponentNodeWithNodeList();
            componentNode.setAbstractNode(abstractNode);
        }
        return componentNode;
    }

    private AbstractValueObject prepareValueObject(InputExtractionResult result){
        AbstractValueObject valueObject;
        if (result.getValue().isPresent()) {
            String value = result.getValue().get();
            if (!value.isEmpty()) {
                valueObject = new ValueObject(value);
            } else {
                valueObject = new EmptyValueObject();
            }
        } else {
            valueObject = new NullValueObject();
        }
        return valueObject;
    }

    private boolean isInputExtractionResultLeaf(InputExtractionResult result){
        String[] xmlSigns = {"<", ">", "/"};
        if(result.getValue().isPresent()){
            String value = result.getValue().get();
            if(value.isEmpty()){
                return true;
            } else {
                return Arrays.stream(xmlSigns).noneMatch(value::contains);
            }
        } else {
            return true;
        }
    }

    private boolean isInputExtractionResultTheSameLevelList(List<InputExtractionResult> resultList){
        if(resultList.size() == 0){
            throw new RuntimeException("ExtractionResultList is Empty");
        }
        return resultList.size() > 1;
    }

    private boolean isListWithEqualElement(List<AbstractNode> resultList) {
        List<String> nodeNames = resultList.stream().map(AbstractNode::getNodeName).collect(Collectors.toList());
        return new HashSet<>(nodeNames).size() != resultList.size();
    }

}

class InputExtractionResult {

    private String wholeLine;
    private String name;
    private String attributes;
    private String value;

    public String getWholeLine() {
        return wholeLine;
    }

    public void setWholeLine(String wholeLine) {
        this.wholeLine = wholeLine;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Optional<String> getAttributes() {
        return Optional.ofNullable(attributes);
    }

    public void setAttributes(String attributes) {
        this.attributes = attributes;
    }

    public Optional<String> getValue() {
        return Optional.ofNullable(value);
    }

    public void setValue(String value) {
        this.value = value;
    }

    public boolean isLeaf() {
        return !value.contains("<");
    }
}