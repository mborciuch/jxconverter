package com.mbor.converterservice.factories.printers.xml;

import com.mbor.converterservice.utils.CommonUtils;
import com.mbor.converterservice.components.AbstractNode;
import com.mbor.converterservice.components.NodeList;
import com.mbor.converterservice.components.Printer;
import com.mbor.converterservice.utils.XmlUtils;
import com.mbor.converterservice.converters.abstractconverter.xml2json.Xml2JsonConverter;

public class XMLNodeListPrinter implements Printer {

    @Override
    public String prepareElement(AbstractNode abstractNode) {
        NodeList xmlList = (NodeList) abstractNode;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(XmlUtils.XML_OPEN_TAG).append(xmlList.getNodeName()).append(XmlUtils.XML_CLOSE_TAG).append(CommonUtils.NEW_LINE);
        Xml2JsonConverter.incrementCurrentIndentation();
        for (AbstractNode currentElement : xmlList) {
            stringBuilder.append(CommonUtils.EMPTY_SPACE.repeat(Xml2JsonConverter.getCurrentIndentation()));
            stringBuilder.append(currentElement.print());
            stringBuilder.append(CommonUtils.NEW_LINE);
        }
        Xml2JsonConverter.decrementCurrentIndentation();
        stringBuilder.append(CommonUtils.EMPTY_SPACE.repeat(Xml2JsonConverter.getCurrentIndentation()));
        stringBuilder.append(XmlUtils.XML_CLOSE_ELEMENT_TAG).append(xmlList.getNodeName()).append(XmlUtils.XML_CLOSE_TAG);
        return stringBuilder.toString();
    }
}
