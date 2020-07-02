package com.mbor.converterservice.converters.abstractconverter.xml2json.valueobjects;

import com.mbor.converterservice.components.ValueObject.AbstractValueObject;

public class JsonValueObject extends AbstractValueObject {
    private String value;

    public JsonValueObject(String value) {
        this.value = value;
    }

    @Override
    public String getValue() {
        return "\"" + value + "\"";
    }
}
