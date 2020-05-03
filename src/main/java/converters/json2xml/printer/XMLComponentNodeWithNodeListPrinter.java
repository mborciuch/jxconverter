package converters.json2xml.printer;

import converters.CommonUtils;
import converters.components.AbstractNode;
import converters.components.Printer;
import converters.xml2json.json2xmlConverter;

import static converters.xml2json.JsonUtils.JSON_CLOSE_SIGN;
import static converters.xml2json.JsonUtils.JSON_OPEN_SIGN;

public class XMLComponentNodeWithNodeListPrinter implements Printer {

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
