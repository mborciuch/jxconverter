package com.mbor.converterservice.components.ValueObject;

public class XmlValueObject extends AbstractValueObject{
    private String value;

    public XmlValueObject(String value) {
        this.value = value;
    }

    @Override
    public String getValue() {
        return  value;
    }
}
