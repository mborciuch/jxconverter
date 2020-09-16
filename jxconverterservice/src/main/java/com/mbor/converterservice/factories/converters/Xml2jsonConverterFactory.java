package com.mbor.converterservice.factories.converters;

import com.mbor.converterservice.converters.abstractconverter.xml2json.Xml2JsonConverter;
import com.mbor.converterservice.factories.nodes.NodeFactory;
import com.mbor.converterservice.factories.printers.JsonPrinterFactory;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Xml2jsonConverterFactory {

    public Xml2JsonConverter xml2jsonConverter(){
        JsonPrinterFactory jsonNodePrinterFactory = new JsonPrinterFactory();
        NodeFactory nodeFactory = new NodeFactory(jsonNodePrinterFactory);
        ExecutorService executor = Executors.newFixedThreadPool(10);
        return new Xml2JsonConverter(nodeFactory, executor);
    }
}
