package com.clownfish7.springbootsomething.enum_case;

import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * classname CodeName2EnumConverterFactory
 * description TODO
 * create 2022-06-06 10:19
 */
public class CodeName2EnumConverterFactory implements ConverterFactory<String, CodeNameEnum> {

    @SuppressWarnings("rawtypes")
    private static final Map<Class, Converter> CONVERTERS = new HashMap<>();

    @Override
    public <T extends CodeNameEnum> Converter<String, T> getConverter(Class<T> targetType) {
        Converter<String, T> converter = CONVERTERS.get(targetType);
        if (converter == null) {
            converter = new CodeName2EnumConverter<>(targetType);
            CONVERTERS.put(targetType, converter);
        }
        return converter;
    }
}
