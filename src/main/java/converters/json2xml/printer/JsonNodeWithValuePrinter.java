package converters.json2xml.printer;

import converters.CommonUtils;
import converters.components.AbstractNode;
import converters.components.Node;
import converters.components.Printer;


import static converters.json2xml.JsonUtils.*;

public class JsonNodeWithValuePrinter implements Printer {

    @Override
    public String prepareElement(AbstractNode abstractNode) {
        StringBuilder stringBuilder = new StringBuilder();
        Node jsonLine = (Node) abstractNode;
        stringBuilder
                .append(JSON_QUOTE).append(jsonLine.getNodeName()).append(JSON_QUOTE).append(CommonUtils.EMPTY_SPACE).append(JSON_COLON).append(CommonUtils.EMPTY_SPACE)
                .append(JSON_QUOTE).append(jsonLine.getValue()).append(JSON_QUOTE);
        return stringBuilder.toString();
    }

}
