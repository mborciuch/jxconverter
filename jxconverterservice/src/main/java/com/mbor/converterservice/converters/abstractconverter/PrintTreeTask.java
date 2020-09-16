package com.mbor.converterservice.converters.abstractconverter;

import com.mbor.converterservice.components.ComponentNode;

import java.util.concurrent.Callable;

public class PrintTreeTask implements Callable<String> {
    private final ComponentNode componentNode;
    private final ThreadLocal<Indentation> threadLocal = new ThreadLocal<>();

    public PrintTreeTask(ComponentNode componentNode) {
        this.componentNode = componentNode;
    }

    @Override
    public String call() throws Exception {
        threadLocal.set(new Indentation());
        componentNode.setPrinterThreadLocal(threadLocal);
        return componentNode.print();
    }
}
