package converters.json2xml.printer;

import converters.components.AbstractNode;
import converters.components.Printer;

public class XMLComponentNodeWithNodePrinter implements Printer {

    @Override
    public String prepareElement(AbstractNode abstractNode) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(abstractNode.print());
        return stringBuilder.toString();
    }

}
