package com.mbor.converterservice.converters.abstractconverter;

public class Indentation {
    private final int offset = 4;
    private int currentIndentation = 0;

    public void increaseIndentation(){
        currentIndentation = currentIndentation + offset;
    }
    public void decreaseIndentation(){
        currentIndentation = currentIndentation - offset;
    }
}
