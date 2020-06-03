package com.mbor.converterservice.converters.abstractconverter;

import com.mbor.converterservice.factories.nodes.NodeFactory;

 public abstract class AbstractConverter implements Converter {

    private NodeFactory nodeFactory;

    public AbstractConverter(NodeFactory nodeFactory) {
        this.nodeFactory = nodeFactory;
    }

    public NodeFactory getNodeFactory() {
        return nodeFactory;
    }
}
