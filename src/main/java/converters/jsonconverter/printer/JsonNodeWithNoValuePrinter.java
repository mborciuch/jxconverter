package converters.jsonconverter.printer;

import converters.CommonUtils;
import converters.components.AbstractNode;
import converters.components.Printer;

import static converters.jsonconverter.JsonUtils.JSON_COLON;
import static converters.jsonconverter.JsonUtils.JSON_QUOTE;

public class JsonNodeWithNoValuePrinter implements Printer {

    @Override
    public String prepareElement(AbstractNode abstractNode) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(JSON_QUOTE);
        stringBuilder.append(abstractNode.getNodeName()).append(JSON_QUOTE).append(CommonUtils.EMPTY_SPACE).append(JSON_COLON).append(CommonUtils.EMPTY_SPACE).append(CommonUtils.NULL);
        return stringBuilder.toString();
    }


}
