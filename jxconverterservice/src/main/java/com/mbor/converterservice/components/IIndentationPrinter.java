package com.mbor.converterservice.components;

import com.mbor.converterservice.converters.abstractconverter.Indentation;

public interface IIndentationPrinter {

    String prepareElement(AbstractNode abstractNode);

    ThreadLocal<Indentation> getThreadLocal();

    void setThreadLocal(ThreadLocal<Indentation> threadLocal);

     void increaseIndentation();

     void decreaseIndentation();

     int getCurrentIndentation();
}
