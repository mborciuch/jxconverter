package com.mbor.converterservice.printers.xml;

import com.mbor.converterservice.components.AbstractNode;
import com.mbor.converterservice.components.Printer;

public class XMLComponentNodeWithNodeListPrinter implements Printer {

    @Override
    public String prepareElement(AbstractNode abstractNode) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(abstractNode.print());
        return stringBuilder.toString();
    }

}
