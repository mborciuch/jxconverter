package com.mbor.converterservice.factories.nodes;


import com.mbor.converterservice.components.*;
import com.mbor.converterservice.components.ValueObject.AbstractValueObject;
import com.mbor.converterservice.components.ValueObject.XmlNullValueObject;
import com.mbor.converterservice.factories.printers.AbstractPrinterFactory;

import java.util.LinkedList;
import java.util.List;

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

    public Node getNode(String elementName, AbstractValueObject abstractValueObject) {
        Printer printer;
        if(abstractValueObject instanceof XmlNullValueObject){
            printer = abstractPrinterFactory.getNodeWithNoValuePrinter();
        } else {
            printer = abstractPrinterFactory.getNodeWithValuePrinter();
        }
        return new Node(elementName, abstractValueObject,  printer);
    }
    public Node getNodeWithAttributes(String elementName, AbstractValueObject abstractValueObject){
        return new Node(elementName,  abstractValueObject, abstractPrinterFactory.getNodeWithAttributesPrinter());
    }


    public NodeList getNodeList(String elementName){
        return new NodeList(elementName, abstractPrinterFactory.getNodeListPrinter());
    }

    public NodeList getNodeListWithAttributes(String elementName){
        return new  NodeList(elementName, abstractPrinterFactory.getNodeListWithAttributesPrinter());
    }

    public NodeList getNodeListWithAttributes(NodeList nodeList){
        NodeList newNodeList = new NodeList(nodeList.getNodeName(), abstractPrinterFactory.getNodeListWithAttributesPrinter());
        List<AbstractNode> abstractNodes = getAbstractNodes(nodeList);
        newNodeList.setList(abstractNodes);
        return newNodeList;
    }


    public  NodeList getEqualNodeList(NodeList nodeList){
        NodeList equalNodeList =  new NodeList(nodeList.getNodeName(), abstractPrinterFactory.getNodeEqualListPrinter());
        List<AbstractNode> abstractNodes = getAbstractNodes(nodeList);
        equalNodeList.setList(abstractNodes);
        return equalNodeList;
    }

    public NodeList getEqualNodeListWithAttributes(NodeList nodeList) {
        NodeList equalNodeList = new NodeList(nodeList.getNodeName(), abstractPrinterFactory.getEqualNodeListWithAttributesPrinter());
        List<AbstractNode> abstractNodes = getAbstractNodes(nodeList);
        if(nodeList.getAttributes() == null){
            throw new RuntimeException("NodeList should have attributes");
        }
        equalNodeList.setList(abstractNodes);
        equalNodeList.setAttributes(nodeList.getAttributes());
        return equalNodeList;
    }

    public Node getNodeInEqualList(Node node){
        return new Node(node.getNodeName(), node.getValueObject(), abstractPrinterFactory.getNodeInEqualListPrinter());
    }

    public Node getNodeWithAttributesInEqualList(Node node){
        Node newNode =  new Node(node.getNodeName(), node.getValueObject(), abstractPrinterFactory.getNodeWithAttributesInEqualListPrinter());
        newNode.setAttributes(node.getAttributes());
        return newNode;
    }
    private List<AbstractNode> getAbstractNodes(NodeList nodeList) {
        List<AbstractNode> abstractNodes = new LinkedList<>();
        nodeList.forEach(element ->{
            if(element instanceof Node){
                if(element.hasAttributes()){
                    element = getNodeWithAttributesInEqualList((Node) element);
                } else {
                    element = getNodeInEqualList((Node) element);
                }
            }
            abstractNodes.add(element);
        });
        return abstractNodes;
    }


}
