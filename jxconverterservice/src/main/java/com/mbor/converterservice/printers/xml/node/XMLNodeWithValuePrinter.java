package com.mbor.converterservice.printers.xml.node;

import com.mbor.converterservice.components.Node;

import static com.mbor.converterservice.utils.XmlUtils.XML_CLOSE_ELEMENT_TAG;
import static com.mbor.converterservice.utils.XmlUtils.XML_CLOSE_TAG;

public class XMLNodeWithValuePrinter extends XmlNodePrinter {

    @Override
    protected String printValue(Node xmlLine) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(XML_CLOSE_TAG).append(xmlLine.getValue()).append(XML_CLOSE_ELEMENT_TAG).append(xmlLine.getNodeName()).append(XML_CLOSE_TAG);
        return stringBuilder.toString();
    }
}



