package com.mbor.converterservice.components.ValueObject;

public class EmptyValueObject extends AbstractValueObject {
    @Override
    public String getValue() {
        return "\"\"";
    }
}
