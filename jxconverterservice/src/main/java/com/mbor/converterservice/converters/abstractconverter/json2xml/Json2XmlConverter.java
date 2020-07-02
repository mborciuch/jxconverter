package com.mbor.converterservice.converters.abstractconverter.json2xml;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mbor.converterservice.components.AbstractNode;
import com.mbor.converterservice.components.ComponentNode;
import com.mbor.converterservice.components.Node;
import com.mbor.converterservice.components.NodeList;
import com.mbor.converterservice.components.ValueObject.AbstractValueObject;
import com.mbor.converterservice.converters.abstractconverter.AbstractConverter;
import com.mbor.converterservice.converters.abstractconverter.InputExtractionResult;
import com.mbor.converterservice.converters.abstractconverter.json2xml.valueobjects.XmlEmptyValueObject;
import com.mbor.converterservice.converters.abstractconverter.json2xml.valueobjects.XmlNullValueObject;
import com.mbor.converterservice.converters.abstractconverter.json2xml.valueobjects.XmlValueObject;
import com.mbor.converterservice.converters.abstractconverter.xml2json.valueobjects.JsonNullValueObject;
import com.mbor.converterservice.exception.ProcessingException;
import com.mbor.converterservice.factories.nodes.NodeFactory;

import java.util.*;

public class Json2XmlConverter extends AbstractConverter<JsonInputExtractionResult> {

    private final ObjectMapper objectMapper;

    public Json2XmlConverter(NodeFactory nodeFactory, ObjectMapper objectMapper) {
        super(nodeFactory);
        this.objectMapper = objectMapper;
    }

    @Override
    public String convert(String input) {
        LinkedHashMap<String, Object> rootMap = prepareRootMap(input);
        AbstractNode resultTree = prepareStructure(rootMap);
        return prepareComponentNode(resultTree).print();
    }

    private AbstractNode prepareStructure(LinkedHashMap<String, Object> rootMap) {
        String elementName;
        List<JsonInputExtractionResult> resultList = extractInput(rootMap);
        if (isInputExtractionResultTheSameLevelList(resultList)) {
            elementName = "root";
            NodeList nodeList = getNodeFactory().getNodeList(elementName);
            resultList.forEach(element ->
                    nodeList.addAbstractElement(prepareStructure((LinkedHashMap<String, Object>) element.getWholeLine())
            ));
            return nodeList;
        } else {
            JsonInputExtractionResult result = resultList.get(0);
            elementName = result.getName();
            if (isInputExtractionResultLeaf(result)) {
                AbstractValueObject valueObject;
                valueObject = prepareValueObject(result);
                Node node;
                node = getNodeFactory().getNode(elementName, valueObject);
                return node;
            } else {
                if (isInputExtractionResultWithAttributes((LinkedHashMap<String, Object>) result.getValue())) {
                    LinkedHashMap<String, String> attributes = getAttributes((LinkedHashMap<String, Object>) result.getValue());
                    AbstractNode abstractNode = prepareStructure(createMapWithValueFromElementWithAttributes(elementName, (Map<String, Object>) result.getValue()));
                    if(abstractNode instanceof Node){
                        Node node = (Node) abstractNode;
                        node.setAttributes(attributes);
                        return node;
                    } else {
                        NodeList nodeList = (NodeList) abstractNode;
                        nodeList.setAttributes(attributes);
                        return nodeList;
                    }
                } else {
                    NodeList nodeList = getNodeFactory().getNodeList(result.getName());
                    if (!(result.getValue() instanceof Map)) {
                        throw new RuntimeException("Result Value should be Map");
                    }
                    LinkedHashMap<String, Object> values = (LinkedHashMap<String, Object>) result.getValue();
                    values.entrySet().forEach(value -> {
                        nodeList.addAbstractElement(prepareStructure(createMapFromEntry(value)));
                    });
                    return nodeList;
                }
            }
        }
    }

    private boolean isInputExtractionResultWithAttributes(LinkedHashMap<String, Object> rootMap) {
        int attributesNumber = (int) rootMap.keySet().stream()
                .filter(entry -> entry.startsWith("@"))
                .count();
        int valueNumber = (int) rootMap.keySet().stream()
                .filter(entry -> entry.startsWith("#"))
                .count();
        if(attributesNumber + valueNumber == 0){
            return false;
        } else {
            if(valueNumber != 1){
                throw new ProcessingException("Element must have one value");
            }
            if(attributesNumber != rootMap.size() - 1){
                throw new ProcessingException("All element should be either attributes or value");
            }
            return true;
        }
    }

