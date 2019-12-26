package converters.xmlconverter.components;

import converters.components.AbstractNode;
import converters.components.Printer;

public class XmlObjectWithLinePrinter implements Printer {

    @Override
    public String prepareElement(AbstractNode abstractNode) {
        return abstractNode.print();
    }
}
