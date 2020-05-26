package converters.json2xml;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import converters.AbstractConverter;
import converters.components.ComponentNode;
import converters.components.Node;
import converters.components.NodeList;
import converters.factories.NodeFactory;
import converters.json2xml.exception.ProcessingException;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

public class XmlConverter extends AbstractConverter {

    private final ObjectMapper objectMapper;

    public XmlConverter(NodeFactory nodeFactory, ObjectMapper objectMapper) {
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
            Map<String, Object> tempMap = new LinkedHashMap<>();
            tempMap.put("root", rootMap);
            componentNode = prepareListStructure(tempMap);
        } else {
            String key = rootMap.keySet().stream().findFirst().orElseThrow(RuntimeException::new);
            if (rootMap.get(key) instanceof String || rootMap.get(key) == null) {
                componentNode = prepareNodeStructure(rootMap);
            } else {
                Map<String, Object> nestedMap = (Map<String, Object>) rootMap.get(key);
                String nestedMapFirstKey = nestedMap.keySet().stream().findFirst().orElseThrow(RuntimeException::new);
                if (nestedMapFirstKey.startsWith("@")) {
                    componentNode = prepareNodeWithAttributesStructure(nestedMap);
                } else {
                    componentNode = prepareListStructure(rootMap);
                }
            }
        }
        return componentNode;
    }

    private ComponentNode prepareListStructure(Map<String, Object> rootMap) throws ProcessingException {
        String key = rootMap.keySet().stream().findFirst().orElseThrow(RuntimeException::new);
        ComponentNode componentNode = getNodeFactory().getComponentNodeWithNodeList();
        NodeList nodes = prepareNodeList(rootMap);
        componentNode.setAbstractNode(nodes);
        return componentNode;
    }

    private NodeList prepareNodeList(Map<String, Object> rootMap) throws ProcessingException {
        String key = rootMap.keySet().stream().findFirst().orElseThrow(RuntimeException::new);
        NodeList nodes = getNodeFactory().getNodeList(key);
        Map<String, Object> tempMap = (Map<String, Object>) rootMap.get(key);
        String tempMapFirstKey = tempMap.keySet().stream().findFirst().orElseThrow(RuntimeException::new);
        if (tempMap.get(tempMapFirstKey) instanceof Map) {
            nodes.addAbstractElement(prepareNodeList(tempMap));
        } else {
            Iterator<Map.Entry<String, Object>> entryIterator = tempMap.entrySet().iterator();
            while (entryIterator.hasNext()) {
                Map.Entry<String, Object> currentEntry = entryIterator.next();
                if (currentEntry.getValue() instanceof String || currentEntry.getValue() == null) {
                    Map<String, Object> innerTempMap = new LinkedHashMap<>();
                    innerTempMap.put(currentEntry.getKey(), currentEntry.getValue());
                    Node<String> node = prepareNode(innerTempMap);
                    nodes.addAbstractElement(node);
                } else {
                    Map<String, Object> innerTempMap = (Map<String, Object>) currentEntry.getValue();
                    String innerNestedMapFirstKey = innerTempMap.keySet().stream().findFirst().orElseThrow(RuntimeException::new);
                    if (innerNestedMapFirstKey.startsWith("@")) {
                        nodes.addAbstractElement(prepareNodeWithAttributes(innerTempMap));
                    } else {
                        nodes.addAbstractElement(prepareNodeList(innerTempMap));
                    }
                }
            }
        }

        return nodes;
    }

    private ComponentNode prepareNodeStructure(Map<String, Object> rootMap) {
        ComponentNode componentNode = getNodeFactory().getComponentNodeWithNode();
        Node node = prepareNode(rootMap);
        componentNode.setNodeName(node.getNodeName());
        componentNode.setAbstractNode(node);
        return componentNode;
    }

    private ComponentNode prepareNodeWithAttributesStructure(Map<String, Object> rootMap) throws ProcessingException {
        ComponentNode componentNode = getNodeFactory().getComponentNodeWithNode();
        String key = rootMap.keySet().stream().findFirst().orElseThrow(RuntimeException::new);
        Node node = prepareNodeWithAttributes(rootMap);
        componentNode.setNodeName(node.getNodeName());
        componentNode.setAbstractNode(node);
        return componentNode;
    }

    private Node prepareNode(Map<String, Object> rootMap) {
        String key = rootMap.keySet().stream().findFirst().orElseThrow(RuntimeException::new);
        Node<String> node;
        if (rootMap.get(key) != null) {
            node = getNodeFactory().getNodeWithValue(key, rootMap.get(key));
        } else {
            node = getNodeFactory().getNodeWithNoValue(key);
        }
        return node;
    }

    private Node prepareNodeWithAttributes(Map<String, Object> rootMap) throws ProcessingException {
        Node<String> node = null;
        Iterator<Map.Entry<String, Object>> iterator = rootMap.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, Object> currentEntry = iterator.next();
            if (!iterator.hasNext()) {
                if (currentEntry.getValue() != null) {
                    node = getNodeFactory().getNodeWithValue(currentEntry.getKey(), currentEntry.getValue());
                } else {
                    node = getNodeFactory().getNodeWithNoValue(currentEntry.getKey());
                }
            }
        }
        iterator = rootMap.entrySet().iterator();
        Map<String, String> attributes = new HashMap<>();
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
}






