package app.factories.printers.json;

import app.components.AbstractNode;
import app.components.Printer;

import static app.utils.JsonUtils.JSON_CLOSE_SIGN;
import static app.utils.JsonUtils.JSON_OPEN_SIGN;

public class JsonComponentNodeWithNodePrinter implements Printer {

    @Override
    public String prepareElement(AbstractNode abstractNode) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(JSON_OPEN_SIGN);
        stringBuilder.append(abstractNode.print());
        stringBuilder.append(JSON_CLOSE_SIGN);
        return stringBuilder.toString();
    }

}
