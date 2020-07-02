package com.mbor.converterservice.factories.printers.xml.node;

import com.mbor.converterservice.components.AbstractNode;
import com.mbor.converterservice.components.Node;
import com.mbor.converterservice.components.Printer;

import java.util.Map;

import static com.mbor.converterservice.utils.XmlUtils.XML_OPEN_TAG;

public abstract class XmlNodePrinter implements Printer {

    @Override
    public String prepareElement(AbstractNode abstractNode) {
        StringBuilder stringBuilder = new StringBuilder();
        Node xmlLine = (Node) abstractNode;
        String nodeName;
        nodeName = xmlLine.getNodeName();

        stringBuilder.append(XML_OPEN_TAG).append(nodeName);
        if (!xmlLine.getAttributes().isEmpty()) {
            stringBuilder
                    .append(addAttributes(xmlLine));
        }
        stringBuilder.append(printValue(xmlLine));
        return stringBuilder.toString();
    }

    private String addAttributes(Node node) {
        StringBuilder stringBuilder = new StringBuilder();
        String key;
        String value;
        for (Map.Entry entry : node.getAttributes().entrySet()) {
            if(entry.getKey().toString().startsWith("@")){
                key = entry.getKey().toString().replaceFirst("@", "");
            } else {
                key = entry.getKey().toString();
            }
            value = "\"" + entry.getValue().toString() + "\"";
            stringBuilder.append(" ").append(key).append("=").append(value);
        }
        return stringBuilder.toString();
    }

    protected abstract String printValue(Node xmlLine);
}
