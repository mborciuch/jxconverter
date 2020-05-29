package app.converters.json2xml;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import app.converters.abstractconverter.AbstractConverter;
import app.components.ComponentNode;
import app.components.Node;
import app.components.NodeList;
import app.factories.NodeFactory;
import app.exception.ProcessingException;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

public class xml2jsonConverter extends AbstractConverter {

    private final ObjectMapper objectMapper;

    public xml2jsonConverter(NodeFactory nodeFactory, ObjectMapper objectMapper) {
        super(nodeFactory);
        this.objectMapper = objectMapper;
    }

    @Override
    public String convert(String input) {
        LinkedHashMap<String, Object> rootMap = null;
        ComponentNode componentNode = null;
        try {
            rootMap = (LinkedHashMap<String, Object>) objectMapper.readValue(input, Map.class);
            componentNode = prepareStructure(rootMap);
        } catch (JsonProcessingException | ProcessingException e) {
            e.printStackTrace();
        }

        return componentNode.print();
    }


    private ComponentNode prepareStructure(Map<String, Object> rootMap) throws ProcessingException {
        ComponentNode componentNode;
        NodeList nodes;
        if (rootMap.keySet().size() > 1) {
            componentNode = getNodeFactory().getComponentNodeWithNodeList();
            componentNode.setAbstractNode(prepareNodeList("root", rootMap));
        } else {
            String key = rootMap.keySet().stream().findFirst().orElseThrow(RuntimeException::new);
            if (rootMap.get(key) instanceof String || rootMap.get(key) == null) {
                componentNode = prepareNodeStructure(key, rootMap);
            } else {
                Map<String, Object> nestedMap = (Map<String, Object>) rootMap.get(key);
                String nestedMapFirstKey = nestedMap.keySet().stream().findFirst().orElseThrow(RuntimeException::new);
                if (nestedMapFirstKey.startsWith("@")) {
                    componentNode = prepareNodeWithAttributesStructure(key, nestedMap);
                } else {
                    componentNode = prepareListStructure(key, nestedMap);
                }
            }
        }
        return componentNode;
    }

    private ComponentNode prepareListStructure(String key, Map<String, Object> rootMap) throws ProcessingException {

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
        while (entryIterator.hasNext()){
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

    private ComponentNode prepareNodeWithAttributesStructure(String key, Map<String, Object> rootMap) throws ProcessingException {
        ComponentNode componentNode = getNodeFactory().getComponentNodeWithNode();
        Node node = prepareNodeWithAttributes(rootMap);
        componentNode.setNodeName(key);
        componentNode.setAbstractNode(node);
        return componentNode;
    }

    private Node prepareNode(Map<String, Object> rootMap){
        String key = rootMap.keySet().stream().findFirst().orElseThrow(RuntimeException::new);
        Node node;
        if (rootMap.get(key) != null) {
            node = getNodeFactory().getNodeWithValue(key, (String) rootMap.get(key));
        } else {
            node = getNodeFactory().getNodeWithNoValue(key);
        }
        return node;
    }

    private Node prepareNodeWithAttributes(Map<String, Object> rootMap) throws ProcessingException {
        Node node = null;
        Iterator<Map.Entry<String, Object>> iterator = rootMap.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, Object> currentEntry = iterator.next();
            if (!iterator.hasNext()) {
                if (currentEntry.getValue() != null) {
                    node = getNodeFactory().getNodeWithValue(currentEntry.getKey(), String.valueOf(currentEntry.getValue()));
                } else {
                    node = getNodeFactory().getNodeWithNoValue(currentEntry.getKey());
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
                String value =  currentEntry.getValue().toString();
                attributes.put(currentEntry.getKey(), value);
            } else {
                break;
            }
        }
        node.setAttributes(attributes);
        return node;
    }
}






