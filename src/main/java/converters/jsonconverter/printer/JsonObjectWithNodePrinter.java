package converters.jsonconverter.printer;

import converters.components.AbstractNode;
import converters.components.Printer;

import static converters.jsonconverter.JsonUtils.JSON_CLOSE_SIGN;
import static converters.jsonconverter.JsonUtils.JSON_OPEN_SIGN;

public class JsonObjectWithNodePrinter implements Printer {

    @Override
    public String prepareElement(AbstractNode abstractNode) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(JSON_OPEN_SIGN);
        stringBuilder.append(abstractNode.print());
        stringBuilder.append(JSON_CLOSE_SIGN);
        return stringBuilder.toString();
    }

}
