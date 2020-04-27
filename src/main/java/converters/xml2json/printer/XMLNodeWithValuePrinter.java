package converters.xml2json.printer;

import converters.components.AbstractNode;
import converters.components.Node;
import converters.components.Printer;

import java.util.Map;

import static converters.xml2json.XmlUtils.*;

public class XMLNodeWithValuePrinter implements Printer {

    @Override
    public String prepareElement(AbstractNode abstractNode) {
        StringBuilder stringBuilder = new StringBuilder();
        Node xmlLine = (Node) abstractNode;
        stringBuilder
                .append(XML_OPEN_TAG).append(xmlLine.getNodeName()).append(getAttributes(xmlLine)).append(XML_CLOSE_TAG)
                .append(xmlLine.getValue()).append(XML_CLOSE_ELEMENT_TAG).append(xmlLine.getNodeName()).append(XML_CLOSE_TAG);
        return stringBuilder.toString();
    }

    private String getAttributes(Node node){
        if (!node.getAttributes().isEmpty()) {
            StringBuilder stringBuilder = new StringBuilder();
            for(Map.Entry entry : node.getAttributes().entrySet()){
                stringBuilder.append(entry.getKey()).append("\\s").append(entry.getValue());
            }
            return stringBuilder.toString();
        }
        else {
            return "";
        }
    }


}
