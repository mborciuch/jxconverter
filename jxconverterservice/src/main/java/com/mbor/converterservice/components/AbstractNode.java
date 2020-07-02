package com.mbor.converterservice.components;

import java.util.LinkedHashMap;
import java.util.Map;

 public abstract class AbstractNode {

    private Map<String, String> attributes = new LinkedHashMap<>();

    public abstract String getNodeName();

    public abstract void setNodeName(String nodeName);

    public abstract String print();

     public Map<String, String> getAttributes() {
        return attributes;
    }

    public void setAttributes(Map<String, String> attributes) {
        this.attributes = attributes;
    }

    public boolean hasAttributes(){
         return !attributes.isEmpty();
    }
}
