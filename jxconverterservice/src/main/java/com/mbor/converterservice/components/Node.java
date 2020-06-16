package com.mbor.converterservice.components;

import com.mbor.converterservice.components.ValueObject.AbstractValueObject;

public class Node extends AbstractNode {

    private String elementName;

    private Printer printer;

    protected AbstractValueObject value;

    public Node(String elementName, Printer printer) {
        this.elementName = elementName;
        this.printer = printer;
    }

    public Node(String elementName,  AbstractValueObject value,  Printer printer) {
        this.elementName = elementName;
        this.value = value;
        this.printer = printer;
    }

    public String getValue() {
        return value.getValue();
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
}
