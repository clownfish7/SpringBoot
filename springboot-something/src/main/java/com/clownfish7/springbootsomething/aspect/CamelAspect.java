package com.clownfish7.springbootsomething.aspect;

import com.clownfish7.springbootsomething.utils.CamelUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @author yzy
 * @classname CamelAspect
 * @description map-key驼峰下划线命名转换切面
 * @create 2019-10-16 9:10
 */
@Slf4j
@Aspect
@Component
public class CamelAspect {

    @Pointcut("@annotation(com.clownfish7.springbootsomething.annotation.Camel)")
    public void camelPointCut() {

    }

    @Around("camelPointCut()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        Map<String, Object> params = new LinkedHashMap<>();
        if (point.getArgs()[0] instanceof Map) {
            params = (Map<String, Object>) point.getArgs()[0];
            params = CamelUtils.underLine2Camel(params);
        }

        Object result = point.proceed(new Object[]{params});

        if ("map".equals(judge(result))) {
            return CamelUtils.camel2UnderLine((Map<String, Object>) result);
        }
        if ("list<map>".equals(judge(result))) {
            List<Map<String, Object>> mapList = (List<Map<String, Object>>) result;
            List<Map<String, Object>> underLineResult = new LinkedList<>();
            mapList.forEach(item -> {
                underLineResult.add(CamelUtils.camel2UnderLine(item));
            });
            return underLineResult;
        }
        return result;
    }

    private String judge(Object obj) {
        if (obj == null) {
            return "";
        }
        if (obj instanceof Map) {
            return "map";
        }
        if (obj instanceof List) {
            List list = (List) obj;
            String str = "list";
            if (list.size() > 0 && list.get(0) instanceof Map) {
                str += "<map>";
            }
            return str;
        }
        return "other";
    }
}
