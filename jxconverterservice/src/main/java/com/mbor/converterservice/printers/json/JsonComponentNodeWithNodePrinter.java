package com.mbor.converterservice.printers.json;

import com.mbor.converterservice.components.AbstractNode;
import com.mbor.converterservice.components.IndentationPrinter;

import static com.mbor.converterservice.utils.JsonUtils.JSON_CLOSE_SIGN;
import static com.mbor.converterservice.utils.JsonUtils.JSON_OPEN_SIGN;

public class JsonComponentNodeWithNodePrinter extends IndentationPrinter {

    @Override
    public String prepareElement(AbstractNode abstractNode) {
        abstractNode.setPrinterThreadLocal(getThreadLocal());
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(JSON_OPEN_SIGN);
        stringBuilder.append(abstractNode.print());
        stringBuilder.append(JSON_CLOSE_SIGN);
        return stringBuilder.toString();
    }

}
