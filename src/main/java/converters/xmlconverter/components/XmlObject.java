package converters.xmlconverter.components;

import converters.components.AbstractNode;
import converters.components.Printer;

public class XmlObject {

    private AbstractNode abstractNode;

    public XmlObject(){
        abstractNode = new XmlNodeList();
    }

    public void setAbstractNode(AbstractNode abstractNode){
        this.abstractNode = abstractNode;
    }

    public String printXml(){
        Printer printer = null;
        if(abstractNode instanceof XmlNode){
            printer = new XmlObjectWithLinePrinter();
        }
        return  printer.prepareElement(abstractNode);
    }
}
