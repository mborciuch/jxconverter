package converters.xml2json.component;

import converters.factories.JsonPrinterFactory;
import converters.factories.NodeFactory;
import converters.xml2json.json2xmlConverter;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonElementTest {

    private static json2xmlConverter json2xmlConverter;

    @BeforeAll
    public static void prepareConverter(){
        JsonPrinterFactory jsonNodePrinterFactory = new JsonPrinterFactory();
        NodeFactory nodeFactory = new NodeFactory(jsonNodePrinterFactory);
        json2xmlConverter =  new json2xmlConverter(nodeFactory);
    }

    @Test
    public void buildElementWithValue() {
        String xmlElement  =  "<host>127.0.0.1</host>";
        String jsonValue  =  json2xmlConverter.convert(xmlElement);
        assertEquals("{\"host\" : \"127.0.0.1\"}", jsonValue);
     }

    @Test
    public void buildEmptyElement(){
        String xmlElement  =  "<success></success>";
        String jsonValue  =  json2xmlConverter.convert(xmlElement);
        assertEquals("{\"success\" : \"\"}", jsonValue);
    }

    @Test
    public void buildEmptyElementSingleTag(){
        String xmlElement  =  "<success/>";
        String jsonValue  =  json2xmlConverter.convert(xmlElement);
        assertEquals("{\"success\" : null}", jsonValue);
    }

    @Test
    public void buildElement_NotEmptyElement_WithAttribute(){
        String xmlElement  =  "<employee department = \"manager\">Garry Smith</employee>";
        String jsonValue  =  json2xmlConverter.convert(xmlElement);
        assertEquals("{\n" +
                "    \"employee\" : {\n" +
                "        \"@department\" : \"manager\",\n" +
                "        \"#employee\" : \"Garry Smith\"\n" +
                "    }\n" +
                "}", jsonValue);
    }

    @Test
    public void buildElement_NotEmptyElement_WithAttributes(){
        String xmlElement  =  "<employee role =\"leader\" department=\"manager\">Garry Smith</employee>";
        String jsonValue  =  json2xmlConverter.convert(xmlElement);
        assertEquals("{\n" +
                "    \"employee\" : {\n" +
                "        \"@role\" : \"leader\",\n" +
                "        \"@department\" : \"manager\",\n" +
                "        \"#employee\" : \"Garry Smith\"\n" +
                "    }\n" +
                "}", jsonValue);
    }

    @Test
    public void buildElement_EmptyElement_WithAttributes(){
        String xmlElement  =  "<person rate=\"1\" name=\"Torvalds\"/>";
        String jsonValue  =  json2xmlConverter.convert(xmlElement);
        assertEquals("{\n" +
                "    \"person\" : {\n" +
                "        \"@rate\" : \"1\",\n" +
                "        \"@name\" : \"Torvalds\",\n" +
                "        \"#person\" : null\n" +
                "    }\n" +
                "}", jsonValue);
    }

    @Test
    public void buildElement_WithNestedLines(){
        String xmlElement  =  "<root>\n" +
                "    <id>6753322</id>\n" +
                "    <number region = \"Russia\">8-900-000-00-00</number>\n" +
                "    <nonattr1/>\n" +
                "    <nonattr2></nonattr2>\n" +
                "    <nonattr3>text</nonattr3>\n" +
                "    <attr1 id = \"1\"/>\n" +
                "    <attr2 id = \"2\"></attr2>\n" +
                "    <attr3 id = \"3\">text</attr3>\n" +
                "    <email>\n" +
                "        <to>to_example@gmail.com</to>\n" +
                "        <from>from_example@gmail.com</from>\n" +
                "        <subject>Project discussion</subject>\n" +
                "        <body font = \"Verdana\">Body message</body>\n" +
                "        <date day = \"12\" month = \"12\" year = \"2018\" />\n" +
                "    </email>\n" +
                "</root>";
        String jsonValue  =  json2xmlConverter.convert(xmlElement);
        assertEquals("{\n" +
                "    \"root\" : {\n" +
                "        \"id\" : \"6753322\",\n" +
                "        \"number\" : {\n" +
                "            \"@region\" : \"Russia\",\n" +
                "            \"#number\" : \"8-900-000-00-00\"\n" +
                "        },\n" +
                "        \"nonattr1\" : null,\n" +
                "        \"nonattr2\" : \"\",\n" +
                "        \"nonattr3\" : \"text\",\n" +
                "        \"attr1\" : {\n" +
                "            \"@id\" : \"1\",\n" +
                "            \"#attr1\" : null\n" +
                "        },\n" +
                "        \"attr2\" : {\n" +
                "            \"@id\" : \"2\",\n" +
                "            \"#attr2\" : \"\"\n" +
                "        },\n" +
                "        \"attr3\" : {\n" +
                "            \"@id\" : \"3\",\n" +
                "            \"#attr3\" : \"text\"\n" +
                "        },\n" +
                "        \"email\" : {\n" +
                "            \"to\" : \"to_example@gmail.com\",\n" +
                "            \"from\" : \"from_example@gmail.com\",\n" +
                "            \"subject\" : \"Project discussion\",\n" +
                "            \"body\" : {\n" +
                "                \"@font\" : \"Verdana\",\n" +
                "                \"#body\" : \"Body message\"\n" +
                "            },\n" +
                "            \"date\" : {\n" +
                "                \"@day\" : \"12\",\n" +
                "                \"@month\" : \"12\",\n" +
                "                \"@year\" : \"2018\",\n" +
                "                \"#date\" : null\n" +
                "            }\n" +
                "        }\n" +
                "    }\n" +
                "}", jsonValue);
    }

    @Test
    public void listWithTheSameElementName(){
        String input = "<element>\n" +
                "   <deep deepattr = \"deepvalue\">\n" +
                "       <element1>1</element1>\n" +
                "       <element2>2</element2>\n" +
                "       <element3>3</element3>\n" +
                "   </deep>\n" +
                "</element>\n" ;
        String result = json2xmlConverter.convert(input);
        System.out.println(result);
    }

    //Not Nested Element
    public void buildElement_WithListAndNestedLines() {
        String xmlElement  =  "<root>\n" +
                "    <transaction>\n" +
                "        <id>6753322</id>\n" +
                "        <number region = 'Russia'>8-900-000-000</number>\n" +
                "        <special1>false</special1>\n" +
                "        <special2>true</special2>\n" +
                "        <empty1 />\n" +
                "        <empty2></empty2>\n" +
                "        <array1>\n" +
                "            <element />\n" +
                "            <element />\n" +
                "        </array1>\n" +
                "        <array2>\n" +
                "            <element></element>\n" +
                "            <element />\n" +
                "            <element>123</element>\n" +
                "            <element>123.456</element>\n" +
                "            <element>\n" +
                "                <key1>value1</key1>\n" +
                "                <key2 attr = \"value2\">value3</key2>\n" +
                "            </element>\n" +
                "            <element attr2 = 'value4'>value5</element>\n" +
                "            <element>\n" +
                "                <attr3>value4</attr3>\n" +
                "                <elem>value5</elem>\n" +
                "            </element>\n" +
//                "            <element>\n" +
//                "                <deep deepattr = \"deepvalue\">\n" +
//                "                    <element>1</element>\n" +
//                "                    <element>2</element>\n" +
//                "                    <element>3</element>\n" +
//                "                </deep>\n" +
//                "            </element>\n" +
                "        </array2>\n" +
                "        <inner1>\n" +
                "            <inner2>\n" +
                "                <inner3>\n" +
                "                    <key1>value1</key1>\n" +
                "                    <key2>value2</key2>\n" +
                "                </inner3>\n" +
                "            </inner2>\n" +
                "        </inner1>\n" +
                "        <inner4>\n" +
                "            <innerNot4>value3</innerNot4>\n" +
                "        </inner4>\n" +
                "        <inner5>\n" +
                "            <attr1>123.456</attr1>\n" +
                "            <inner4>value4</inner4>\n" +
                "        </inner5>\n" +
                "        <inner6 attr2 = \"789.321\">value5</inner6>\n" +
                "        <inner7>value6</inner7>\n" +
                "        <inner8>\n" +
                "            <attr3>value7</attr3>\n" +
                "        </inner8>\n" +
                "        <inner9>\n" +
                "            <attr4>value8</attr4>\n" +
                "            <innerNot9>value9</innerNot9>\n" +
                "            <something>value10</something>\n" +
                "        </inner9>\n" +
                "        <inner10 attr5 = '' />\n" +
                "        <inner11 attr11 = \"value11\">\n" +
                "            <inner12 attr12 = \"value12\">\n" +
                "                <inner13 attr13 = \"value13\">\n" +
                "                    <inner14>v14</inner14>\n" +
                "                </inner13>\n" +
                "            </inner12>\n" +
                "        </inner11>\n" +
                "        <inner15></inner15>\n" +
                "        <inner16>\n" +
                "            <somekey>keyvalue</somekey>\n" +
                "            <innerNot16>notnull</innerNot16>\n" +
                "        </inner16>\n" +
                "        <crazyattr1 attr1 = '123'>v15</crazyattr1>\n" +
                "        <crazyattr2 attr1 = \"123.456\">v16</crazyattr2>\n" +
                "        <crazyattr3 attr1 = ''>v17</crazyattr3>\n" +
                "        <crazyattr9>\n" +
                "            <attr1>\n" +
                "                <key>4</key>\n" +
                "            </attr1>\n" +
                "            <crazyattrNot9>v23</crazyattrNot9>\n" +
                "        </crazyattr9>\n" +
                "    </transaction>\n" +
                "    <meta>\n" +
                "        <version>0.01</version>\n" +
                "    </meta>\n" +
                "</root>";
        String jsonValue  =  json2xmlConverter.convert(xmlElement);
        assertEquals("{\n" +
                "    \"root\": {\n" +
                "        \"transaction\": {\n" +
                "            \"id\": \"6753322\",\n" +
                "            \"number\": {\n" +
                "                \"@region\": \"Russia\",\n" +
                "                \"#number\": \"8-900-000-000\"\n" +
                "            },\n" +
                "            \"special1\": \"false\",\n" +
                "            \"special2\": \"true\",\n" +
                "            \"empty1\": null,\n" +
                "            \"empty2\": \"\",\n" +
                "            \"array1\": [\n" +
                "                null, null\n" +
                "            ],\n" +
                "            \"array2\": [\n" +
                "                \"\",\n" +
                "                null,\n" +
                "                \"123\",\n" +
                "                \"123.456\",\n" +
                "                {\n" +
                "                    \"key1\": \"value1\",\n" +
                "                    \"key2\": {\n" +
                "                        \"@attr\": \"value2\",\n" +
                "                        \"#key2\": \"value3\"\n" +
                "                    }\n" +
                "                },\n" +
                "                {\n" +
                "                    \"@attr2\": \"value4\",\n" +
                "                    \"#element\": \"value5\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"attr3\": \"value4\",\n" +
                "                    \"elem\": \"value5\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"deep\": {\n" +
                "                        \"@deepattr\": \"deepvalue\",\n" +
                "                        \"#deep\": [\n" +
                "                            \"1\",\n" +
                "                            \"2\",\n" +
                "                            \"3\"\n" +
                "                        ]\n" +
                "                    }\n" +
                "                }\n" +
                "            ],\n" +
                "            \"inner1\": {\n" +
                "                \"inner2\": {\n" +
                "                    \"inner3\": {\n" +
                "                        \"key1\": \"value1\",\n" +
                "                        \"key2\": \"value2\"\n" +
                "                    }\n" +
                "                }\n" +
                "            },\n" +
                "            \"inner4\": {\n" +
                "                \"inner4\": \"value3\"\n" +
                "            },\n" +
                "            \"inner5\": {\n" +
                "                \"attr1\": \"123.456\",\n" +
                "                \"inner4\": \"value4\"\n" +
                "            },\n" +
                "            \"inner6\": {\n" +
                "                \"@attr2\": \"789.321\",\n" +
                "                \"#inner6\": \"value5\"\n" +
                "            },\n" +
                "            \"inner7\": \"value6\",\n" +
                "            \"inner8\": {\n" +
                "                \"attr3\": \"value7\"\n" +
                "            },\n" +
                "            \"inner9\": {\n" +
                "                \"attr4\": \"value8\",\n" +
                "                \"inner9\": \"value9\",\n" +
                "                \"something\": \"value10\"\n" +
                "            },\n" +
                "            \"inner10\": {\n" +
                "                \"@attr5\": \"\",\n" +
                "                \"#inner10\": null\n" +
                "            },\n" +
                "            \"inner11\": {\n" +
                "                \"@attr11\": \"value11\",\n" +
                "                \"#inner11\": {\n" +
                "                    \"inner12\": {\n" +
                "                        \"@attr12\": \"value12\",\n" +
                "                        \"#inner12\": {\n" +
                "                            \"inner13\": {\n" +
                "                                \"@attr13\": \"value13\",\n" +
                "                                \"#inner13\": {\n" +
                "                                    \"inner14\": \"v14\"\n" +
                "                                }\n" +
                "                            }\n" +
                "                        }\n" +
                "                    }\n" +
                "                }\n" +
                "            },\n" +
                "            \"inner15\": \"\",\n" +
                "            \"inner16\": {\n" +
                "                \"somekey\": \"keyvalue\",\n" +
                "                \"inner16\": \"notnull\"\n" +
                "            },\n" +
                "            \"crazyattr1\": {\n" +
                "                \"@attr1\": \"123\",\n" +
                "                \"#crazyattr1\": \"v15\"\n" +
                "            },\n" +
                "            \"crazyattr2\": {\n" +
                "                \"@attr1\": \"123.456\",\n" +
                "                \"#crazyattr2\": \"v16\"\n" +
                "            },\n" +
                "            \"crazyattr3\": {\n" +
                "                \"@attr1\": \"\",\n" +
                "                \"#crazyattr3\": \"v17\"\n" +
                "            },\n" +
                "            \"crazyattr9\": {\n" +
                "                \"attr1\": {\n" +
                "                    \"key\": \"4\"\n" +
                "                },\n" +
                "                \"crazyattr9\": \"v23\"\n" +
                "            }\n" +
                "        },\n" +
                "        \"meta\": {\n" +
                "            \"version\": \"0.01\"\n" +
                "        }\n" +
                "    }\n" +
                "}", jsonValue);
    }
}