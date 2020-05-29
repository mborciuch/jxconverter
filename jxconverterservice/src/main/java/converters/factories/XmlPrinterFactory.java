package converters.factories;

import converters.components.Printer;
import converters.json2xml.printer.*;

public class XmlPrinterFactory extends AbstractPrinterFactory {
    @Override
    public Printer getComponentNodeWithNodePrinter() {
        return new XMLComponentNodeWithNodePrinter();
    }

    @Override
    public Printer getComponentNodeWithNodeListPrinter() {
        return new XMLComponentNodeWithNodeListPrinter();
    }

    @Override
    public Printer getNodeWithNoValuePrinter() {
        return new XMLNodeWithNoValuePrinter();
    }

    @Override
    public Printer getNodeWithValuePrinter() {
        return new XMLNodeWithValuePrinter();
    }

    @Override
    public Printer getNodeListPrinter() {
        return new XMLNodeListPrinter();
    }

    @Override
    public Printer getNodeEqualListPrinter() {
        return new XMLEqualNodeListPrinter();
    }
}
