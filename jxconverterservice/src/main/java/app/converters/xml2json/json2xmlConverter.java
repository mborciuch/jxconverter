package app.converters.xml2json;

import app.converters.abstractconverter.AbstractConverter;
import app.components.ComponentNode;
import app.components.Node;
import app.components.NodeList;
import app.factories.NodeFactory;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class json2xmlConverter extends AbstractConverter {

    private static final String XML_ONE_LINE = ".+?>.*?([<>/]).*?</.+?";
    private static final String ELEMENT_NAME_NO_ATTRIBUTES = "<([^<>]*?)/?[>]";
    private static final String ELEMENT_NAME_WITH_ATTRIBUTES = "<(?:([^<>]*?)\\s)?";
    private static final String ELEMENT_VALUE = ">([^<>/]*?)</";
    private static final String ELEMENT_VALUE_NESTED = "([^/]><[^/])";
    private static final String ELEMENT_ATTRIBUTES = "\\s(.*?)\\s=\\s\"(.*?)\"";
    private static final String ELEMENT_ATTRIBUTES_ONE_LINE_XML = "\\s(.*?)\\s?=\\s?\"(.*?)\"";
    private static final String ELEMENT_ATTRIBUTES_BEGINNING_OF_LIST = "";

    private static final String ATTRIBUTE_SIGN = "@";
    private static final String VALUE_SIGN = "#";

    private static int currentIndentation = 0;
    private static int indentationOffset = 4;

    public json2xmlConverter(NodeFactory nodeFactory) {
        super(nodeFactory);
    }

    public static int getCurrentIndentation() {
        return currentIndentation;
    }

    public static void incrementCurrentIndentation() {
        json2xmlConverter.currentIndentation += indentationOffset;
    }

    public static void decrementCurrentIndentation() {
        json2xmlConverter.currentIndentation -= indentationOffset;
    }

    @Override
    public String convert(String input) {
        return prepareStructure(trimInput(input)).print();
    }

    private ComponentNode prepareStructure(String input) {
        ComponentNode componentNode;
        String elementName;
        boolean isInputOneLineXML = isInputOneLine(input);
        if (isInputOneLineXML) {
            Optional<Map<String, String>> possibleAttributes = getElementAttributes(input, ELEMENT_ATTRIBUTES_ONE_LINE_XML);
            Optional<String> oneLineValue = getElementValue(input, ELEMENT_VALUE);
            if (possibleAttributes.isPresent()) {
                elementName = getElementName(input, ELEMENT_NAME_WITH_ATTRIBUTES);
                NodeList nodeList = getNodeFactory().getNodeList(elementName);
                for (Map.Entry<String, String> entry : possibleAttributes.get().entrySet()) {
                    Node node = getNodeFactory().getNodeWithValue(entry.getKey(), entry.getValue());
                    nodeList.addAbstractElement(node);
                }
                componentNode = getNodeFactory().getComponentNodeWithNodeList();
                if (oneLineValue.isPresent()) {
                    Node node = getNodeFactory().getNodeWithValue(prepareValueName(elementName), oneLineValue.get());
                    nodeList.addAbstractElement(node);
                    componentNode.setAbstractNode(nodeList);
                } else {
                    Node node = getNodeFactory().getNodeWithNoValue(prepareValueName(elementName));
                    nodeList.addAbstractElement(node);
                    componentNode.setAbstractNode(nodeList);
                }

            } else {
                elementName = getElementName(input, ELEMENT_NAME_NO_ATTRIBUTES);
                componentNode = getNodeFactory().getComponentNodeWithNode();
                if (oneLineValue.isPresent()) {
                    Node node = getNodeFactory().getNodeWithValue(elementName, oneLineValue.get());
                    componentNode.setAbstractNode(node);
                } else {
                    Node node = getNodeFactory().getNodeWithNoValue(elementName);
                    componentNode.setAbstractNode(node);
                }
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
            componentNode = getNodeFactory().getComponentNodeWithNodeList();
            componentNode.setNodeName(elementName);
            String extractedValue = extractElement(input, elementName);
            List<String> allOneLines = new ArrayList<>();
            findAllLines(extractedValue, allOneLines);
            componentNode.setAbstractNode(nodeList);
            for (String element : allOneLines) {
                ComponentNode newJsonObject = prepareStructure(element);
                nodeList.addAbstractElement(newJsonObject.getAbstractNode());
            }
        }
        return componentNode;
    }

    private boolean isInputWithList(String input, String regexPattern, String parentName){
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
        while (listMatcher.find()){
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
                    String elementName;
                    if (getElementAttributes(oneLineMatcher.group(1), ELEMENT_ATTRIBUTES).isPresent()) {
                        elementName = getElementName(oneLineMatcher.group(2), ELEMENT_NAME_WITH_ATTRIBUTES);
                    } else {
                        elementName = getElementName(oneLineMatcher.group(2), ELEMENT_NAME_NO_ATTRIBUTES);
                    }
                    StringBuilder openingTag = new StringBuilder();
                    openingTag.append("<").append(elementName).append(">");
                    StringBuilder closingTag = new StringBuilder(openingTag);
                    closingTag.insert(1,"/");
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
    private boolean isInputOneLine(String input) {
        Pattern elementNamePattern = Pattern.compile(XML_ONE_LINE);
        Matcher elementNameMatcher = elementNamePattern.matcher(input);
        return !elementNameMatcher.find();
    }

    private Optional<Map<String, String>> getElementAttributes(String input, String regexp) {
        Map<String, String> attributes;
        Pattern attributesPattern = Pattern.compile(regexp);
        Matcher attributesMatcher = attributesPattern.matcher(input);
        String[] values;
        if (attributesMatcher.find()) {
            attributesMatcher.reset();
            attributes = new LinkedHashMap<>();
            while (attributesMatcher.find()){
                values = attributesMatcher.group().split("=");
                attributes.put(prepareAttributeName(values[0].trim()), values[1].replace("\"", "").trim());
            }
            return Optional.of(attributes);
        }
        return Optional.empty();
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
}
