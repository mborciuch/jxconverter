package converters.jsonconverter;

import converters.components.Printer;
import converters.jsonconverter.printer.*;

public class JsonNodePrinterFactory {

    public static Printer getJsonNodeEmptyPrinter(){
        return new JsonNodeEmptyPrinter();
    }

    public static Printer getJsonNodeWithValuePrinter(){
        return new JsonNodeWithValuePrinter();
    }

    public static Printer getJsonNodeEqualListPrinter(){
        return new JsonEqualNodeListPrinter();
    }

    public static Printer getJsonNodeListPrinter(){
        return new JsonNodePrinter();
    }

    public static Printer getJsonObjectWithNodePrinter(){
        return new JsonObjectWithNodePrinter();
    }

    public static Printer getJsonObjectWithNodeListPrinter(){
        return new JsonObjectWithNodeListPrinter();
    }

}
