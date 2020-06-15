package com.mbor.converterservice.factories.printers.json;

import com.mbor.converterservice.utils.CommonUtils;
import com.mbor.converterservice.components.AbstractNode;
import com.mbor.converterservice.components.Printer;

import static com.mbor.converterservice.utils.JsonUtils.JSON_COLON;
import static com.mbor.converterservice.utils.JsonUtils.JSON_QUOTE;

public class JsonNodeWithNoValuePrinter implements Printer {

    @Override
    public String prepareElement(AbstractNode abstractNode) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(JSON_QUOTE);
        stringBuilder
                .append(abstractNode.getNodeName()).append(JSON_QUOTE).append(CommonUtils.EMPTY_SPACE).append(JSON_COLON).append(CommonUtils.EMPTY_SPACE).append(CommonUtils.NULL);
        return stringBuilder.toString();
    }


}
