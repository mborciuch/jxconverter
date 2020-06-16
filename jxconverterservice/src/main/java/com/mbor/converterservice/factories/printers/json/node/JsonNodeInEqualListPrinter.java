package com.mbor.converterservice.factories.printers.json.node;

import com.mbor.converterservice.components.AbstractNode;
import com.mbor.converterservice.components.Node;
import com.mbor.converterservice.components.Printer;

public class JsonNodeInEqualListPrinter implements Printer {
    @Override
    public String prepareElement(AbstractNode abstractNode) {
        if(!(abstractNode  instanceof Node)){
            throw new RuntimeException("Should be instance of node");
        }
        Node node = (Node) abstractNode;
        return node.getValue();
    }
}