    private LinkedHashMap<String, String> getAttributes(LinkedHashMap<String, Object> rootMap) {
        LinkedHashMap<String, String> attributes = new LinkedHashMap<>();
        for(Map.Entry<String, Object> entry : rootMap.entrySet()){
            if((entry.getValue() instanceof Collection)){
                throw new ProcessingException("Attribute Value must be string or number");
            }
            if(entry.getKey().startsWith("@")){
                attributes.put(entry.getKey().substring(1), entry.getValue().toString());
            }
        }
        if(rootMap.size() - 1 != attributes.size()){
            throw new ProcessingException("Incorrect number of parameters: should be" + (rootMap.size() - 1) + "but is:" + attributes.size());
        }
        return attributes;
    }

    private ComponentNode prepareListStructure(String key, Map<String, Object> rootMap) {

        ComponentNode componentNode = getNodeFactory().getComponentNodeWithNodeList();
        NodeList nodes = getNodeFactory().getNodeList(key);
        Iterator<Map.Entry<String, Object>> entryIterator = rootMap.entrySet().iterator();
        while (entryIterator.hasNext()) {
            Map.Entry<String, Object> currentEntry = entryIterator.next();
            String innerKey = currentEntry.getKey();
            if (currentEntry.getValue() instanceof String || currentEntry.getValue() == null) {
                Map<String, Object> tempMap = new LinkedHashMap<>();
                tempMap.put(currentEntry.getKey(), currentEntry.getValue());
                Node node = prepareNode(tempMap);
                nodes.addAbstractElement(node);
            } else {
                Map<String, Object> innerNestedMap = (Map<String, Object>) currentEntry.getValue();
                String innerNestedMapFirstKey = innerNestedMap.keySet().stream().findFirst().orElseThrow(RuntimeException::new);
                if (innerNestedMapFirstKey.startsWith("@")) {
                    nodes.addAbstractElement(prepareNodeWithAttributesStructure(innerKey, innerNestedMap));
                } else {
                    nodes.addAbstractElement(prepareNodeList(innerKey, innerNestedMap));
                }
            }
        }
        componentNode.setAbstractNode(nodes);
        return componentNode;
    }

    private ComponentNode prepareNodeStructure(String key, Map<String, Object> rootMap) {
        ComponentNode componentNode = getNodeFactory().getComponentNodeWithNode();
        Node node = prepareNode(rootMap);
        componentNode.setNodeName(key);
        componentNode.setAbstractNode(node);
        return componentNode;
    }

    private NodeList prepareNodeList(String key, Map<String, Object> rootMap) throws ProcessingException {
        String innerKey = rootMap.keySet().stream().findFirst().orElseThrow(RuntimeException::new);
        NodeList nodes = getNodeFactory().getNodeList(key);
        Iterator<Map.Entry<String, Object>> entryIterator = rootMap.entrySet().iterator();
        while (entryIterator.hasNext()) {
            Map.Entry<String, Object> currentEntry = entryIterator.next();
            if (currentEntry.getValue() instanceof String || currentEntry.getValue() == null) {
                Map<String, Object> tempMap = new LinkedHashMap<>();
                tempMap.put(currentEntry.getKey(), currentEntry.getValue());
                Node node = prepareNode(tempMap);
                nodes.addAbstractElement(node);
            } else {
                Map<String, Object> innerNestedMap = (Map<String, Object>) currentEntry.getValue();
                String innerNestedMapFirstKey = innerNestedMap.keySet().stream().findFirst().orElseThrow(RuntimeException::new);
                if (innerNestedMapFirstKey.startsWith("@")) {
                    nodes.addAbstractElement(prepareNodeWithAttributesStructure(innerKey, innerNestedMap));
                } else {
                    nodes.addAbstractElement(prepareNodeList(innerKey, innerNestedMap));
                }
            }
        }
        return nodes;
    }

    private ComponentNode prepareNodeWithAttributesStructure(String key, Map<String, Object> rootMap) {
        ComponentNode componentNode = getNodeFactory().getComponentNodeWithNode();
        Node node = prepareNodeWithAttributes(rootMap);
        componentNode.setNodeName(key);
        componentNode.setAbstractNode(node);
        return componentNode;
    }

