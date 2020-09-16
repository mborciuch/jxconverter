package com.mbor.converterservice.printers.json.equallist;

import com.mbor.converterservice.components.AbstractNode;
import com.mbor.converterservice.components.IndentationPrinter;
import com.mbor.converterservice.components.NodeList;
import com.mbor.converterservice.utils.CommonUtils;

import java.util.Iterator;
import java.util.Map;

import static com.mbor.converterservice.utils.CommonUtils.EMPTY_SPACE;
import static com.mbor.converterservice.utils.CommonUtils.NEW_LINE;
import static com.mbor.converterservice.utils.JsonUtils.*;

public class JsonEqualNodeListWithAttributesPrinter extends IndentationPrinter {
    @Override
    public String prepareElement(AbstractNode abstractNode) {
        NodeList nodelist = (NodeList) abstractNode;
        if(nodelist.getAttributes() == null){
            throw new RuntimeException("Node should have attributes");
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(JSON_QUOTE).append(nodelist.getNodeName()).append(JSON_QUOTE).append(EMPTY_SPACE).append(JSON_COLON).append(EMPTY_SPACE).append(JSON_OPEN_SIGN).append(NEW_LINE);
        increaseIndentation();
        for (Map.Entry<String, String> entry : nodelist.getAttributes().entrySet()) {
            stringBuilder.append(EMPTY_SPACE.repeat(getCurrentIndentation()));
            stringBuilder.append(JSON_QUOTE).append(ATTRIBUTE_SIGN).append(entry.getKey()).append(JSON_QUOTE).append(EMPTY_SPACE).append(JSON_COLON).append(EMPTY_SPACE)
                    .append(JSON_QUOTE).append(entry.getValue()).append(JSON_QUOTE).append(COMMA)
                    .append(NEW_LINE);
        }
        stringBuilder.append(EMPTY_SPACE.repeat(getCurrentIndentation()));
        stringBuilder.append(JSON_QUOTE).append(ELEMENT_SIGN).append(nodelist.getNodeName()).append(JSON_QUOTE).append(CommonUtils.EMPTY_SPACE).append(JSON_COLON).append(CommonUtils.EMPTY_SPACE).append(JSON_LIST_OPEN_SIGN).append(CommonUtils.NEW_LINE);
        increaseIndentation();
        Iterator<AbstractNode> jsonElementListIterator = nodelist.iterator();
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
        stringBuilder.append(EMPTY_SPACE.repeat(getCurrentIndentation()));
        stringBuilder.append(JSON_LIST_CLOSE_SIGN);
        stringBuilder.append(CommonUtils.NEW_LINE);
        decreaseIndentation();
        stringBuilder.append(EMPTY_SPACE.repeat(getCurrentIndentation()));
        stringBuilder.append(JSON_CLOSE_SIGN);
        return stringBuilder.toString();
    }
    
}
