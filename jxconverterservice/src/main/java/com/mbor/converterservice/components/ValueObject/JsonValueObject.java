package com.mbor.converterservice.components.ValueObject;

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
