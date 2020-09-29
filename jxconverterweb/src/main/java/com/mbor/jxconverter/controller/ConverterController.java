package com.mbor.jxconverter.controller;

import com.mbor.converterservice.converters.abstractconverter.json2xml.Json2XmlConverter;
import com.mbor.converterservice.converters.abstractconverter.xml2json.Xml2JsonConverter;
import com.mbor.jxconverter.model.InputValue;
import com.mbor.jxconverter.model.OutputValue;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/converters")
@CrossOrigin(origins = "*")
public class ConverterController {

    private final Json2XmlConverter json2xmlConverter;
    private final Xml2JsonConverter xml2jsonConverter;

    public ConverterController(Json2XmlConverter json2xmlConverter, Xml2JsonConverter xml2jsonConverter) {
        this.json2xmlConverter = json2xmlConverter;
        this.xml2jsonConverter = xml2jsonConverter;
    }

    //TODO Refactor
    @PostMapping(params = "conversionType=toJson",
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<OutputValue> convertXmlToJson(@RequestBody InputValue inputValue){
        String result = xml2jsonConverter.convert(inputValue.getValue());
        result = addQuotationMarks(result.replaceAll("\\n",  "\\\\n").replaceAll("\"", "\\\\\""));
        OutputValue outputValue = OutputValue.outputValueBuilder()
                .setValue(result)
                .build();
        return new ResponseEntity<>(outputValue, HttpStatus.OK);
    }

    @PostMapping(params = "conversionType=toXml",
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<OutputValue> convertJsonToXml(@RequestBody InputValue inputValue){
        String result = json2xmlConverter.convert(inputValue.getValue());
        result = addQuotationMarks(result).replaceAll("\\n",  "\\\\n");
        OutputValue outputValue = OutputValue.outputValueBuilder()
                .setValue(result)
                .build();
        return new ResponseEntity<>(outputValue, HttpStatus.OK);
    }

    private String addQuotationMarks(String result) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("\"").append(result).append("\"");
        return stringBuilder.toString();
    }
}

