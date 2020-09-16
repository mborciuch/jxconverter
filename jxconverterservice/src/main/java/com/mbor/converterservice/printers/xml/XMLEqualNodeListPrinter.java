package com.mbor.converterservice.printers.xml;

import com.mbor.converterservice.components.AbstractNode;
import com.mbor.converterservice.components.NodeList;
import com.mbor.converterservice.components.Printer;
import com.mbor.converterservice.converters.abstractconverter.xml2json.Xml2JsonConverter;
import com.mbor.converterservice.utils.CommonUtils;

import static com.mbor.converterservice.utils.JsonUtils.*;

public class XMLEqualNodeListPrinter implements Printer {

    @Override
    public String prepareElement(AbstractNode abstractNode) {
        return "";
    }

}
