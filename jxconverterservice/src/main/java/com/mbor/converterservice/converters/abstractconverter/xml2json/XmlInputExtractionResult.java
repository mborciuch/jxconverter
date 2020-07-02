package com.mbor.converterservice.converters.abstractconverter.xml2json;

import com.mbor.converterservice.converters.abstractconverter.InputExtractionResult;
import com.mbor.converterservice.exception.ProcessingException;

import java.util.Optional;

public class XmlInputExtractionResult implements InputExtractionResult {

    private String wholeLine;
    private String name;
    private String attributes;
    private String value;

    public String getWholeLine() {
        return wholeLine;
    }

    @Override
    public void setWholeLine(Object wholeLine) {
        if(!(wholeLine instanceof String)){
            throw new ProcessingException("Whole line should be String");
        }
        setWholeLine((String) wholeLine);
    }

    private void setWholeLine(String wholeLine) {
        this.wholeLine = wholeLine;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Optional<String> getAttributes() {
        return Optional.ofNullable(attributes);
    }

    public void setAttributes(String attributes) {
        this.attributes = attributes;
    }

    public Optional<String> getValue() {
        return Optional.ofNullable(value);
    }

    @Override
    public void setValue(Object value) {
        if(!(value instanceof String)){
            throw new ProcessingException("Whole line should be String");
        }
        setValue((String)value);
    }

    private void setValue(String value) {
        this.value = value;
    }

}
