package com.mbor.converterservice.printers.json;

import com.mbor.converterservice.components.AbstractNode;
import com.mbor.converterservice.components.IndentationPrinter;
import com.mbor.converterservice.utils.CommonUtils;

import static com.mbor.converterservice.utils.JsonUtils.JSON_CLOSE_SIGN;
import static com.mbor.converterservice.utils.JsonUtils.JSON_OPEN_SIGN;

public class JsonComponentNodeWithNodeListPrinter extends IndentationPrinter {

    @Override
    public String prepareElement(AbstractNode abstractNode) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(JSON_OPEN_SIGN).append(CommonUtils.NEW_LINE);
        increaseIndentation();
        stringBuilder.append(CommonUtils.EMPTY_SPACE.repeat(getCurrentIndentation()));
        abstractNode.setPrinterThreadLocal(getThreadLocal());
        stringBuilder.append(abstractNode.print());
        decreaseIndentation();
        stringBuilder.append(CommonUtils.NEW_LINE).append(CommonUtils.EMPTY_SPACE.repeat(getCurrentIndentation()));
        stringBuilder.append(JSON_CLOSE_SIGN);
        return stringBuilder.toString();
    }

}
