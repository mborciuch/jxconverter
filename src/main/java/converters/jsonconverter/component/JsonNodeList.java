package converters.jsonconverter.component;

import converters.components.NodeList;
import converters.components.Printer;

public class JsonNodeList extends NodeList {

    private String elementName;

    private boolean isEqualNodeList = false;

    private Printer printer;

    public JsonNodeList(String elementName, Printer printer) {
        this.elementName = elementName;
        this.printer = printer;
    }

    @Override
    public String getNodeName() {
        return this.elementName;
    }

    @Override
    public void setNodeName(String nodeName) {
        this.elementName = nodeName;
    }

    public String getElementName() {
        return elementName;
    }

    public void setElementName(String elementName) {
        this.elementName = elementName;
    }

    public boolean isEqualNodeList() {
        return isEqualNodeList;
    }

    public void setEqualNodeList(boolean equalNodeList) {
        isEqualNodeList = equalNodeList;
    }

    @Override
    public String print() {
        return printer.prepareElement(this);
    }

}
