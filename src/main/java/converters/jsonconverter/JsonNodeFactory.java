package converters.jsonconverter;

import converters.jsonconverter.component.JsonNode;
import converters.jsonconverter.component.JsonNodeList;
import converters.jsonconverter.component.JsonObject;

public class JsonNodeFactory {

    public static JsonObject getJsonObjectWithNode(){
        return new JsonObject(JsonNodePrinterFactory.getJsonObjectWithNodePrinter());
    }

    public static JsonObject getJsonObjectWithNodeList(){
        return new JsonObject(JsonNodePrinterFactory.getJsonObjectWithNodeListPrinter());
    }

    public static JsonNode getJsonNodeEmpty(String elementName){
        return new JsonNode(elementName, JsonNodePrinterFactory.getJsonNodeEmptyPrinter());
    }
    public static JsonNode getJsonNodeWithValue(String elementName, Object value){
        return new JsonNode(elementName, value,  JsonNodePrinterFactory.getJsonNodeWithValuePrinter());
    }

    public static JsonNodeList getJsonNodeList(String elementName){
        return new JsonNodeList(elementName, JsonNodePrinterFactory.getJsonNodeListPrinter());
    }

    public static JsonNodeList getJsonEqualNodeList(String elementName){
        JsonNodeList result  = getJsonNodeList(elementName);
        result.setEqualNodeList(true);
        return result;
    }


}
