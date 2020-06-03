package com.mbor.converterservice.factories.printers.json;

import com.mbor.converterservice.utils.CommonUtils;
import com.mbor.converterservice.components.AbstractNode;
import com.mbor.converterservice.components.Printer;
import com.mbor.converterservice.converters.abstractconverter.xml2json.Json2xmlConverter;

import static com.mbor.converterservice.utils.JsonUtils.*;

public class JsonComponentNodeWithNodeListPrinter implements Printer {

    @Override
    public String prepareElement(AbstractNode abstractNode) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(JSON_OPEN_SIGN).append(CommonUtils.NEW_LINE);
        Json2xmlConverter.incrementCurrentIndentation();
        stringBuilder.append(CommonUtils.EMPTY_SPACE.repeat(Json2xmlConverter.getCurrentIndentation()));
        stringBuilder.append(abstractNode.print());
        Json2xmlConverter.decrementCurrentIndentation();
        stringBuilder.append(CommonUtils.NEW_LINE).append(CommonUtils.EMPTY_SPACE.repeat(Json2xmlConverter.getCurrentIndentation()));
        stringBuilder.append(JSON_CLOSE_SIGN);
        return stringBuilder.toString();
    }

}
