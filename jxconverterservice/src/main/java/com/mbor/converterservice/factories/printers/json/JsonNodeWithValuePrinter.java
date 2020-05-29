package com.mbor.converterservice.factories.printers.json;

import com.mbor.converterservice.utils.CommonUtils;
import com.mbor.converterservice.components.AbstractNode;
import com.mbor.converterservice.components.Node;
import com.mbor.converterservice.components.Printer;


import static com.mbor.converterservice.utils.JsonUtils.*;

public class JsonNodeWithValuePrinter implements Printer {

    @Override
    public String prepareElement(AbstractNode abstractNode) {
        StringBuilder stringBuilder = new StringBuilder();
        Node jsonLine = (Node) abstractNode;
        stringBuilder
                .append(JSON_QUOTE).append(jsonLine.getNodeName()).append(JSON_QUOTE).append(CommonUtils.EMPTY_SPACE).append(JSON_COLON).append(CommonUtils.EMPTY_SPACE)
                .append(JSON_QUOTE).append(jsonLine.getValue()).append(JSON_QUOTE);
        return stringBuilder.toString();
    }

}
