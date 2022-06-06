package com.clownfish7.springbootsomething.enum_case;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * classname CodeNameEnum
 * description TODO
 * create 2022-06-06 9:58
 */
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum CodeNameEnum implements CodeNameBaseEnum {

    ENUM_ONE(1, "first"),
    ENUM_TWO(2, "second"),
    ENUM_THREE(3, "third"),
    ENUM_FIVE(5, "fifth");

    Integer code;
    String name;

    CodeNameEnum(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    @Override
    public Integer getCode() {
        return code;
    }

    @Override
    @JsonValue
    public String getName() {
        return name;
    }

    @JsonCreator
    public static CodeNameEnum getEnum(String value) {
        for (CodeNameEnum e : CodeNameEnum.values()) {
            if (e.getCode().toString().equals(value)) {
                return e;
            }
            if (e.getName().equals(value)) {
                return e;
            }
        }
        return null;
    }
}
