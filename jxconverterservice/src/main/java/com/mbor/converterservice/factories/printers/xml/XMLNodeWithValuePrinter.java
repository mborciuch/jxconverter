package com.mbor.converterservice.factories.printers.xml;

import com.mbor.converterservice.components.AbstractNode;
import com.mbor.converterservice.components.Node;
import com.mbor.converterservice.components.Printer;

import java.util.Map;

import static com.mbor.converterservice.utils.XmlUtils.*;

public class XMLNodeWithValuePrinter implements Printer {

    @Override
    public String prepareElement(AbstractNode abstractNode) {
        StringBuilder stringBuilder = new StringBuilder();
        Node xmlLine = (Node) abstractNode;
        String nodeName;
        if (xmlLine.getNodeName().startsWith("#")) {
            nodeName = xmlLine.getNodeName().replaceFirst("#", "");
        } else {
            nodeName = xmlLine.getNodeName();
        }
        stringBuilder.append(XML_OPEN_TAG).append(nodeName);
        if (!xmlLine.getAttributes().isEmpty()) {
            stringBuilder
                    .append(addAttributes(xmlLine));
        }
        stringBuilder.append(XML_CLOSE_TAG)
                .append(xmlLine.getValue()).append(XML_CLOSE_ELEMENT_TAG).append(nodeName).append(XML_CLOSE_TAG);
        return stringBuilder.toString();
    }

    private String addAttributes(Node node) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(" ");
        String key;
        String value;
        for (Map.Entry entry : node.getAttributes().entrySet()) {
            if(entry.getKey().toString().startsWith("@")){
                key = entry.getKey().toString().replaceFirst("@", "");
            } else {
                 key = entry.getKey().toString();
            }
            value = "\"" + entry.getValue().toString() + "\"";
            stringBuilder.append(key).append("=").append(value);
        }
        return stringBuilder.toString();
    }
}



