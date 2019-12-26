package converters.jsonconverter;

import converters.AbstractConverter;
import converters.components.NodeList;
import converters.jsonconverter.component.JsonNode;
import converters.jsonconverter.component.JsonNodeList;
import converters.jsonconverter.component.JsonObject;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JsonConverter extends AbstractConverter {

    private static String ELEMENT_NAME_NO_ATTRIBUTES = "<([^<>]*?)/?[>]";
    private static String ELEMENT_NAME_WITH_ATTRIBUTES = "<(?:([^<>]*?)\\s)?";
    private static String ELEMENT_VALUE = ">([^<>/]*?)</";
    private static String ELEMENT_VALUE_EMPTY_ONE_TAG = "(.*\\s/>)?";
    private static String ELEMENT_VALUE_NESTED = "([^/]><[^/])";
    private static String ELEMENT_EMPTY_VALUE_NESTED = "(/><[^/])";
    private static String ELEMENT_EMPTY_VALUE_NESTED_2 = "(<(?:.*?)/>)((<.*?/?>)(.*?)(</.*?>))";
    private static String ELEMENT_ATTRIBUTES = "\\s(.*?)\\s=\\s\"(.*?)\"";

    private static String ATTRIBUTE_SIGN = "@";
    private static String VALUE_SIGN = "#";

    private static int currentIndentation = 0;
    private static int indentationOffset = 4;

    public static int getCurrentIndentation() {
        return currentIndentation;
    }

    public static void incrementCurrentIndentation() {
        JsonConverter.currentIndentation += indentationOffset;
    }

    public static void decrementCurrentIndentation() {
        JsonConverter.currentIndentation -= indentationOffset;
    }

    @Override
    public String convert(String input) {
        input = trimInput(input);
        JsonObject jsonObject = prepareStructure(input);
        return jsonObject.print();
    }

    private JsonObject prepareStructure(String input) {
        JsonObject jsonObject;
        String elementName;
        boolean isInputOneLineXML = isInputOneLineXML(input);

        if (isInputOneLineXML) {
            Optional<Map<String, String>> possibleAttributes = getElementAttributes(input, ELEMENT_ATTRIBUTES);
            Optional<String> oneLineValue = getElementValue(input, ELEMENT_VALUE);
            if (possibleAttributes.isPresent()) {
                elementName = getElementName(input, ELEMENT_NAME_WITH_ATTRIBUTES);
                NodeList jsonElementList = JsonNodeFactory.getJsonNodeList(elementName);
                for (Map.Entry<String, String> entry : possibleAttributes.get().entrySet()) {
                    JsonNode node = JsonNodeFactory.getJsonNodeWithValue(entry.getKey(), entry.getValue());
                    jsonElementList.addAbstractElement(node);
                }
                jsonObject = JsonNodeFactory.getJsonObjectWithNodeList();
                if (oneLineValue.isPresent()) {
                    JsonNode node = JsonNodeFactory.getJsonNodeWithValue(prepareValueName(elementName), oneLineValue.get());
                    jsonElementList.addAbstractElement(node);
                    jsonObject.setAbstractNode(jsonElementList);
                } else {
                    JsonNode node = JsonNodeFactory.getJsonNodeEmpty(prepareValueName(elementName));
                    jsonElementList.addAbstractElement(node);
                    jsonObject.setAbstractNode(jsonElementList);
                }

            } else {
                elementName = getElementName(input, ELEMENT_NAME_NO_ATTRIBUTES);
                jsonObject = JsonNodeFactory.getJsonObjectWithNode();
                if (oneLineValue.isPresent()) {
                    JsonNode node = JsonNodeFactory.getJsonNodeWithValue(elementName, oneLineValue.get());
                    jsonObject.setAbstractNode(node);
                } else {
                    JsonNode node = JsonNodeFactory.getJsonNodeEmpty(elementName);
                    jsonObject.setAbstractNode(node);
                }
            }
        } else {
            elementName = getElementName(input, ELEMENT_NAME_NO_ATTRIBUTES);
            boolean isInputWithList = isInputWithList(input, ELEMENT_NAME_WITH_ATTRIBUTES, elementName);
            JsonNodeList jsonNodeList;
            if (isInputWithList) {
                jsonNodeList = JsonNodeFactory.getJsonEqualNodeList(elementName);
            } else {
                jsonNodeList = JsonNodeFactory.getJsonNodeList(elementName);
            }

            jsonObject = JsonNodeFactory.getJsonObjectWithNodeList();
            jsonObject.setNodeName(elementName);
            String extractedValue = extractElement(input, elementName);
            List<String> allOneLines = new ArrayList<>();
            findAllLines(extractedValue, allOneLines);
            jsonObject.setAbstractNode(jsonNodeList);
            for (String element : allOneLines) {
                JsonObject newJsonObject = prepareStructure(element);
                jsonNodeList.addAbstractElement(newJsonObject.getAbstractNode());
            }
        }
        return jsonObject;
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
                    //TODO
                    //Inner node with the same name as outer node
                    //Inner11
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

    private boolean isInputOneLineXML(String input) {
        //Pattern elementNamePattern = Pattern.compile(">([^<>/]*?)</ || <([^<>]*?)/?>\"");
        Pattern elementNamePattern = Pattern.compile(">[<>/]*<");
        Matcher elementNameMatcher = elementNamePattern.matcher(input);
        if (elementNameMatcher.find()) {
            elementNameMatcher.usePattern(Pattern.compile("></"));
            return !elementNameMatcher.find();
        } else {
            return true;
        }
    }

    private Optional<Map<String, String>> getElementAttributes(String input, String regexp) {
        Map<String, String> attributes;
        Pattern attributesPattern = Pattern.compile(regexp);
        Matcher attributesMatcher = attributesPattern.matcher(input);
        String[] values;
        if (attributesMatcher.find()) {
//            if(input.indexOf(attributesMatcher.group()) == 0){
//                attributes = new HashMap<>();
//                attributesMatcher.reset();
//                while (attributesMatcher.find()) {
//                    values = attributesMatcher.group().split("=");
//                    attributes.put(values[0].trim(), values[1].replace("\"", "").trim());
//                }
//                return Optional.of(attributes);
//            }
            attributesMatcher.reset();
            attributes = new HashMap<>();
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
