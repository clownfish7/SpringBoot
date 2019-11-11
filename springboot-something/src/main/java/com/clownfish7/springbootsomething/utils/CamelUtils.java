package com.clownfish7.springbootsomething.utils;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author yzy
 * @classname CamelUtils
 * @description 驼峰下划线互转工具类
 * @create 2019-10-15 19:44
 */
public class CamelUtils {

    public static final String UNDERLINE = "_";

    /**
     * {userSex=man, userName=yzy, userAge=18}
     * ↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓
     * {user_sex=man, user_name=yzy, user_age=18}
     */
    public static Map<String, Object> camel2UnderLine(Map<String, Object> map) {
        if (map == null) {
            return null;
        }
        Map<String, Object> result = new LinkedHashMap<>();
        map.forEach((k, v) -> {
            result.put(camel2UnderLine(k), v);
        });
        return result;
    }

    /**
     * {user_sex=man, user_name=yzy, user_age=18}
     * ↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓
     * {userSex=man, userName=yzy, userAge=18}
     */
    public static Map<String, Object> underLine2Camel(Map<String, Object> map) {
        if (map == null) {
            return null;
        }
        Map<String, Object> result = new LinkedHashMap<>();
        map.forEach((k, v) -> {
            result.put(underLine2Camel(k), v);
        });
        return result;
    }

    /**
     * abcDefGhi -> abc_def_ghi
     */
    public static String camel2UnderLine(String str) {
        return str.replaceAll("([a-z])([A-Z])", "$1" + UNDERLINE + "$2").toLowerCase();
    }

    /**
     * abc_def_ghi -> abcDefGhi
     */
    public static String underLine2Camel(String str) {
        String[] split = str.split(UNDERLINE);
        StringBuilder sb = new StringBuilder();
        sb.append(split[0]);
        for (int i = 1; i < split.length; i++) {
            sb.append(split[i].substring(0, 1).toUpperCase()).append(split[i].substring(1));
        }
        return sb.toString();
    }
}
