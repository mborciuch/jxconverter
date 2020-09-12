package com.mbor.converterservice.factories.printers;

import com.mbor.converterservice.components.Printer;
import com.mbor.converterservice.printers.json.JsonComponentNodeWithNodeListPrinter;
import com.mbor.converterservice.printers.json.JsonComponentNodeWithNodePrinter;
import com.mbor.converterservice.printers.json.equallist.JsonEqualNodeListPrinter;
import com.mbor.converterservice.printers.json.equallist.JsonEqualNodeListWithAttributesPrinter;
import com.mbor.converterservice.printers.json.node.JsonNodeInEqualListPrinter;
import com.mbor.converterservice.printers.json.node.JsonNodePrinter;
import com.mbor.converterservice.printers.json.node.JsonNodeWithAttributesInEqualListPrinter;
import com.mbor.converterservice.printers.json.node.JsonNodeWithAttributesPrinter;
import com.mbor.converterservice.printers.json.nodelist.JsonNodeListPrinter;
import com.mbor.converterservice.printers.json.nodelist.JsonNodeListWithAttributesPrinter;

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
        return null;
    }

    @Override
    public Printer getNodePrinter(){
        return new JsonNodePrinter();
    }

    @Override
    public Printer getNodeWithValuePrinter() {
        return new JsonNodePrinter();
    }

    @Override
    public Printer getNodeListPrinter() {
        return new JsonNodeListPrinter();
    }

    @Override
    public Printer getNodeListWithAttributesPrinter() {
        return new JsonNodeListWithAttributesPrinter();
    }

    @Override
    public Printer getNodeEqualListPrinter() {
        return new JsonEqualNodeListPrinter();
    }

    @Override
    public Printer getNodeWithAttributesPrinter() {
        return new JsonNodeWithAttributesPrinter();
    }

    @Override
    public Printer getNodeInEqualListPrinter() {
        return new JsonNodeInEqualListPrinter();
    }

    @Override
    public Printer getNodeWithAttributesInEqualListPrinter(){return new JsonNodeWithAttributesInEqualListPrinter();}

    @Override
    public Printer getEqualNodeListWithAttributesPrinter() {
        return new JsonEqualNodeListWithAttributesPrinter();
    }
}
