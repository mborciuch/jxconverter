package com.mbor.converterservice.factories.printers;

import com.mbor.converterservice.components.IIndentationPrinter;
import com.mbor.converterservice.printers.xml.XMLComponentNodeWithNodeListPrinter;
import com.mbor.converterservice.printers.xml.XMLComponentNodeWithNodePrinter;
import com.mbor.converterservice.printers.xml.XMLEqualNodeListPrinter;
import com.mbor.converterservice.printers.xml.node.XMLNodeWithNoValuePrinter;
import com.mbor.converterservice.printers.xml.node.XMLNodeWithValuePrinter;
import com.mbor.converterservice.printers.xml.nodelist.XMLNodeListPrinter;

public class XmlPrinterFactory extends AbstractPrinterFactory {

    @Override
    public IIndentationPrinter getComponentNodeWithNodePrinter() {
        return new XMLComponentNodeWithNodePrinter();
    }

    @Override
    public IIndentationPrinter getComponentNodeWithNodeListPrinter() {
        return new XMLComponentNodeWithNodeListPrinter();
    }

    @Override
    public IIndentationPrinter getNodeWithNoValuePrinter() {
        return new XMLNodeWithNoValuePrinter();
    }

    @Override
    public IIndentationPrinter getNodePrinter() {
        return new XMLNodeWithValuePrinter();
    }

    @Override
    public IIndentationPrinter getNodeWithValuePrinter() {
        return new XMLNodeWithValuePrinter();
    }

    @Override
    public IIndentationPrinter getNodeListPrinter() {
        return new XMLNodeListPrinter();
    }

    @Override
    public IIndentationPrinter getNodeEqualListPrinter() {
        return new XMLEqualNodeListPrinter();
    }

    @Override
    public IIndentationPrinter getNodeWithAttributesPrinter() {
        return null;
    }

    @Override
    public IIndentationPrinter getNodeInEqualListPrinter() {
        return null;
    }

    @Override
    public IIndentationPrinter getNodeWithAttributesInEqualListPrinter() {
        return null;
    }

    @Override
    public IIndentationPrinter getNodeListWithAttributesPrinter() {
        return null;
    }

    @Override
    public IIndentationPrinter getEqualNodeListWithAttributesPrinter() {
        return null;
    }
}
