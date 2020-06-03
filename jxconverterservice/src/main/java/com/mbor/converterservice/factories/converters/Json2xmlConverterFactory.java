package com.mbor.converterservice.factories.converters;

import com.mbor.converterservice.converters.abstractconverter.xml2json.Json2xmlConverter;
import com.mbor.converterservice.factories.nodes.NodeFactory;
import com.mbor.converterservice.factories.printers.JsonPrinterFactory;
import org.springframework.stereotype.Component;

@Component
public class Json2xmlConverterFactory {

    public Json2xmlConverter json2xmlConverter(){
        JsonPrinterFactory jsonNodePrinterFactory = new JsonPrinterFactory();
        NodeFactory nodeFactory = new NodeFactory(jsonNodePrinterFactory);
        return new Json2xmlConverter(nodeFactory);
    }
}
