package com.mbor.converterservice.printers.json.node;

import com.mbor.converterservice.components.AbstractNode;
import com.mbor.converterservice.components.IndentationPrinter;
import com.mbor.converterservice.components.Node;
import com.mbor.converterservice.utils.CommonUtils;

import static com.mbor.converterservice.utils.JsonUtils.JSON_COLON;
import static com.mbor.converterservice.utils.JsonUtils.JSON_QUOTE;

public class JsonNodePrinter extends IndentationPrinter {

    @Override
    public String prepareElement(AbstractNode abstractNode) {
        Node node = (Node) abstractNode;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder
                .append(JSON_QUOTE).append(node.getNodeName()).append(JSON_QUOTE).append(CommonUtils.EMPTY_SPACE).append(JSON_COLON).append(CommonUtils.EMPTY_SPACE)
                .append(node.getValue());
        return stringBuilder.toString();
    }

}
