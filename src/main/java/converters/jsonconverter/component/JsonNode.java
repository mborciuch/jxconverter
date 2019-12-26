package converters.jsonconverter.component;

import converters.components.Node;
import converters.components.Printer;

public class JsonNode extends Node {

    private String elementName;

    private Printer printer;

    public JsonNode(String elementName, Printer printer) {
        this.elementName = elementName;
        this.printer = printer;
    }
    public JsonNode(String elementName, Object value, Printer printer ) {
        super(value);
        this.elementName = elementName;
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

    @Override
    public String print() {
        return printer.prepareElement(this);
    }

}
