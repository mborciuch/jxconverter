package com.mbor.converterservice.converters.abstractconverter.json2xml.valueobjects;

import com.mbor.converterservice.components.ValueObject.AbstractValueObject;

public class XmlNullValueObject extends AbstractValueObject {
    @Override
    public String getValue() {
        return null;
    }
}
