package app.components;

public  class Node extends AbstractNode {

    private String elementName;

    private Printer printer;

    protected String value;

    public Node(String elementName, Printer printer) {
        this.elementName = elementName;
        this.printer = printer;
    }

    public Node(String elementName, Printer printer, String value) {
        this.elementName = elementName;
        this.printer = printer;
        this.value = value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
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
