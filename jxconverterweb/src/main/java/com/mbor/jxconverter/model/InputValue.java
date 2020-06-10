package com.mbor.jxconverter.model;

import com.fasterxml.jackson.annotation.JsonRawValue;

public class InputValue {

    @JsonRawValue
    String value;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
