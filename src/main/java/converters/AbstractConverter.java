package converters;

import converters.components.AbstractNode;

public abstract class AbstractConverter {

    protected AbstractNode abstractNode = null;

    public AbstractNode getAbstractNode() {
        return abstractNode;
    }

    public void setAbstractNode(AbstractNode abstractNode) {
        this.abstractNode = abstractNode;
    }

    public abstract String convert(String input);
}
