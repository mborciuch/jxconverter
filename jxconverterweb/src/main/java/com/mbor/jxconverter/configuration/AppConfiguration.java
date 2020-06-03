package com.mbor.jxconverter.configuration;


import com.mbor.converterservice.converters.abstractconverter.json2xml.Json2XmlConverter;
import com.mbor.converterservice.converters.abstractconverter.xml2json.Xml2JsonConverter;
import com.mbor.converterservice.factories.converters.Json2xmlConverterFactory;
import com.mbor.converterservice.factories.converters.Xml2jsonConverterFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfiguration {

    @Bean
    Json2XmlConverter json2xmlConverter(){
        return new Json2xmlConverterFactory().json2xmlConverter();
    }

    @Bean
    Xml2JsonConverter xml2jsonConverterFactory(){
        return new Xml2jsonConverterFactory().xml2jsonConverter();
    }

}
