package converters.xmlconverter.components;

import converters.CommonUtils;
import converters.components.AbstractNode;
import converters.components.Printer;

import java.util.Map;

import static converters.xmlconverter.XmlUtils.*;

public class XmlLineEmptyPrinter implements Printer {

    @Override
    public String prepareElement(AbstractNode abstractNode){
        StringBuilder stringBuilder = new StringBuilder();
        XmlNode xmlLine = (XmlNode) abstractNode;
        stringBuilder.append(XML_OPEN_TAG).append(xmlLine.getNodeName());
        if (xmlLine.getAttributes() != null){
            for (Map.Entry<String, String> entry : xmlLine.getAttributes().entrySet()) {
                stringBuilder.append(CommonUtils.EMPTY_SPACE).append(entry.getKey()).append(CommonUtils.EMPTY_SPACE).append(XML_EQUALS).append(CommonUtils.EMPTY_SPACE).append(CommonUtils.QUOTE).append(entry.getValue()).append(CommonUtils.QUOTE);
            }
        }
        stringBuilder.append(XML_CLOSE_EMPTY_ELEMENT_TAG);
        return stringBuilder.toString();
    }
}
