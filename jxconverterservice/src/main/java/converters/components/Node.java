package converters.components;

public  class Node<T> extends AbstractNode {

    private String elementName;

    private Printer printer;

    protected T value;

    public Node(){};

    public Node(String elementName, Printer printer) {
        this.elementName = elementName;
        this.printer = printer;
    }

    public Node(String elementName, Printer printer, T value) {
        this.elementName = elementName;
        this.printer = printer;
        this.value = value;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
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
