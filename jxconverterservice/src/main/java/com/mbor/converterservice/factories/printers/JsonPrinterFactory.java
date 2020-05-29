package com.mbor.converterservice.factories.printers;

import com.mbor.converterservice.components.Printer;
import com.mbor.converterservice.factories.printers.json.*;

public class JsonPrinterFactory extends AbstractPrinterFactory {

    @Override
    public Printer getComponentNodeWithNodePrinter() {
        return new JsonComponentNodeWithNodePrinter();
    }

    @Override
    public Printer getComponentNodeWithNodeListPrinter() {
        return new JsonComponentNodeWithNodeListPrinter();
    }

    @Override
    public Printer getNodeWithNoValuePrinter() {
        return new JsonNodeWithNoValuePrinter();
    }

    @Override
    public Printer getNodeWithValuePrinter() {
        return new JsonNodeWithValuePrinter();
    }

    @Override
    public Printer getNodeListPrinter() {
        return new JsonNodeListPrinter();
    }

    @Override
    public Printer getNodeEqualListPrinter() {
        return new JsonEqualNodeListPrinter();
    }
}
