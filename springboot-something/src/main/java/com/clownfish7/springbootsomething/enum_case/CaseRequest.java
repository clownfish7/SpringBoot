package com.clownfish7.springbootsomething.enum_case;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

/**
 * classname CaseRequest
 * description TODO
 * create 2022-06-06 10:03
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CaseRequest {
    private CodeNameEnum caseEnum;
}
