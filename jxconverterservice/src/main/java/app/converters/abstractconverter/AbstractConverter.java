package app.converters.abstractconverter;

import app.factories.NodeFactory;

public abstract class AbstractConverter implements Converter {

    private NodeFactory nodeFactory;

    public AbstractConverter(NodeFactory nodeFactory) {
        this.nodeFactory = nodeFactory;
    }

    public NodeFactory getNodeFactory() {
        return nodeFactory;
    }
}
