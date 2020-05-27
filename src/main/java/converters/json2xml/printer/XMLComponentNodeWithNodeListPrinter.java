package converters.json2xml.printer;

import converters.CommonUtils;
import converters.components.AbstractNode;
import converters.components.Printer;
import converters.json2xml.XmlUtils;
import converters.xml2json.json2xmlConverter;

import static converters.xml2json.JsonUtils.JSON_CLOSE_SIGN;
import static converters.xml2json.JsonUtils.JSON_OPEN_SIGN;

public class XMLComponentNodeWithNodeListPrinter implements Printer {

    @Override
    public String prepareElement(AbstractNode abstractNode) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(abstractNode.print());
        return stringBuilder.toString();
    }

}