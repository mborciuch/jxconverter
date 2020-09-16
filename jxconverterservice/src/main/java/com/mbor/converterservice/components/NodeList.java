package com.mbor.converterservice.components;

import com.mbor.converterservice.converters.abstractconverter.Indentation;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

 public class NodeList extends AbstractNode implements Iterable<AbstractNode> {

    private String elementName;

    private List<AbstractNode> list;

    private IIndentationPrinter printer;

    public NodeList(String elementName, IIndentationPrinter printer) {
        this.elementName = elementName;
        this.printer = printer;
        this.list = new ArrayList<>();
    }

    @Override
    public Iterator<AbstractNode> iterator() {
        return list.iterator();
    }

    @Override
    public String getNodeName() {
        return this.elementName;
    }

    @Override
    public void setNodeName(String nodeName) {
        this.elementName = nodeName;
    }

    public List<AbstractNode> getList() {
        return list;
    }

    public void setList(List<AbstractNode> list) {
        this.list = list;
    }

    public void addAbstractElement(AbstractNode abstractNode) {
        list.add(abstractNode);
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



