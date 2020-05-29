package com.mbor.converterservice.factories.converters;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mbor.converterservice.converters.json2xml.Xml2jsonConverter;
import com.mbor.converterservice.converters.xml2json.Json2xmlConverter;
import com.mbor.converterservice.factories.nodes.NodeFactory;
import com.mbor.converterservice.factories.printers.JsonPrinterFactory;
import com.mbor.converterservice.factories.printers.XmlPrinterFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

@Component
public class Xml2jsonConverterFactory {


    public Xml2jsonConverter xml2jsonConverter(){
        XmlPrinterFactory xmlPrinterFactory = new XmlPrinterFactory();
        NodeFactory nodeFactory = new NodeFactory(xmlPrinterFactory);
        ObjectMapper objectMapper = new ObjectMapper();
        return new Xml2jsonConverter(nodeFactory, objectMapper);
    }




}