    private Node prepareNode(Map<String, Object> rootMap) {
        String key = rootMap.keySet().stream().findFirst().orElseThrow(RuntimeException::new);
        Node node;
        if (rootMap.get(key) != null) {

            //todo
            node = getNodeFactory().getNode(key, new JsonNullValueObject());
        } else {
            node = getNodeFactory().getNode(key, new JsonNullValueObject());
        }
        return node;
    }

    private Node prepareNodeWithAttributes(Map<String, Object> rootMap) {
        Node node = null;
        Iterator<Map.Entry<String, Object>> iterator = rootMap.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, Object> currentEntry = iterator.next();
            if (!iterator.hasNext()) {
                if (currentEntry.getValue() != null) {
                    //todo
                    node = getNodeFactory().getNode(currentEntry.getKey(), new JsonNullValueObject());
                } else {
                    node = getNodeFactory().getNode(currentEntry.getKey(), new JsonNullValueObject());
                }
            }
        }
        iterator = rootMap.entrySet().iterator();
        Map<String, String> attributes = new LinkedHashMap<>();
        while (iterator.hasNext()) {
            Map.Entry<String, Object> currentEntry = iterator.next();
            if (iterator.hasNext()) {
                if ((currentEntry.getValue() instanceof Map)) {
                    throw new ProcessingException("Attribute should be String or Number");
                }
                String value = currentEntry.getValue().toString();
                attributes.put(currentEntry.getKey(), value);
            } else {
                break;
            }
        }
        node.setAttributes(attributes);
        return node;
    }


    protected List<JsonInputExtractionResult> extractInput(Map<String, Object> rootMap) {
        List<JsonInputExtractionResult> resultList = new LinkedList<>();
        for (Map.Entry<String, Object> entry : rootMap.entrySet()) {
            JsonInputExtractionResult jsonInputExtractionResult = new JsonInputExtractionResult();
            jsonInputExtractionResult.setName(entry.getKey());
            jsonInputExtractionResult.setValue(entry.getValue());
            jsonInputExtractionResult.setWholeLine(createMapFromEntry(entry));
            resultList.add(jsonInputExtractionResult);
        }
        return resultList;
    }

    private LinkedHashMap<String, Object> createMapFromEntry(Map.Entry<String, Object> entry){
        LinkedHashMap<String, Object> linkedHashMap = new LinkedHashMap<>();
        linkedHashMap.put(entry.getKey(), entry.getValue());
        return linkedHashMap;
    }

    private LinkedHashMap<String, Object> createMapWithValueFromElementWithAttributes(String key, Map<String, Object> map){
        LinkedHashMap<String, Object> linkedHashMap = new LinkedHashMap<>();
        String tempKey = "#".concat(key);
        linkedHashMap.put(key, map.get(tempKey));
        return linkedHashMap;
    }

    private LinkedHashMap<String, Object> prepareRootMap(String input){
        LinkedHashMap<String, Object> rootMap;
        try {
            rootMap = (LinkedHashMap<String, Object>) objectMapper.readValue(input, Map.class);
        } catch (JsonProcessingException e) {
            throw new ProcessingException("Error during processing input Json:" + e.getMessage());
        }
        return rootMap;
    }


    @Override
    protected boolean isInputExtractionResultLeaf(InputExtractionResult inputExtractionResult) {
        if (!(inputExtractionResult instanceof JsonInputExtractionResult)) {
            throw new RuntimeException("Result should be JsonInputExtractionResult");
        }
        JsonInputExtractionResult jsonInputExtractionResult = (JsonInputExtractionResult) inputExtractionResult;
        Object value = jsonInputExtractionResult.getValue();
        return !(value instanceof Map);
    }

    protected AbstractValueObject prepareValueObject(JsonInputExtractionResult result) {
        AbstractValueObject valueObject;
        if (result.getValue() != null) {
            String value = (String) result.getValue();
            if (!value.isEmpty()) {
                valueObject = new XmlValueObject(value);
            } else {
                valueObject = new XmlEmptyValueObject();
            }
        } else {
            valueObject = new XmlNullValueObject();
        }
        return valueObject;
    }

}






