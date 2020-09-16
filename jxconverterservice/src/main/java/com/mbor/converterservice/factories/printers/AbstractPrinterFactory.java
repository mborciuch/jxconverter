package com.mbor.converterservice.factories.printers;

import com.mbor.converterservice.components.IIndentationPrinter;

public abstract class AbstractPrinterFactory {

    public abstract IIndentationPrinter getComponentNodeWithNodePrinter();

    public abstract IIndentationPrinter getComponentNodeWithNodeListPrinter();

    public abstract IIndentationPrinter getNodeWithNoValuePrinter();

    public abstract IIndentationPrinter getNodePrinter();

    public abstract IIndentationPrinter getNodeWithValuePrinter();

    public abstract IIndentationPrinter getNodeListPrinter();

    public abstract IIndentationPrinter getNodeEqualListPrinter();

    public abstract IIndentationPrinter getNodeWithAttributesPrinter();

    public abstract IIndentationPrinter getNodeInEqualListPrinter();

    public abstract IIndentationPrinter getNodeWithAttributesInEqualListPrinter();

    public abstract IIndentationPrinter getNodeListWithAttributesPrinter();

    public abstract IIndentationPrinter getEqualNodeListWithAttributesPrinter();
}
