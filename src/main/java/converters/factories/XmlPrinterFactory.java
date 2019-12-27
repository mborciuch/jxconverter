package converters.factories;

import converters.components.Printer;

public class XmlPrinterFactory extends AbstractPrinterFactory {
    @Override
    public Printer getComponentNodeWithNodePrinter() {
        return null;
    }

    @Override
    public Printer getComponentNodeWithNodeListPrinter() {
        return null;
    }

    @Override
    public Printer getNodeWithNoValuePrinter() {
        return null;
    }

    @Override
    public Printer getNodeWithValuePrinter() {
        return null;
    }

    @Override
    public Printer getNodeListPrinter() {
        return null;
    }

    @Override
    public Printer getNodeEqualListPrinter() {
        return null;
    }
}
