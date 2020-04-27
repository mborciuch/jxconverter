package converters.json2xml.printer;

import converters.CommonUtils;
import converters.components.AbstractNode;
import converters.components.Printer;
import converters.json2xml.json2xmlConverter;

import static converters.json2xml.JsonUtils.*;

public class JsonComponentNodeWithNodeListPrinter implements Printer {

    @Override
    public String prepareElement(AbstractNode abstractNode) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(JSON_OPEN_SIGN).append(CommonUtils.NEW_LINE);
        json2xmlConverter.incrementCurrentIndentation();
        stringBuilder.append(CommonUtils.EMPTY_SPACE.repeat(json2xmlConverter.getCurrentIndentation()));
        stringBuilder.append(abstractNode.print());
        json2xmlConverter.decrementCurrentIndentation();
        stringBuilder.append(CommonUtils.NEW_LINE).append(CommonUtils.EMPTY_SPACE.repeat(json2xmlConverter.getCurrentIndentation()));
        stringBuilder.append(JSON_CLOSE_SIGN);
        return stringBuilder.toString();
    }

}
