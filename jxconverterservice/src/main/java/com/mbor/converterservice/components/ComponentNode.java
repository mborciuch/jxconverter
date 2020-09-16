package com.mbor.converterservice.components;

import com.mbor.converterservice.converters.abstractconverter.Indentation;

public class ComponentNode extends AbstractNode {

    private String elementName;

    private AbstractNode abstractNode;

    private IIndentationPrinter printer;

    public ComponentNode(IIndentationPrinter printer) {
        this.printer = printer;
    }

    @Override
    public String getNodeName() {
        return elementName;
    }

    @Override
    public void setNodeName(String nodeName) {
        this.elementName = nodeName;
    }

    public AbstractNode getAbstractNode() {
        return abstractNode;
    }

    public void setAbstractNode(AbstractNode abstractNode) {
        this.abstractNode = abstractNode;
    }

    @Override
    public String print() {
        return printer.prepareElement(abstractNode);
    }

    @Override
    public void setPrinterThreadLocal(ThreadLocal<Indentation> threadLocal) {
        printer.setThreadLocal(threadLocal);
    }
}
