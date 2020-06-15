package com.mbor.converterservice.components;

import java.util.Optional;

public class Node extends AbstractNode {

    private String elementName;

    private Printer printer;

    protected String value;

    public Node(String elementName, Printer printer) {
        this.elementName = elementName;
        this.printer = printer;
    }

    public Node(String elementName,  String value,  Printer printer) {
        this.elementName = elementName;
        this.value = value;
        this.printer = printer;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Optional<String> getValue() {
        return Optional.ofNullable(value);
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
