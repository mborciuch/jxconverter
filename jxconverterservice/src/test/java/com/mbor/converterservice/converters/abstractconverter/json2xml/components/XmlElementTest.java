package com.mbor.converterservice.converters.abstractconverter.json2xml.components;

import com.mbor.converterservice.converters.abstractconverter.json2xml.Json2XmlConverter;
import com.mbor.converterservice.factories.converters.Json2xmlConverterFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class XmlElementTest {

    private static Json2XmlConverter json2XmlConverter;

    @BeforeAll
    public static void prepareConverter() {
        Json2xmlConverterFactory json2xmlConverterFactory = new Json2xmlConverterFactory();
        json2XmlConverter = json2xmlConverterFactory.json2xmlConverter();
    }

    @Test
    public void buildElementWithValue() {
        String jsonElement = "{\"jdk\" : \"1.8.9\"}";
        String xmlValue = json2XmlConverter.convert(jsonElement);
        assertEquals("<jdk>1.8.9</jdk>", xmlValue);
    }

    @Test
    public void buildElementEmpty() {
        String jsonElement = "{\"success\" : null}";
        String xmlValue = json2XmlConverter.convert(jsonElement);
        assertEquals("<success/>", xmlValue);
    }

    @Test
    public void buildElementNestedTwoEqualLine() {
        String jsonElement = "{\n" +
                "    \"root\" : {\n" +
                "        \"host\" : \"localhost\",\n" +
                "        \"port\" : \"8080\"\n" +
                "    }\n" +
                "}";
        String jsonValue = json2XmlConverter.convert(jsonElement);
        assertEquals("<root>\n" +
                "    <host>localhost</host>\n" +
                "    <port>8080</port>\n" +
                "</root>", jsonValue);
    }

    @Test
    public void buildElementWithTwoValues() {
        String jsonElement =
                "{\n" +
                        "   \"jdk\" : \"1.8.9\",\n" +
                        "   \"jre\" :  \"11.0.1\" \n" +
                        "}";
        String xmlValue = json2XmlConverter.convert(jsonElement);
        assertEquals(
                "<root>\n" +
                        "    <jdk>1.8.9</jdk>\n" +
                        "    <jre>11.0.1</jre>\n" +
                        "</root>", xmlValue);
    }

    @Test
    public void buildElementWithValueWithAttribute() {
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
    public void buildElementEmptyWithAttributes() {
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
    public void buildElementListAndNestedList() {
        String jsonElement = "{\n" +
                "    \"root\" : {\n" +
                "        \"host\" : \"localhost\",\n" +
                "        \"port\" : \"8080\",\n" +
                "        \"list\" : {\n" +
                "            \"el\" : \"1\"\n" +
                "        }\n" +
                "    }\n" +
                "}";
        String jsonValue = json2XmlConverter.convert(jsonElement);
        assertEquals("<root>\n" +
                "    <host>localhost</host>\n" +
                "    <port>8080</port>\n" +
                "    <list>\n" +
                "        <el>1</el>\n" +
                "    </list>\n" +
                "</root>", jsonValue);
    }

    @Test
    public void buildElementListWithAttributesAndNestedList() {
        String jsonElement = "{\n" +
                "    \"root\" : {\n" +
                "        \"@attr\" : \"1\",\n" +
                "        \"#root\" : {\n" +
                "            \"host\" : \"localhost\",\n" +
                "            \"port\" : \"8080\",\n" +
                "            \"list\" : {\n" +
                "                  \"el\" : \"1\"\n" +
                "            }\n" +
                "        }\n" +
                "    }\n" +
                "}";

        String jsonValue = json2XmlConverter.convert(jsonElement);
        assertEquals("<root attr=\"1\">\n" +
                "    <host>localhost</host>\n" +
                "    <port>8080</port>\n" +
                "    <list>\n" +
                "        <el>1</el>\n" +
                "    </list>\n" +
                "</root>", jsonValue);
    }

    @Test
    public void buildElementWithNestedLines() {
        String jsonElement = "{\n" +
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
        String xmlValue = json2XmlConverter.convert(jsonElement);
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
}
