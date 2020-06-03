package com.mbor.jxconverter.model;

import com.fasterxml.jackson.annotation.JsonRawValue;

public class OutputValue {

    @JsonRawValue
    private String value;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public static OutputValueBuilder outputValueBuilder(){
        return new OutputValueBuilder();
    };

    public static class OutputValueBuilder {

        private String value;

        public OutputValueBuilder setValue(String value) {
            this.value = value;
            return this;
        }

        public OutputValue build(){
            OutputValue outputValue = new OutputValue();
            outputValue.setValue(this.value);
            return outputValue;
        }
    }

}


