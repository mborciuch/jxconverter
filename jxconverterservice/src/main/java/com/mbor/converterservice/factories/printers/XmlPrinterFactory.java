package com.mbor.converterservice.factories.printers;

import com.mbor.converterservice.components.Printer;
import com.mbor.converterservice.printers.xml.XMLComponentNodeWithNodeListPrinter;
import com.mbor.converterservice.printers.xml.XMLComponentNodeWithNodePrinter;
import com.mbor.converterservice.printers.xml.XMLEqualNodeListPrinter;
import com.mbor.converterservice.printers.xml.node.XMLNodeWithNoValuePrinter;
import com.mbor.converterservice.printers.xml.node.XMLNodeWithValuePrinter;
import com.mbor.converterservice.printers.xml.nodelist.XMLNodeListPrinter;

public class XmlPrinterFactory extends AbstractPrinterFactory {
    @Override
    public Printer getComponentNodeWithNodePrinter() {
        return new XMLComponentNodeWithNodePrinter();
    }

    @Override
    public Printer getComponentNodeWithNodeListPrinter() {
        return new XMLComponentNodeWithNodeListPrinter();
    }

    @Override
    public Printer getNodeWithNoValuePrinter() {
        return new XMLNodeWithNoValuePrinter();
    }

    @Override
    public Printer getNodePrinter() {
        return new XMLNodeWithValuePrinter();
    }

    @Override
    public Printer getNodeWithValuePrinter() {
        return new XMLNodeWithValuePrinter();
    }

    @Override
    public Printer getNodeListPrinter() {
        return new XMLNodeListPrinter();
    }

    @Override
    public Printer getNodeEqualListPrinter() {
        return new XMLEqualNodeListPrinter();
    }

    @Override
    public Printer getNodeWithAttributesPrinter() {
        return null;
    }

    @Override
    public Printer getNodeInEqualListPrinter() {
        return null;
    }

    @Override
    public Printer getNodeWithAttributesInEqualListPrinter() {
        return null;
    }

    @Override
    public Printer getNodeListWithAttributesPrinter() {
        return null;
    }

    @Override
    public Printer getEqualNodeListWithAttributesPrinter() {
        return null;
    }
}
