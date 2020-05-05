package converters.json2xml;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import converters.AbstractConverter;
import converters.components.ComponentNode;
import converters.components.Node;
import converters.components.NodeList;
import converters.factories.NodeFactory;

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
        try {
            rootMap = (LinkedHashMap<String, Object>) objectMapper.readValue(input, Map.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        ComponentNode componentNode = prepareStructure(rootMap);
        return componentNode.print();
    }

    private ComponentNode prepareStructure(Map<String, Object> rootMap) {
        ComponentNode componentNode;
        NodeList nodes;
        if (rootMap.keySet().size() > 1) {
            componentNode = getNodeFactory().getComponentNodeWithNodeList();
            nodes = getNodeFactory().getNodeList("root");
        } else {
            String key = rootMap.keySet().stream().findFirst().orElseThrow(RuntimeException::new);
            if (rootMap.get(key) instanceof String || rootMap.get(key) == null) {
                componentNode = prepareNodeStructure(rootMap);
            } else {
                Map<String, Object> nestedMap = (Map<String, Object>) rootMap.get(key);
                String nestedMapFirstKey = nestedMap.keySet().stream().findFirst().orElseThrow(RuntimeException::new);
                if (nestedMapFirstKey.startsWith("@")) {
                    componentNode = prepareNodeWithAttributesStructure(rootMap);
                } else {
                    componentNode = prepareListStructure(rootMap);
                }
            }
        }
        return componentNode;
    }

    private ComponentNode prepareListStructure(Map<String, Object> rootMap) {
        String key = rootMap.keySet().stream().findFirst().orElseThrow(RuntimeException::new);
        ComponentNode componentNode = getNodeFactory().getComponentNodeWithNodeList();
        NodeList nodes = getNodeFactory().getNodeList(key);
        Map<String, Object> nestedMap = (Map<String, Object>) rootMap.get(key);
        Iterator<Map.Entry<String, Object>> entryIterator = nestedMap.entrySet().iterator();
        while (entryIterator.hasNext()) {
            Map.Entry<String, Object> currentEntry = entryIterator.next();
            if (currentEntry.getValue() instanceof String || currentEntry.getValue() == null) {
                Node<String> node;
                if (currentEntry.getValue() != null) {
                    node = getNodeFactory().getNodeWithValue(currentEntry.getKey(), currentEntry.getValue());
                } else {
                    node = getNodeFactory().getNodeWithNoValue(currentEntry.getKey());
                }
                nodes.addAbstractElement(node);
            } else {
                nodes.addAbstractElement(prepareStructure((Map<String, Object>) currentEntry.getValue()));
            }
        }
        componentNode.setAbstractNode(nodes);
        return componentNode;
    }

    private ComponentNode prepareNodeStructure(Map<String, Object> rootMap) {
        String key = rootMap.keySet().stream().findFirst().orElseThrow(RuntimeException::new);
        ComponentNode componentNode = getNodeFactory().getComponentNodeWithNode();
        componentNode.setNodeName(key);
        Node<String> node;
        if (rootMap.get(key) != null) {
            node = getNodeFactory().getNodeWithValue(key, rootMap.get(key));
        } else {
            node = getNodeFactory().getNodeWithNoValue(key);
        }
        componentNode.setAbstractNode(node);
        return componentNode;
    }

    private ComponentNode prepareNodeWithAttributesStructure(Map<String, Object> rootMap) {
        String key = rootMap.keySet().stream().findFirst().orElseThrow(RuntimeException::new);
        ComponentNode componentNode = getNodeFactory().getComponentNodeWithNode();
        componentNode.setNodeName(key);
        Node<String> node = null;
        Map<String, Object> nestedMap = (Map<String, Object>) rootMap.get(key);
        Iterator<Map.Entry<String, Object>> iterator = nestedMap.entrySet().iterator();
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
        iterator = nestedMap.entrySet().iterator();
        Map<String, String> attributes = new HashMap<>();
        while (iterator.hasNext()) {
            Map.Entry<String, Object> currentEntry = iterator.next();
            if (iterator.hasNext()) {
                if (!(currentEntry.getValue() instanceof String)) {
                    throw new RuntimeException("Attribute should be String");
                }
                String value = (String) currentEntry.getValue();
                attributes.put(currentEntry.getKey(), value);
            } else {
                break;
            }
        }
        node.setAttributes(attributes);
        componentNode.setAbstractNode(node);
        return componentNode;
    }
}






