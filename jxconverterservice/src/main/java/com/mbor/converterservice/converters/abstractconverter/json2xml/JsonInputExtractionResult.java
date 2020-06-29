package com.mbor.converterservice.converters.abstractconverter.json2xml;

import com.mbor.converterservice.converters.abstractconverter.InputExtractionResult;

import java.util.Optional;

public class JsonInputExtractionResult implements InputExtractionResult {

    private Object wholeLine;
    private String name;
    private String attributes;
    private Object value;

    @Override
    public Object getWholeLine() {
        return wholeLine;
    }

    public void setWholeLine(Object wholeLine) {
        this.wholeLine = wholeLine;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public Optional<String> getAttributes() {
        return Optional.ofNullable(attributes);
    }

    @Override
    public void setAttributes(String attributes) {
        this.attributes = attributes;
    }

    @Override
    public Object getValue() {
        return value;
    }

    @Override
    public void setValue(Object value) {
        this.value = value;
    }
}
