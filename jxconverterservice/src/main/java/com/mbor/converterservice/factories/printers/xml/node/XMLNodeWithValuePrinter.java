package com.mbor.converterservice.factories.printers.xml.node;

import com.mbor.converterservice.components.AbstractNode;
import com.mbor.converterservice.components.Node;
import com.mbor.converterservice.components.Printer;

import java.util.Map;

import static com.mbor.converterservice.utils.XmlUtils.*;

public class XMLNodeWithValuePrinter extends XmlNodePrinter {

    @Override
    protected String printValue(Node xmlLine) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(XML_CLOSE_TAG).append(xmlLine.getValue()).append(XML_CLOSE_ELEMENT_TAG).append(xmlLine.getNodeName()).append(XML_CLOSE_TAG);
        return stringBuilder.toString();
    }
}



