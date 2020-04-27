package converters.xml2json.printer;

import converters.components.AbstractNode;
import converters.components.Node;
import converters.components.Printer;

import java.util.Map;

import static converters.xml2json.XmlUtils.XML_CLOSE_EMPTY_ELEMENT_TAG;
import static converters.xml2json.XmlUtils.XML_OPEN_TAG;

public class XMLNodeWithNoValuePrinter implements Printer {

    @Override
    public String prepareElement(AbstractNode abstractNode) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(XML_OPEN_TAG).append(abstractNode.getNodeName()).append(abstractNode).append(XML_CLOSE_EMPTY_ELEMENT_TAG);
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
