package converters.components;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public abstract class NodeList extends AbstractNode implements Iterable<AbstractNode> {

    private List<AbstractNode> list;

    public NodeList() {
        this.list = new ArrayList<>();
    }

    @Override
    public Iterator<AbstractNode> iterator() {
        return list.iterator();
    }

    public List<AbstractNode> getList() {
        return list;
    }

    public void setList(List<AbstractNode> list) {
        this.list = list;
    }

    public void addAbstractElement(AbstractNode abstractNode) {
        list.add(abstractNode);
    }
}



