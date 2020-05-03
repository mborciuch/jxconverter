package converters.json2xml;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import converters.AbstractConverter;
import converters.components.ComponentNode;
import converters.components.Node;
import converters.factories.NodeFactory;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

public class XmlConverter extends AbstractConverter {

    private final ObjectMapper objectMapper;

    public XmlConverter(NodeFactory nodeFactory, ObjectMapper objectMapper) {
        super(nodeFactory);
        this.objectMapper = objectMapper;
    }

    @Override
    public String convert(String input) {
        LinkedHashMap<String, Object> map = null;
        try {
            map = (LinkedHashMap<String, Object>) objectMapper.readValue(input, Map.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        String key;
        if (!(map.keySet().size() > 1)) {
            Optional<String> result = map.keySet().stream().findFirst();
            if (result.isPresent()) {
                key = result.get();
            } else {
                throw new RuntimeException("There is no root element");
            }
        } else {
            throw new RuntimeException("There should be only one root element");
        }
        ComponentNode componentNode;
        if (map.get(key) instanceof LinkedHashMap) {
            componentNode = getNodeFactory().getComponentNodeWithNodeList();
        } else {
            componentNode = getNodeFactory().getComponentNodeWithNode();
            componentNode.setNodeName(key);
            Node<String> node;
            if (map.get(key) != null) {
                node = getNodeFactory().getNodeWithValue(key, map.get(key));
            } else {
                node = getNodeFactory().getNodeWithNoValue(key);
            }
            componentNode.setAbstractNode(node);
        }
        return componentNode.print();
    }
}

