package com.mbor.converterservice.factories.printers.xml;

import com.mbor.converterservice.utils.CommonUtils;
import com.mbor.converterservice.components.AbstractNode;
import com.mbor.converterservice.components.NodeList;
import com.mbor.converterservice.components.Printer;
import com.mbor.converterservice.converters.xml2json.Json2xmlConverter;

import static com.mbor.converterservice.utils.JsonUtils.*;

public class XMLEqualNodeListPrinter implements Printer {

    @Override
    public String prepareElement(AbstractNode abstractNode) {
        NodeList jsonEqualNodeList = (NodeList) abstractNode;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(JSON_QUOTE).append(jsonEqualNodeList.getNodeName()).append(JSON_QUOTE).append(CommonUtils.EMPTY_SPACE).append(JSON_COLON).append(CommonUtils.EMPTY_SPACE).append(JSON_LIST_OPEN_SIGN).append(CommonUtils.NEW_LINE);
        Json2xmlConverter.incrementCurrentIndentation();
        for (AbstractNode currentElement : jsonEqualNodeList) {
            stringBuilder.append(CommonUtils.EMPTY_SPACE.repeat(Json2xmlConverter.getCurrentIndentation()));
            stringBuilder.append(currentElement.print());
            stringBuilder.append(CommonUtils.NEW_LINE);
        }
        Json2xmlConverter.decrementCurrentIndentation();
        stringBuilder.append(CommonUtils.EMPTY_SPACE.repeat(Json2xmlConverter.getCurrentIndentation()));
        stringBuilder.append(JSON_LIST_CLOSE_SIGN);
        return stringBuilder.toString();
    }

}
