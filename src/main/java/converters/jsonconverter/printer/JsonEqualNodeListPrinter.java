package converters.jsonconverter.printer;

import converters.CommonUtils;
import converters.components.AbstractNode;
import converters.components.Printer;
import converters.jsonconverter.JsonConverter;
import converters.jsonconverter.component.JsonNodeList;

import static converters.jsonconverter.JsonUtils.*;

public class JsonEqualNodeListPrinter implements Printer {

    @Override
    public String prepareElement(AbstractNode abstractNode) {
        JsonNodeList jsonEqualNodeList = (JsonNodeList) abstractNode;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(JSON_QUOTE).append(jsonEqualNodeList.getNodeName()).append(JSON_QUOTE).append(CommonUtils.EMPTY_SPACE).append(JSON_COLON).append(CommonUtils.EMPTY_SPACE).append(JSON_LIST_OPEN_SIGN).append(CommonUtils.NEW_LINE);
        JsonConverter.incrementCurrentIndentation();
        for (AbstractNode currentElement : jsonEqualNodeList) {
            stringBuilder.append(CommonUtils.EMPTY_SPACE.repeat(JsonConverter.getCurrentIndentation()));
            stringBuilder.append(currentElement.print());
            stringBuilder.append(CommonUtils.NEW_LINE);
        }

        JsonConverter.decrementCurrentIndentation();
        stringBuilder.append(CommonUtils.EMPTY_SPACE.repeat(JsonConverter.getCurrentIndentation()));
        stringBuilder.append(JSON_LIST_CLOSE_SIGN);
        return stringBuilder.toString();
    }

}
