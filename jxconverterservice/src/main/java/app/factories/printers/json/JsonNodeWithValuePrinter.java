package app.factories.printers.json;

import app.utils.CommonUtils;
import app.components.AbstractNode;
import app.components.Node;
import app.components.Printer;


import static app.utils.JsonUtils.*;

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
