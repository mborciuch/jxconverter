package com.mbor.converterservice.components.ValueObject;

public class ValueObject extends AbstractValueObject {
    private String value;

    public ValueObject(String value) {
        this.value = value;
    }

    @Override
    public String getValue() {
        return "\"" + value + "\"";
    }
}
