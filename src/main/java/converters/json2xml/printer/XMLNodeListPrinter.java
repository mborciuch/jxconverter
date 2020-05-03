package converters.json2xml.printer;

import converters.CommonUtils;
import converters.components.AbstractNode;
import converters.components.Node;
import converters.components.NodeList;
import converters.components.Printer;
import converters.xml2json.json2xmlConverter;

import static converters.xml2json.JsonUtils.*;

public class XMLNodeListPrinter implements Printer {

    @Override
    public String prepareElement(AbstractNode abstractNode) {
        NodeList xmlList = (NodeList) abstractNode;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(JSON_QUOTE).append(xmlList.getNodeName()).append(JSON_QUOTE).append(CommonUtils.EMPTY_SPACE).append(JSON_COLON).append(CommonUtils.EMPTY_SPACE).append(JSON_OPEN_SIGN).append(CommonUtils.NEW_LINE);
        json2xmlConverter.incrementCurrentIndentation();
        for (AbstractNode currentElement : xmlList) {
            stringBuilder.append(CommonUtils.EMPTY_SPACE.repeat(json2xmlConverter.getCurrentIndentation()));
            stringBuilder.append(currentElement.print());
            if (currentElement.getNodeName().startsWith("@") || (currentElement instanceof Node && !currentElement.getNodeName().startsWith("#")) || currentElement instanceof NodeList){
                stringBuilder.append(CommonUtils.COMMA);
            }
            stringBuilder.append(CommonUtils.NEW_LINE);
        }

        json2xmlConverter.decrementCurrentIndentation();
        stringBuilder.append(CommonUtils.EMPTY_SPACE.repeat(json2xmlConverter.getCurrentIndentation()));
        stringBuilder.append(JSON_CLOSE_SIGN);
        return stringBuilder.toString();
    }
}
