package converters.jsonconverter.printer;

import converters.CommonUtils;
import converters.components.AbstractNode;
import converters.components.Node;
import converters.components.NodeList;
import converters.components.Printer;
import converters.jsonconverter.JsonConverter;


import static converters.jsonconverter.JsonUtils.*;

public class JsonNodePrinter implements Printer {
    @Override
    public String prepareElement(AbstractNode abstractNode) {
        NodeList jsonElementList = (NodeList) abstractNode;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(JSON_QUOTE).append(jsonElementList.getNodeName()).append(JSON_QUOTE).append(CommonUtils.EMPTY_SPACE).append(JSON_COLON).append(CommonUtils.EMPTY_SPACE).append(JSON_OPEN_SIGN).append(CommonUtils.NEW_LINE);
        JsonConverter.incrementCurrentIndentation();
        for (AbstractNode currentElement : jsonElementList) {
            stringBuilder.append(CommonUtils.EMPTY_SPACE.repeat(JsonConverter.getCurrentIndentation()));
            stringBuilder.append(currentElement.print());
            if (currentElement.getNodeName().startsWith("@") || (currentElement instanceof Node && !currentElement.getNodeName().startsWith("#")) || currentElement instanceof NodeList){
                stringBuilder.append(CommonUtils.COLON);
            }
            stringBuilder.append(CommonUtils.NEW_LINE);
        }

        JsonConverter.decrementCurrentIndentation();
        stringBuilder.append(CommonUtils.EMPTY_SPACE.repeat(JsonConverter.getCurrentIndentation()));
        stringBuilder.append(JSON_CLOSE_SIGN);
        return stringBuilder.toString();
    }
}
