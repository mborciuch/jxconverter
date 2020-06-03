package com.mbor.converterservice.converters.abstractconverter.json2xml.components;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mbor.converterservice.factories.nodes.NodeFactory;
import com.mbor.converterservice.factories.printers.XmlPrinterFactory;
import com.mbor.converterservice.converters.abstractconverter.json2xml.Json2XmlConverter;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.assertEquals;

public class XmlElementTest {

    private static Json2XmlConverter json2XmlConverter;

    @BeforeAll
    public static void prepareConverter(){
        XmlPrinterFactory xmlPrinterFactory = new XmlPrinterFactory();
        NodeFactory nodeFactory = new NodeFactory(xmlPrinterFactory);
        ObjectMapper objectMapper = new ObjectMapper();
        json2XmlConverter = new Json2XmlConverter(nodeFactory, objectMapper);
    }

    @Test
    public void buildElement_WithValue() {
        String jsonElement = "{\"jdk\" : \"1.8.9\"}";
        String xmlValue = json2XmlConverter.convert(jsonElement);
        assertEquals("<jdk>1.8.9</jdk>", xmlValue);
    }
    @Test
    public void buildElement_Empty(){
        String jsonElement = "{\"success\" : null}";
        String xmlValue  = json2XmlConverter.convert(jsonElement);
        assertEquals("<success/>",xmlValue);

    }

    @Test
    public void buildElement_WithTwoValues() {
        String jsonElement =
                "{" +
                " \"jdk\" : \"1.8.9\",\n" +
                " \"jre\" :  \"11.0.1\"}";
        String xmlValue = json2XmlConverter.convert(jsonElement);
        assertEquals(
                "<root>\n" +
                "    <jdk>1.8.9</jdk>\n" +
                "    <jre>11.0.1</jre>\n" +
                "</root>", xmlValue);
    }

    @Test
    public void buildElement_WithValueWithAttribute(){
        String jsonElement = "{\n" +
                "    \"employee\" : {\n" +
                "        \"@department\" : \"manager\",\n" +
                "        \"#employee\" : \"Garry Smith\"\n" +
                "    }\n" +
                "}";
        String xmlValue = json2XmlConverter.convert(jsonElement);
        assertEquals("<employee department=\"manager\">Garry Smith</employee>", xmlValue);
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
        String xmlValue = json2XmlConverter.convert(jsonElement);
        assertEquals("<person rate=\"1\" name=\"Torvalds\"/>", xmlValue);
    }

    @Test
    public void buildElement_WithNestedLines(){
        String jsonElement  = "{\n" +
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
                "}";
        String xmlValue  =  json2XmlConverter.convert(jsonElement);
        assertEquals("<root>\n" +
                "    <id>6753322</id>\n" +
                "    <number region=\"Russia\">8-900-000-00-00</number>\n" +
                "    <nonattr1/>\n" +
                "    <nonattr2></nonattr2>\n" +
                "    <nonattr3>text</nonattr3>\n" +
                "    <attr1 id=\"1\"/>\n" +
                "    <attr2 id=\"2\"></attr2>\n" +
                "    <attr3 id=\"3\">text</attr3>\n" +
                "    <email>\n" +
                "        <to>to_example@gmail.com</to>\n" +
                "        <from>from_example@gmail.com</from>\n" +
                "        <subject>Project discussion</subject>\n" +
                "        <body font=\"Verdana\">Body message</body>\n" +
                "        <date day=\"12\" month=\"12\" year=\"2018\"/>\n" +
                "    </email>\n" +
                "</root>", xmlValue);

    }

    @Test
    public void buildElement_Pizza(){
        String jsonElement = "{\n" +
                "    \"pizza\" : {\n" +
                "        \"@size\" : 20,\n" +
                "        \"#pizza\" : 123\n" +
                "    }\n" +
                "}";
        String xmlValue = json2XmlConverter.convert(jsonElement);
        assertEquals("<pizza size=\"20\">123</pizza>", xmlValue);
    }

}
