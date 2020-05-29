package converters.json2xml.printer;

import converters.CommonUtils;
import converters.components.AbstractNode;
import converters.components.Node;
import converters.components.NodeList;
import converters.components.Printer;
import converters.json2xml.XmlUtils;
import converters.xml2json.json2xmlConverter;

import static converters.xml2json.JsonUtils.*;

public class XMLNodeListPrinter implements Printer {

    @Override
    public String prepareElement(AbstractNode abstractNode) {
        NodeList xmlList = (NodeList) abstractNode;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(XmlUtils.XML_OPEN_TAG).append(xmlList.getNodeName()).append(XmlUtils.XML_CLOSE_TAG).append(CommonUtils.NEW_LINE);
        json2xmlConverter.incrementCurrentIndentation();
        for (AbstractNode currentElement : xmlList) {
            stringBuilder.append(CommonUtils.EMPTY_SPACE.repeat(json2xmlConverter.getCurrentIndentation()));
            stringBuilder.append(currentElement.print());
            stringBuilder.append(CommonUtils.NEW_LINE);
        }
        json2xmlConverter.decrementCurrentIndentation();
        stringBuilder.append(CommonUtils.EMPTY_SPACE.repeat(json2xmlConverter.getCurrentIndentation()));
        stringBuilder.append(XmlUtils.XML_CLOSE_ELEMENT_TAG).append(xmlList.getNodeName()).append(XmlUtils.XML_CLOSE_TAG);
        return stringBuilder.toString();
    }
}
