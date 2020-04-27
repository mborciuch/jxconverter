package converters.factories;

import converters.components.Printer;
import converters.json2xml.printer.*;

public class JsonPrinterFactory extends AbstractPrinterFactory {

    @Override
    public Printer getComponentNodeWithNodePrinter() {
        return new JsonComponentNodeWithNodePrinter();
    }

    @Override
    public Printer getComponentNodeWithNodeListPrinter() {
        return new JsonComponentNodeWithNodeListPrinter();
    }

    @Override
    public Printer getNodeWithNoValuePrinter() {
        return new JsonNodeWithNoValuePrinter();
    }

    @Override
    public Printer getNodeWithValuePrinter() {
        return new JsonNodeWithValuePrinter();
    }

    @Override
    public Printer getNodeListPrinter() {
        return new JsonNodeListPrinter();
    }

    @Override
    public Printer getNodeEqualListPrinter() {
        return new JsonEqualNodeListPrinter();
    }
}
