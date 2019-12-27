//package converters.xmlconverter.components;
//
//import converters.components.Node;
//import converters.components.Printer;
//
//import java.util.Map;
//
//public class XmlNode extends Node {
//
//    private String elementName;
//
//    private Map<String, String> attributes;
//
//    public XmlNode(String elementName, Object value) {
//        super(value);
//        this.elementName = elementName;
//    }
//
//    public XmlNode(String elementName){
//        super();
//        this.elementName = elementName;
//    }
//
//    @Override
//    public String getNodeName() {
//        return elementName;
//    }
//
//    @Override
//    public void setNodeName(String nodeName) {
//        this.elementName = nodeName;
//    }
//
//    public Map<String, String> getAttributes() {
//        return attributes;
//    }
//
//    public void setAttributes(Map<String, String> attributes) {
//        this.attributes = attributes;
//    }
//
//    @Override
//    public String print() {
//        Printer printer;
//        if(this.value == null){
//            printer = new XmlLineEmptyPrinter();
//        }else {
//            printer = new XmlLineWithValuePrinter();
//        }
//        return printer.prepareElement(this);
//    }
//}
