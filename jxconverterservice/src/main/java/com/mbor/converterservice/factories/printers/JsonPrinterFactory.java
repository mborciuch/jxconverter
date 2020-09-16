package com.mbor.converterservice.factories.printers;

import com.mbor.converterservice.components.IIndentationPrinter;
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
    public IIndentationPrinter getComponentNodeWithNodePrinter() {
        return new JsonComponentNodeWithNodePrinter();
    }

    @Override
    public IIndentationPrinter getComponentNodeWithNodeListPrinter() {
        return new JsonComponentNodeWithNodeListPrinter();
    }

    @Override
    public IIndentationPrinter getNodeWithNoValuePrinter() {
        return null;
    }

    @Override
    public IIndentationPrinter getNodePrinter(){
        return new JsonNodePrinter();
    }

    @Override
    public IIndentationPrinter getNodeWithValuePrinter() {
        return new JsonNodePrinter();
    }

    @Override
    public IIndentationPrinter getNodeListPrinter() {
        return new JsonNodeListPrinter();
    }

    @Override
    public IIndentationPrinter getNodeListWithAttributesPrinter() {
        return new JsonNodeListWithAttributesPrinter();
    }

    @Override
    public IIndentationPrinter getNodeEqualListPrinter() {
        return new JsonEqualNodeListPrinter();
    }

    @Override
    public IIndentationPrinter getNodeWithAttributesPrinter() {
        return new JsonNodeWithAttributesPrinter();
    }

    @Override
    public IIndentationPrinter getNodeInEqualListPrinter() {
        return new JsonNodeInEqualListPrinter();
    }

    @Override
    public IIndentationPrinter getNodeWithAttributesInEqualListPrinter(){return new JsonNodeWithAttributesInEqualListPrinter();}

    @Override
    public IIndentationPrinter getEqualNodeListWithAttributesPrinter() {
        return new JsonEqualNodeListWithAttributesPrinter();
    }
}
