package com.mbor.converterservice.components;

import com.mbor.converterservice.components.ValueObject.AbstractValueObject;
import com.mbor.converterservice.converters.abstractconverter.Indentation;

public class Node extends AbstractNode {

    private String elementName;

    private IIndentationPrinter printer;

    protected AbstractValueObject value;

    public Node(String elementName, IIndentationPrinter printer) {
        this.elementName = elementName;
        this.printer = printer;
    }

    public Node(String elementName,  AbstractValueObject value,  IIndentationPrinter printer) {
        this.elementName = elementName;
        this.value = value;
        this.printer = printer;
    }

    public String getValue() {
        return value.getValue();
    }

    public AbstractValueObject getValueObject(){
        return value;
    }

    @Override
    public String getNodeName() {
        return elementName;
    }

    @Override
    public void setNodeName(String nodeName) {
        this.elementName = nodeName;
    }

    @Override
    public String print() {
        return printer.prepareElement(this);
    }

    @Override
    public void setPrinterThreadLocal(ThreadLocal<Indentation> threadLocal) {
        printer.setThreadLocal(threadLocal);
    }
}
