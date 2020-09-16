package com.mbor.converterservice.converters.abstractconverter;

import com.mbor.converterservice.components.ComponentNode;

import java.util.concurrent.Callable;

public class PrintXmlTreeTask implements Callable<String> {

    private final ComponentNode componentNode;


    public PrintXmlTreeTask(ComponentNode componentNode) {
        this.componentNode = componentNode;
    }

    @Override
    public String call() throws Exception {
        return null;
    }
}
