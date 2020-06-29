package com.mbor.converterservice.converters.abstractconverter.json2xml;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mbor.converterservice.components.AbstractNode;
import com.mbor.converterservice.components.ComponentNode;
import com.mbor.converterservice.components.Node;
import com.mbor.converterservice.components.NodeList;
import com.mbor.converterservice.components.ValueObject.*;
import com.mbor.converterservice.converters.abstractconverter.AbstractConverter;
import com.mbor.converterservice.converters.abstractconverter.json2xml.valueobjects.XmlNullValueObject;
import com.mbor.converterservice.converters.abstractconverter.json2xml.valueobjects.XmlValueObject;
import com.mbor.converterservice.converters.abstractconverter.xml2json.valueobjects.JsonNullValueObject;
import com.mbor.converterservice.exception.ProcessingException;
import com.mbor.converterservice.factories.nodes.NodeFactory;

import java.util.*;

public class Json2XmlConverter extends AbstractConverter {

    private final ObjectMapper objectMapper;

    public Json2XmlConverter(NodeFactory nodeFactory, ObjectMapper objectMapper) {
        super(nodeFactory);
        this.objectMapper = objectMapper;
    }

    @Override
    public String convert(String input) {
        AbstractNode resultTree = prepareStructure(input);
        return prepareComponentNode(resultTree).print();
    }

    private AbstractNode prepareStructure(String input) {
        String elementName;
        List<JsonInputExtractionResult> resultList = extractInput(input);
        JsonInputExtractionResult result = resultList.get(0);
        elementName = result.getName();
        AbstractValueObject valueObject;
        valueObject = prepareValueObject(result);
        Node node;
        node = getNodeFactory().getNode(elementName, valueObject);
        return node;
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

    private List<JsonInputExtractionResult> extractInput(String input) {
        LinkedHashMap<String, Object> rootMap;
        try {
            rootMap = (LinkedHashMap<String, Object>) objectMapper.readValue(input, Map.class);
        } catch (JsonProcessingException e) {
            throw new ProcessingException("Error during processing input Json:" + e.getMessage());
        }
        List<JsonInputExtractionResult> resultList = new LinkedList<>();
        for (Map.Entry<String, Object> entry : rootMap.entrySet()) {
            JsonInputExtractionResult jsonInputExtractionResult = new JsonInputExtractionResult();
            jsonInputExtractionResult.setName(entry.getKey());
            jsonInputExtractionResult.setValue(entry.getValue());
            jsonInputExtractionResult.setWholeLine(entry);
            resultList.add(jsonInputExtractionResult);
        }

        return resultList;
    }

    protected AbstractValueObject prepareValueObject(JsonInputExtractionResult result){
        AbstractValueObject valueObject;
        if (result.getValue() != null) {
            String value = (String) result.getValue();
            if (!value.isEmpty()) {
                valueObject = new XmlValueObject(value);
            } else {
                valueObject = new EmptyValueObject();
            }
        } else {
            valueObject = new XmlNullValueObject();
        }
        return valueObject;
    }
}






