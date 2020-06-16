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

public class Xml2JsonConverter extends AbstractConverter {

    private static final String XML_ONE_LINE = "<(.+)\\s?(.+)?>(.+)?</\\1>|<([\\w\\d]+)\\s?(.+)?/>";
    private static final String ELEMENT_NAME_NO_ATTRIBUTES = "<([^<>]*?)/?[>]";
    private static final String ELEMENT_NAME_WITH_ATTRIBUTES = "<(?:([^<>]*?)\\s)?";
    private static final String ELEMENT_VALUE = ">([^<>/]*?)</";
    private static final String ELEMENT_VALUE_NESTED = "([^/]><[^/])";
    private static final String ELEMENT_ATTRIBUTES = "([\\w]+)\\s?=\\s?([\\w\\\"]+)";
    private static final String ELEMENT_ATTRIBUTES_ONE_LINE_XML = "\\s(.*?)\\s?=\\s?\"(.*?)\"";
    private static final String ELEMENT_ATTRIBUTES_BEGINNING_OF_LIST = "";

    private static final String ATTRIBUTE_SIGN = "@";
    private static final String VALUE_SIGN = "#";

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
        //is multipleLine
        //is leaf node / is leaf empty node
        boolean isInputLeafNode = true;
        InputExtractionResult result = isInputLeafNode(input);
        elementName = result.getName();
        if (isInputLeafNode) {
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
            elementName = getElementName(input, ELEMENT_NAME_NO_ATTRIBUTES);
            boolean isInputWithList = isInputWithList(input, ELEMENT_NAME_WITH_ATTRIBUTES, elementName);
            NodeList nodeList;
            if (isInputWithList) {
                nodeList = getNodeFactory().getEqualNodeList(elementName);
            } else {
                nodeList = getNodeFactory().getNodeList(elementName);
            }
            String extractedValue = extractElement(input, elementName);
            List<String> allOneLines = new ArrayList<>();
            findAllLines(extractedValue, allOneLines);
            for (String element : allOneLines) {
                AbstractNode newJsonObject = prepareStructure(element);
                nodeList.addAbstractElement(newJsonObject);
            }
            return nodeList;
        }

    }

    private boolean isInputWithList(String input, String regexPattern, String parentName) {
        Pattern listPattern = Pattern.compile(parentName.concat(">".concat("<(.*?)[\\s>]")));
        Matcher listMatcher = listPattern.matcher(input);
        String result = null;
        if (listMatcher.find()) {
            result = listMatcher.group(1);
        } else {
            return false;
        }
        listMatcher.usePattern(Pattern.compile("<".concat(result.concat("[\\s>]"))));
        int counter = 0;
        while (listMatcher.find()) {
            counter++;
        }
        return counter > 0;
    }

    private String extractElement(String input, String parentName) {
        Pattern extractionPattern = Pattern.compile(parentName.concat("(?:.*?)").concat(">(.*?)</".concat(parentName).concat(">")));
        Matcher extractionMatcher = extractionPattern.matcher(input);
        String extractedElement = "";
        if (extractionMatcher.find()) {
            extractedElement = extractionMatcher.group(1);
        }
        return extractedElement.trim();
    }

    private List<String> findAllLines(String input, List<String> allOneLines) {
        String elementName = "";
        Pattern emptyNodePattern = Pattern.compile("<(.*?/?)>");
        Pattern oneLinePattern = Pattern.compile("((<.*?/?>)(.*?)(</.*?>))");
        Matcher oneLineMatcher = emptyNodePattern.matcher(input);
        while (oneLineMatcher.find()) {
            String result = oneLineMatcher.group();
            if (!result.contains("/")) {
                int firstIndex = oneLineMatcher.start();
                oneLineMatcher.region(firstIndex, input.length());
                oneLineMatcher.usePattern(oneLinePattern);
            } else {
                if (getElementValue(oneLineMatcher.group(1), ELEMENT_VALUE_NESTED).isPresent()) {
                    //                   String elementName;
//                    if (getElementAttributes(oneLineMatcher.group(1), ELEMENT_ATTRIBUTES).isPresent()) {
//                        elementName = getElementName(oneLineMatcher.group(2), ELEMENT_NAME_WITH_ATTRIBUTES);
//                    } else {
//                        elementName = getElementName(oneLineMatcher.group(2), ELEMENT_NAME_NO_ATTRIBUTES);
//                    }
                    StringBuilder openingTag = new StringBuilder();
                    openingTag.append("<").append(elementName).append(">");
                    StringBuilder closingTag = new StringBuilder(openingTag);
                    closingTag.insert(1, "/");
                    String newInput = input.substring(oneLineMatcher.regionStart());
                    int cutInputLength = input.length() - newInput.length();
                    String extractedValue = openingTag.toString().concat(extractElement(newInput, elementName).concat(closingTag.toString()));
                    allOneLines.add(extractedValue);
                    int lastIndex = cutInputLength + extractedValue.length();
                    oneLineMatcher.region(lastIndex, input.length());
                } else {
                    allOneLines.add(oneLineMatcher.group());
                }
                oneLineMatcher.usePattern(emptyNodePattern);
            }
        }
        return allOneLines;
    }

    private String getElementName(String element, String regexPattern) {
        Pattern elementNamePattern = Pattern.compile(regexPattern);
        Matcher elementNameMatcher = elementNamePattern.matcher(element);
        String elementName = "";
        if (elementNameMatcher.find()) {
            elementName = elementNameMatcher.group(1);
            if (elementName.contains("/")) {
                elementName = elementName.replace("/", "");
            }
        }
        return elementName;
    }

    private Optional<String> getElementValue(String element, String regexPattern) {
        Pattern elementValuePattern = Pattern.compile(regexPattern);
        Matcher elementValueMatcher = elementValuePattern.matcher(element);
        if (elementValueMatcher.find()) {
            return Optional.ofNullable(elementValueMatcher.group(1));
        }
        return Optional.empty();
    }

    //@JavaDoc Special case: one line xml with empty value : elementName></elementName>
    //Additional check in If statement. 3 derivatives from length of "></"
    private InputExtractionResult isInputLeafNode(String input) {
        Pattern elementNamePattern = Pattern.compile(XML_ONE_LINE);
        Matcher elementNameMatcher = elementNamePattern.matcher(input);
        InputExtractionResult inputExtractionResult = new InputExtractionResult();
        if (!elementNameMatcher.find()) {
            throw new RuntimeException("Invalid input line: " + input);
        }
        if (elementNameMatcher.group(4) == null) {
            inputExtractionResult.setName(elementNameMatcher.group(1));
            if (elementNameMatcher.group(2) != null) {
                inputExtractionResult.setAttributes(elementNameMatcher.group(2));
            }
            if (elementNameMatcher.group(3) != null) {
                inputExtractionResult.setValue(elementNameMatcher.group(3));
            } else {
                inputExtractionResult.setValue("");
            }
        } else {
            inputExtractionResult.setName(elementNameMatcher.group(4));
            if (elementNameMatcher.group(5) != null) {
                inputExtractionResult.setAttributes(elementNameMatcher.group(5));
            }
        }
        return inputExtractionResult;
    }

    private Map<String, String> getElementAttributes(String input, String regexp) {
        Map<String, String> attributes;
        Pattern attributesPattern = Pattern.compile(regexp);
        Matcher attributesMatcher = attributesPattern.matcher(input);
        String[] values;
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

    private String prepareAttributeName(String key) {
        return ATTRIBUTE_SIGN.concat(key);
    }

    private String prepareValueName(String key) {
        return VALUE_SIGN.concat(key);
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
}

class InputExtractionResult {

    private String name;
    private String attributes;
    private String value;

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