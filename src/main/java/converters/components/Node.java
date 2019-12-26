package converters.components;

public abstract class Node<T> extends AbstractNode {

    protected T value;

    public Node(){};

    public Node(T value) {
        this.value = value;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }
}
