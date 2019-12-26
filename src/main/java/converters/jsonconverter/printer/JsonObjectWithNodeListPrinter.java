package converters.jsonconverter.printer;

import converters.CommonUtils;
import converters.components.AbstractNode;
import converters.components.Printer;
import converters.jsonconverter.JsonConverter;

import static converters.jsonconverter.JsonUtils.*;

public class JsonObjectWithNodeListPrinter implements Printer {

    @Override
    public String prepareElement(AbstractNode abstractNode) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(JSON_OPEN_SIGN).append(CommonUtils.NEW_LINE);
        JsonConverter.incrementCurrentIndentation();
        stringBuilder.append(CommonUtils.EMPTY_SPACE.repeat(JsonConverter.getCurrentIndentation()));
        stringBuilder.append(abstractNode.print());
        JsonConverter.decrementCurrentIndentation();
        stringBuilder.append(CommonUtils.NEW_LINE).append(CommonUtils.EMPTY_SPACE.repeat(JsonConverter.getCurrentIndentation()));
        stringBuilder.append(JSON_CLOSE_SIGN);
        return stringBuilder.toString();
    }

}
