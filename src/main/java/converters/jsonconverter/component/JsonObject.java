package converters.jsonconverter.component;

import converters.components.AbstractNode;
import converters.components.Printer;

public class JsonObject extends AbstractNode {

    private String elementName;

    private AbstractNode abstractNode;

    private Printer printer;

    public JsonObject(Printer printer) {
        this.printer = printer;
    }

    public void setAbstractNode(AbstractNode abstractNode) {
        this.abstractNode = abstractNode;
    }

    @Override
    public String getNodeName() {
        return elementName;
    }

    @Override
    public void setNodeName(String nodeName) {
        this.elementName = nodeName;
    }

    public AbstractNode getAbstractNode() {
        return abstractNode;
    }

    @Override
    public String print() {
        return printer.prepareElement(abstractNode);
    }

}
