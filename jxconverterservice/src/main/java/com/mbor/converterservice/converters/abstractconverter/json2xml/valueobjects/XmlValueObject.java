package com.mbor.converterservice.converters.abstractconverter.json2xml.valueobjects;

import com.mbor.converterservice.components.ValueObject.AbstractValueObject;

public class XmlValueObject extends AbstractValueObject {
    private String value;

    public XmlValueObject(String value) {
        this.value = value;
    }

    @Override
    public String getValue() {
        return  value;
    }
}
