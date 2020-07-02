package com.mbor.converterservice.factories.printers;

import com.mbor.converterservice.components.Printer;

public abstract class AbstractPrinterFactory {

    public abstract Printer getComponentNodeWithNodePrinter();

    public abstract Printer getComponentNodeWithNodeListPrinter();

    public abstract Printer getNodeWithNoValuePrinter();

    public abstract Printer getNodePrinter();

    public abstract  Printer getNodeWithValuePrinter();

    public abstract Printer getNodeListPrinter();

    public abstract Printer getNodeEqualListPrinter();

    public abstract Printer getNodeWithAttributesPrinter();

    public abstract Printer getNodeInEqualListPrinter();

    public abstract Printer getNodeWithAttributesInEqualListPrinter();

    public abstract Printer getNodeListWithAttributesPrinter();

    public abstract Printer getEqualNodeListWithAttributesPrinter();
}
