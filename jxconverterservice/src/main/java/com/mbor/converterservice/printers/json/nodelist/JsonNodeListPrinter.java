package com.mbor.converterservice.printers.json.nodelist;

import com.mbor.converterservice.components.AbstractNode;
import com.mbor.converterservice.components.IndentationPrinter;
import com.mbor.converterservice.components.NodeList;
import com.mbor.converterservice.utils.CommonUtils;

import java.util.Iterator;

import static com.mbor.converterservice.utils.JsonUtils.*;

public class JsonNodeListPrinter extends IndentationPrinter {
    @Override
    public String prepareElement(AbstractNode abstractNode) {
        NodeList jsonElementList = (NodeList) abstractNode;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(JSON_QUOTE).append(jsonElementList.getNodeName()).append(JSON_QUOTE).append(CommonUtils.EMPTY_SPACE).append(JSON_COLON).append(CommonUtils.EMPTY_SPACE).append(JSON_OPEN_SIGN).append(CommonUtils.NEW_LINE);
        increaseIndentation();
        Iterator<AbstractNode> jsonElementListIterator = jsonElementList.iterator();
        AbstractNode currentElement;
        while (jsonElementListIterator.hasNext()) {
            currentElement = jsonElementListIterator.next();
            currentElement.setPrinterThreadLocal(getThreadLocal());
            stringBuilder.append(CommonUtils.EMPTY_SPACE.repeat(getCurrentIndentation()));
            stringBuilder.append(currentElement.print());
            if (jsonElementListIterator.hasNext()){
                stringBuilder.append(CommonUtils.COMMA);
            }
            stringBuilder.append(CommonUtils.NEW_LINE);
        }

        decreaseIndentation();
        stringBuilder.append(CommonUtils.EMPTY_SPACE.repeat(getCurrentIndentation()));
        stringBuilder.append(JSON_CLOSE_SIGN);
        return stringBuilder.toString();
    }
}
