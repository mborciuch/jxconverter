package converters;

import converters.factories.NodeFactory;

public abstract class AbstractConverter {

    private NodeFactory nodeFactory;

    public AbstractConverter(NodeFactory nodeFactory) {
        this.nodeFactory = nodeFactory;
    }

    public abstract String convert(String input);

    public NodeFactory getNodeFactory() {
        return nodeFactory;
    }
}
