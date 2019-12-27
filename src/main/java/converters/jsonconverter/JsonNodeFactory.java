package converters.jsonconverter;

import converters.components.ComponentNode;
import converters.components.Node;
import converters.components.NodeList;

public class JsonNodeFactory {

    public static ComponentNode getJsonObjectWithNode(){
        return new ComponentNode(JsonNodePrinterFactory.getJsonObjectWithNodePrinter());
    }

    public static ComponentNode getJsonObjectWithNodeList(){
        return new ComponentNode(JsonNodePrinterFactory.getJsonObjectWithNodeListPrinter());
    }

    public static Node getJsonNodeEmpty(String elementName){
        return new Node(elementName, JsonNodePrinterFactory.getJsonNodeEmptyPrinter());
    }
    public static Node getJsonNodeWithValue(String elementName, Object value){
        return new Node(elementName, JsonNodePrinterFactory.getJsonNodeWithValuePrinter(),  value);
    }

    public static NodeList getJsonNodeList(String elementName){
        return new NodeList(elementName, JsonNodePrinterFactory.getJsonNodeListPrinter());
    }

    public static NodeList getJsonEqualNodeList(String elementName){
        NodeList result  = getJsonNodeList(elementName);
        result.setEqualNodeList(true);
        return result;
    }


}
