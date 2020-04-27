package converters.json2xml.printer;

import converters.CommonUtils;
import converters.components.AbstractNode;
import converters.components.NodeList;
import converters.components.Printer;
import converters.json2xml.json2xmlConverter;

import static converters.json2xml.JsonUtils.*;

public class JsonEqualNodeListPrinter implements Printer {

    @Override
    public String prepareElement(AbstractNode abstractNode) {
        NodeList jsonEqualNodeList = (NodeList) abstractNode;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(JSON_QUOTE).append(jsonEqualNodeList.getNodeName()).append(JSON_QUOTE).append(CommonUtils.EMPTY_SPACE).append(JSON_COLON).append(CommonUtils.EMPTY_SPACE).append(JSON_LIST_OPEN_SIGN).append(CommonUtils.NEW_LINE);
        json2xmlConverter.incrementCurrentIndentation();
        for (AbstractNode currentElement : jsonEqualNodeList) {
            stringBuilder.append(CommonUtils.EMPTY_SPACE.repeat(json2xmlConverter.getCurrentIndentation()));
            stringBuilder.append(currentElement.print());
            stringBuilder.append(CommonUtils.NEW_LINE);
        }

        json2xmlConverter.decrementCurrentIndentation();
        stringBuilder.append(CommonUtils.EMPTY_SPACE.repeat(json2xmlConverter.getCurrentIndentation()));
        stringBuilder.append(JSON_LIST_CLOSE_SIGN);
        return stringBuilder.toString();
    }

}
