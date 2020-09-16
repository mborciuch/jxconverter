package com.mbor.converterservice.components;

import com.mbor.converterservice.converters.abstractconverter.Indentation;

public abstract class IndentationPrinter implements IIndentationPrinter {

    private ThreadLocal<Indentation> threadLocal;

    public ThreadLocal<Indentation> getThreadLocal() {
        return threadLocal;
    }

    public void setThreadLocal(ThreadLocal<Indentation> threadLocal) {
        this.threadLocal = threadLocal;
    }

    public void increaseIndentation(){
        getThreadLocal().get().increaseIndentation();
    }

    public void decreaseIndentation(){
        getThreadLocal().get().decreaseIndentation();
    }

    @Override
    public int getCurrentIndentation() {
        return getThreadLocal().get().getCurrentIndentation();
    }
}
