package com.mbor.converterservice.printers.json.node;

import com.mbor.converterservice.components.AbstractNode;
import com.mbor.converterservice.components.IndentationPrinter;
import com.mbor.converterservice.components.Node;

public class JsonNodeInEqualListPrinter extends IndentationPrinter {
    @Override
    public String prepareElement(AbstractNode abstractNode) {
        if(!(abstractNode  instanceof Node)){
            throw new RuntimeException("Should be instance of node");
        }
        Node node = (Node) abstractNode;
        return node.getValue();
    }
}
