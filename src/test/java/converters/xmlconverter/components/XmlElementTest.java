package converters.xmlconverter.components;

import converters.xmlconverter.XmlConverter;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.assertEquals;

public class XmlElementTest {
    
    XmlConverter xmlConverter;
    
    @BeforeAll
    public void prepareConverter(){
        xmlConverter = new XmlConverter();
    }

    @Test
    public void buildElement_WithValue() {
        String jsonElement = "{\"jdk\" : \"1.8.9\"}";
        String xmlValue = xmlConverter.convert(jsonElement);
        assertEquals("<jdk>1.8.9</jdk>", xmlValue);
    }

    @Test
    public void buildElement_Empty(){
        String jsonElement = "{\"success\" : null}";
        String xmlValue  = xmlConverter.convert(jsonElement);
        assertEquals("<success/>",xmlValue);

    }

    @Test
    public void buildElement_WithValueWithAttribute(){
        String jsonElement = "{\n" +
                "    \"employee\" : {\n" +
                "        \"@department\" : \"manager\",\n" +
                "        \"#employee\" : \"Garry Smith\"\n" +
                "    }\n" +
                "}";
        String xmlValue = xmlConverter.convert(jsonElement);
        assertEquals("<employee department = \"manager\">Garry Smith</employee>", xmlValue);
    }

    @Test
    public void buildElement_EmptyWithAttributes(){
        String jsonElement = "{\n" +
                "    \"person\" : {\n" +
                "        \"@rate\" : 1,\n" +
                "        \"@name\" : \"Torvalds\",\n" +
                "        \"#person\" : null\n" +
                "    }\n " +
                "}";
        String xmlValue = xmlConverter.convert(jsonElement);
        assertEquals("<person rate = \"1\" name = \"Torvalds\"/>", xmlValue);
    }

    @Test
    public void buildElement_Pizza(){
        String jsonElement = "{\n" +
                "    \"pizza\" : {\n" +
                "        \"@size\" : 20,\n" +
                "        \"#pizza\" : 123\n" +
                "    }\n" +
                "}";
        String xmlValue = xmlConverter.convert(jsonElement);
        assertEquals("<pizza size = \"20\">123</pizza>", xmlValue);
    }

}
