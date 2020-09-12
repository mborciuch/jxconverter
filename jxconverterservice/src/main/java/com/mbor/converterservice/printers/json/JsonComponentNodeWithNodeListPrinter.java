package com.mbor.converterservice.printers.json;

import com.mbor.converterservice.components.AbstractNode;
import com.mbor.converterservice.components.Printer;
import com.mbor.converterservice.converters.abstractconverter.xml2json.Xml2JsonConverter;
import com.mbor.converterservice.utils.CommonUtils;

import static com.mbor.converterservice.utils.JsonUtils.JSON_CLOSE_SIGN;
import static com.mbor.converterservice.utils.JsonUtils.JSON_OPEN_SIGN;

public class JsonComponentNodeWithNodeListPrinter implements Printer {

    @Override
    public String prepareElement(AbstractNode abstractNode) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(JSON_OPEN_SIGN).append(CommonUtils.NEW_LINE);
        Xml2JsonConverter.incrementCurrentIndentation();
        stringBuilder.append(CommonUtils.EMPTY_SPACE.repeat(Xml2JsonConverter.getCurrentIndentation()));
        stringBuilder.append(abstractNode.print());
        Xml2JsonConverter.decrementCurrentIndentation();
        stringBuilder.append(CommonUtils.NEW_LINE).append(CommonUtils.EMPTY_SPACE.repeat(Xml2JsonConverter.getCurrentIndentation()));
        stringBuilder.append(JSON_CLOSE_SIGN);
        return stringBuilder.toString();
    }

}