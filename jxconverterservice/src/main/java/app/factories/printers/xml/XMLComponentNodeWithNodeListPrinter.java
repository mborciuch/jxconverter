package app.factories.printers.xml;

import app.components.AbstractNode;
import app.components.Printer;

public class XMLComponentNodeWithNodeListPrinter implements Printer {

    @Override
    public String prepareElement(AbstractNode abstractNode) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(abstractNode.print());
        return stringBuilder.toString();
    }

}
