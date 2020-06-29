package com.mbor.converterservice.factories.printers.xml.node;

import com.mbor.converterservice.components.AbstractNode;
import com.mbor.converterservice.components.Node;
import com.mbor.converterservice.components.Printer;

import java.util.Map;

import static com.mbor.converterservice.utils.XmlUtils.*;

public class XMLNodeWithNoValuePrinter extends XmlNodePrinter {

    @Override
    protected String printValue(Node xmlLine) {
        return XML_CLOSE_EMPTY_ELEMENT_TAG;
    }

}
