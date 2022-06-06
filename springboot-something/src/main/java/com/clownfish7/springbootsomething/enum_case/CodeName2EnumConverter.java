package com.clownfish7.springbootsomething.enum_case;

import org.springframework.core.convert.converter.Converter;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * classname CodeName2EnumConverter
 * description TODO
 * create 2022-06-06 10:13
 */
public class CodeName2EnumConverter<T extends CodeNameBaseEnum> implements Converter<String, T> {

    private final Map<String, T> codeEnumMap = new HashMap<>();
    private final Map<String, T> nameEnumMap = new HashMap<>();

    public CodeName2EnumConverter(Class<T> enumType) {
        Arrays.stream(enumType.getEnumConstants())
                .forEach(x -> {
                    codeEnumMap.put(x.getCode().toString(), x);
                    nameEnumMap.put(x.getName(), x);
                });
    }

    @Override
    public T convert(String s) {
        return Optional.of(s).map(codeEnumMap::get)
                .orElseGet(
                        () -> Optional.of(s)
                                .map(nameEnumMap::get)
                                .orElseThrow(() -> new RuntimeException("can not find enum!"))
                );
    }
}
