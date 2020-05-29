package app.factories.printers.json;

import app.utils.CommonUtils;
import app.components.AbstractNode;
import app.components.Printer;

import static app.utils.JsonUtils.JSON_COLON;
import static app.utils.JsonUtils.JSON_QUOTE;

public class JsonNodeWithNoValuePrinter implements Printer {

    @Override
    public String prepareElement(AbstractNode abstractNode) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(JSON_QUOTE);
        stringBuilder.append(abstractNode.getNodeName()).append(JSON_QUOTE).append(CommonUtils.EMPTY_SPACE).append(JSON_COLON).append(CommonUtils.EMPTY_SPACE).append(CommonUtils.NULL);
        return stringBuilder.toString();
    }


}
