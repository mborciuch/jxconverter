package com.mbor.converterservice.factories.converters;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mbor.converterservice.converters.abstractconverter.json2xml.Json2XmlConverter;
import com.mbor.converterservice.factories.nodes.NodeFactory;
import com.mbor.converterservice.factories.printers.XmlPrinterFactory;
import org.springframework.stereotype.Component;

@Component
public class Json2xmlConverterFactory {

    public Json2XmlConverter json2xmlConverter(){
        XmlPrinterFactory xmlPrinterFactory = new XmlPrinterFactory();
        NodeFactory nodeFactory = new NodeFactory(xmlPrinterFactory);
        ObjectMapper objectMapper = new ObjectMapper();
        return new Json2XmlConverter(nodeFactory, objectMapper);
    }
}
