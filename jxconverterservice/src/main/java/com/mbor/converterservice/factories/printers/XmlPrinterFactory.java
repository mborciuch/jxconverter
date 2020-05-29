package com.mbor.converterservice.factories.printers;

import com.mbor.converterservice.components.Printer;
import com.mbor.converterservice.factories.printers.xml.*;

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
}
