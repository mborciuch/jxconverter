package com.mbor.converterservice.converters.abstractconverter;

import java.util.Optional;

public interface InputExtractionResult {

    Object getWholeLine();

    void setWholeLine(Object wholeLine);

    String getName();

    void setName(String name);

    Optional<String> getAttributes();

    void setAttributes(String attributes);

    Object getValue();

    void setValue(Object value);
}
