//package converters.xmlconverter;
//
//import converters.AbstractConverter;
//import converters.components.ComponentNode;
//import converters.components.Node;
//
//
//import java.util.HashMap;
//import java.util.Map;
//import java.util.Optional;
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;
//
//public class XmlConverter extends AbstractConverter {
//
//    private static String ELEMENT_VALUE_NO_ATTRIBUTES = "(?:.*?)\\s:\\s\"?([^\"{}]+)\"?";
//    private static String ELEMENT_VALUE_WITH_ATTRIBUTES = "#(?:.*?)\\s:\\s\"?([^\"{}]+)\"?";
//
//    @Override
//    public String convert(String input) {
//        return prepareStructure(trimInput(input)).print();
//    }
//
//    private ComponentNode prepareStructure(String input) {
//        String elementName;
//        boolean isJsonOneLine = isJsonOneLine(input);
//
//        if (isJsonOneLine) {
//            Optional<String> oneLineValue;
//            Optional<Map<String, String>> possibleAttributes = getElementAttributes(input);
//            elementName = getElementName(input);
//            if (possibleAttributes.isPresent()) {
//                oneLineValue = getElementValue(input, ELEMENT_VALUE_WITH_ATTRIBUTES);
//                if (oneLineValue.isPresent()) {
//                    //TO DO OOP
//                    XmlNode xmlLine = new XmlNode(elementName, oneLineValue.get());
//                    xmlLine.setAttributes(possibleAttributes.get());
//                    xmlObject.setAbstractNode(xmlLine);
//                } else {
//                    //TO DO OOP
//                    XmlNode xmlLine = new XmlNode(elementName);
//                    xmlLine.setAttributes(possibleAttributes.get());
//                    xmlObject.setAbstractNode(xmlLine);
//                }
//            } else {
//                oneLineValue = getElementValue(input, ELEMENT_VALUE_NO_ATTRIBUTES);
//                if (oneLineValue.isPresent()) {
//                    Node<String> xmlNode = new XmlNode(elementName, oneLineValue.get());
//                    xmlObject.setAbstractNode(xmlNode);
//                } else {
//                    Node<String> xmlNode = new XmlNode(elementName);
//                    xmlObject.setAbstractNode(xmlNode);
//                }
//            }
//        }
//    }
//
//    private Optional<Map<String, String>> getElementAttributes(String input) {
//        Pattern attributePattern = Pattern.compile("\"[@](.*?)\"\\s:\\s\"?([^,]*)\"?");
//        Matcher attributeMatcher = attributePattern.matcher(input);
//        Map<String, String> attributes;
//        if (attributeMatcher.find()) {
//            attributes = new HashMap<>();
//            attributeMatcher.reset();
//            String[] values;
//            while (attributeMatcher.find()) {
//                values = attributeMatcher.group().split(":");
//                attributes.put(values[0].replace("\"", "").replace("@", "").trim(), values[1].replace("\"", "").trim());
//            }
//            return Optional.of(attributes);
//        }
//        return Optional.empty();
//
//    }
//
//    private String getElementName(String input) {
//        Pattern elementNamePattern = Pattern.compile("\"#?(.*?)\"\\s:");
//        Matcher elementNameMatcher = elementNamePattern.matcher(input);
//        String elementName = "";
//        if (elementNameMatcher.find()) {
//            elementName = elementNameMatcher.group(1);
//        }
//        return  elementName;
//    }
//
//    private Optional<String> getElementValue(String input, String regexPattern) {
//        Pattern elementNamePattern = Pattern.compile(regexPattern);
//        Matcher elementNameMatcher = elementNamePattern.matcher(input);
//        if (elementNameMatcher.find()) {
//            if(elementNameMatcher.group(1).equals("null")){
//                return Optional.empty();
//            }
//            return Optional.ofNullable(elementNameMatcher.group(1));
//        }
//        return Optional.empty();
//    }
//
//    private boolean isJsonOneLine(String input) {
//        Pattern elementNamePattern = Pattern.compile("\\{\"(.*?)\"\\s:\\s([\":]*?)");
//        Matcher elementNameMatcher = elementNamePattern.matcher(input);
//        return elementNameMatcher.find();
//    }
//
//    private String trimInput(String input) {
//        String newInput = input.replaceAll("\\n", "");
//        newInput = newInput.replaceAll("\\s{2,}", "");
//        return newInput;
//    }
//}
