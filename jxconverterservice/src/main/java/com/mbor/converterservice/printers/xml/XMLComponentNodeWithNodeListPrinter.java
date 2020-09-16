package com.mbor.converterservice.printers.xml;

import com.mbor.converterservice.components.AbstractNode;
import com.mbor.converterservice.components.IndentationPrinter;

public class XMLComponentNodeWithNodeListPrinter extends IndentationPrinter {

    @Override
    public String prepareElement(AbstractNode abstractNode) {
        abstractNode.setPrinterThreadLocal(getThreadLocal());
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(abstractNode.print());
        return stringBuilder.toString();
    }

}
