package com.mbor.converterservice.factories.printers.xml.node;

import com.mbor.converterservice.components.Node;

import static com.mbor.converterservice.utils.XmlUtils.XML_CLOSE_EMPTY_ELEMENT_TAG;

public class XMLNodeWithNoValuePrinter extends XmlNodePrinter {

    @Override
    protected String printValue(Node xmlLine) {
        return XML_CLOSE_EMPTY_ELEMENT_TAG;
    }

}
