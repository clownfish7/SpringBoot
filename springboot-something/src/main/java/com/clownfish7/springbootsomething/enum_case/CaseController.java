package com.clownfish7.springbootsomething.enum_case;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * classname CaseController
 * description TODO
 * create 2022-06-06 10:00
 */
@RestController
@RequestMapping("/enum")
public class CaseController {

    @RequestMapping("/case1")
    public String case1(CodeNameEnum caseEnum) {
        return "- " + caseEnum.code + " - " + caseEnum.name;
    }

    @RequestMapping("/case2")
    public String case2(@RequestParam CodeNameEnum caseEnum) {
        return "- " + caseEnum.code + " - " + caseEnum.name;
    }

    @RequestMapping("/case3")
    public String case3(@RequestBody CaseRequest caseRequest) {
        return "- " + caseRequest.getCaseEnum().code + " - " + caseRequest.getCaseEnum().name;
    }

    @RequestMapping("/case4")
    public CaseResponse case4(@RequestBody CaseRequest caseRequest) {
        CaseResponse response = new CaseResponse();
        response.setCodeNameEnum(caseRequest.getCaseEnum());
        return response;
    }
}
