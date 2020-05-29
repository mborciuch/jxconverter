package app.factories;


import app.components.ComponentNode;
import app.components.Node;
import app.components.NodeList;
import app.factories.printers.AbstractPrinterFactory;


public class NodeFactory {

   private final AbstractPrinterFactory abstractPrinterFactory;

    public NodeFactory(AbstractPrinterFactory abstractPrinterFactory) {
        this.abstractPrinterFactory = abstractPrinterFactory;
    }

    public ComponentNode getComponentNodeWithNode(){
        return new ComponentNode(abstractPrinterFactory.getComponentNodeWithNodePrinter());
    }

    public  ComponentNode getComponentNodeWithNodeList(){
        return new ComponentNode(abstractPrinterFactory.getComponentNodeWithNodeListPrinter());
    }

    public Node getNodeWithNoValue(String elementName){
        return new Node(elementName, abstractPrinterFactory.getNodeWithNoValuePrinter());
    }
    public  Node getNodeWithValue(String elementName, String value){
        return new Node(elementName, abstractPrinterFactory.getNodeWithValuePrinter(),  value);
    }

    public NodeList getNodeList(String elementName){
        return new NodeList(elementName, abstractPrinterFactory.getNodeListPrinter());
    }

    public  NodeList getEqualNodeList(String elementName){
       return new NodeList(elementName, abstractPrinterFactory.getNodeEqualListPrinter());
    }


}
