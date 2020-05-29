package app.factories.printers.xml;

import app.utils.CommonUtils;
import app.components.AbstractNode;
import app.components.NodeList;
import app.components.Printer;
import app.utils.XmlUtils;
import app.converters.xml2json.json2xmlConverter;

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
