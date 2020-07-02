package com.mbor.converterservice.converters.abstractconverter.xml2json.valueobjects;

import com.mbor.converterservice.components.ValueObject.AbstractValueObject;

public class JsonNullValueObject extends AbstractValueObject {

    @Override
    public String getValue() {
        return "null";
    }
}
