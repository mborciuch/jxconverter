package app.factories.printers.json;

import app.utils.CommonUtils;
import app.components.AbstractNode;
import app.components.Node;
import app.components.NodeList;
import app.components.Printer;
import app.converters.xml2json.json2xmlConverter;

import java.util.Iterator;

import static app.utils.JsonUtils.*;

public class JsonNodeListPrinter implements Printer {
    @Override
    public String prepareElement(AbstractNode abstractNode) {
        NodeList jsonElementList = (NodeList) abstractNode;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(JSON_QUOTE).append(jsonElementList.getNodeName()).append(JSON_QUOTE).append(CommonUtils.EMPTY_SPACE).append(JSON_COLON).append(CommonUtils.EMPTY_SPACE).append(JSON_OPEN_SIGN).append(CommonUtils.NEW_LINE);
        json2xmlConverter.incrementCurrentIndentation();
        Iterator<AbstractNode> jsonElementListIterator = jsonElementList.iterator();
        AbstractNode currentElement;
        while (jsonElementListIterator.hasNext()) {
            currentElement = jsonElementListIterator.next();
            stringBuilder.append(CommonUtils.EMPTY_SPACE.repeat(json2xmlConverter.getCurrentIndentation()));
            stringBuilder.append(currentElement.print());
            if (currentElement.getNodeName().startsWith("@") || (currentElement instanceof Node && !currentElement.getNodeName().startsWith("#")) || (currentElement instanceof NodeList && jsonElementListIterator.hasNext())){
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
