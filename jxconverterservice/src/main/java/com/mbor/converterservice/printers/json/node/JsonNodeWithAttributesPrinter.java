package com.mbor.converterservice.printers.json.node;

import com.mbor.converterservice.components.AbstractNode;
import com.mbor.converterservice.components.IndentationPrinter;
import com.mbor.converterservice.components.Node;
import com.mbor.converterservice.utils.CommonUtils;

import java.util.Map;

import static com.mbor.converterservice.utils.CommonUtils.EMPTY_SPACE;
import static com.mbor.converterservice.utils.CommonUtils.NEW_LINE;
import static com.mbor.converterservice.utils.JsonUtils.*;

public class JsonNodeWithAttributesPrinter extends IndentationPrinter {

    @Override
    public String prepareElement(AbstractNode abstractNode) {
        Node node = (Node) abstractNode;
        if(node.getAttributes() == null){
            throw new RuntimeException("Node should have attributes");
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(JSON_QUOTE).append(node.getNodeName()).append(JSON_QUOTE).append(EMPTY_SPACE).append(JSON_COLON).append(EMPTY_SPACE).append(JSON_OPEN_SIGN).append(CommonUtils.NEW_LINE);
        increaseIndentation();
        for (Map.Entry<String, String> entry : node.getAttributes().entrySet()) {
            stringBuilder.append(EMPTY_SPACE.repeat(getCurrentIndentation()));
            stringBuilder.append(JSON_QUOTE).append(ATTRIBUTE_SIGN).append(entry.getKey()).append(JSON_QUOTE).append(EMPTY_SPACE).append(JSON_COLON).append(EMPTY_SPACE)
                    .append(JSON_QUOTE).append(entry.getValue()).append(JSON_QUOTE).append(COMMA)
                    .append(NEW_LINE);
        }
        stringBuilder.append(EMPTY_SPACE.repeat(getCurrentIndentation()));
        stringBuilder.append(JSON_QUOTE).append(ELEMENT_SIGN).append(node.getNodeName()).append(JSON_QUOTE).append(EMPTY_SPACE).append(JSON_COLON).append(EMPTY_SPACE).append(node.getValue());
        stringBuilder.append(NEW_LINE);
        decreaseIndentation();
        stringBuilder.append(EMPTY_SPACE.repeat(getCurrentIndentation()));
        stringBuilder.append(JSON_CLOSE_SIGN);
        return stringBuilder.toString();
    }

}
