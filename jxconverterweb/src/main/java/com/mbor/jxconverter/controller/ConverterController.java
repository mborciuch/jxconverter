package com.mbor.jxconverter.controller;

import com.mbor.converterservice.converters.abstractconverter.json2xml.Xml2jsonConverter;
import com.mbor.converterservice.converters.abstractconverter.xml2json.Json2xmlConverter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/converters")
public class ConverterController {

    private final Json2xmlConverter json2xmlConverter;
    private final Xml2jsonConverter xml2jsonConverter;

    public ConverterController(Json2xmlConverter json2xmlConverter, Xml2jsonConverter xml2jsonConverter) {
        this.json2xmlConverter = json2xmlConverter;
        this.xml2jsonConverter = xml2jsonConverter;
    }

    @GetMapping(params = "conversionType=toJson")
    public ResponseEntity<String> convertXmlToJson(@RequestBody InputValue inputValue){
        String result = xml2jsonConverter.convert(inputValue.getValue);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping(params = "conversionType=toXml")
    public ResponseEntity<String> convertJsonToXml(@RequestBody InputValue inputValue){
        String result = json2xmlConverter.convert(inputValue.getValue);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

}
