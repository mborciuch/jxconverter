package com.mbor.converterservice.factories.printers.json;

import com.mbor.converterservice.components.AbstractNode;
import com.mbor.converterservice.components.Printer;

import static com.mbor.converterservice.utils.JsonUtils.JSON_CLOSE_SIGN;
import static com.mbor.converterservice.utils.JsonUtils.JSON_OPEN_SIGN;

public class JsonComponentNodeWithNodePrinter implements Printer {

    @Override
    public String prepareElement(AbstractNode abstractNode) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(JSON_OPEN_SIGN);
        stringBuilder.append(abstractNode.print());
        stringBuilder.append(JSON_CLOSE_SIGN);
        return stringBuilder.toString();
    }

}
