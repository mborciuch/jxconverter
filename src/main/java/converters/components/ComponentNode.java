package converters.components;

public class ComponentNode extends AbstractNode {

    private String elementName;

    private AbstractNode abstractNode;

    private Printer printer;

    public ComponentNode(Printer printer) {
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

    public void setAbstractNode(AbstractNode abstractNode) {
        this.abstractNode = abstractNode;
    }

    public AbstractNode getAbstractNode() {
        return abstractNode;
    }

    @Override
    public String print() {
        return printer.prepareElement(abstractNode);
    }
}
