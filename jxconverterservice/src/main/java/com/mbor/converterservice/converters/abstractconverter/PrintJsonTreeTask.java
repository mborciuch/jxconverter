package com.mbor.converterservice.converters.abstractconverter;

import com.mbor.converterservice.components.ComponentNode;

import java.util.concurrent.Callable;

public class PrintJsonTreeTask implements Callable<String> {

    private final ComponentNode componentNode;
    private ThreadLocal<Indentation> threadLocal = new ThreadLocal<>();

    public PrintJsonTreeTask(ComponentNode componentNode) {
        this.componentNode = componentNode;
    }

    @Override
    public String call() throws Exception {
        threadLocal.set(new Indentation());
        return componentNode.print();
    }
}
