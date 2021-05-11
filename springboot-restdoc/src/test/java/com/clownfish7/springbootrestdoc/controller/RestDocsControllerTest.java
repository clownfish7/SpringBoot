package com.clownfish7.springbootrestdoc.controller;

import com.alibaba.fastjson.JSONObject;
import com.clownfish7.springbootrestdoc.SpringbootRestdocApplicationTests;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author yzy
 * @classname RestDocsControllerTest
 * @description TODO
 * @create 2020-08-21 16:43
 */
class RestDocsControllerTest extends SpringbootRestdocApplicationTests {

    @Test
    void testRestDocsGet() throws Exception {
        this.mockMvc.perform(get("/restDoc/get"))
                .andExpect(status().isOk())
                .andDo(document("{ClassName}/{methodName}",
                        responseFields(
                                fieldWithPath("test").optional().description("测试"),
                                fieldWithPath("code").optional().description("结果")
                        )
                ));
    }

    @Test
    void testRestDocsGetPath() {
        this.mockMvc.perform(get("/restDoc/get/{param1}", "param2"))
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("{ClassName}/{methodName}",
                        pathParameters(
                                parameterWithName("param1").description("param1")),
                        responseFields(
                                fieldWithPath("test").optional().description("测试"),
                                fieldWithPath("code").optional().description("结果")
                        )
                ));
    }

    @Test
    void testRestDocsPost() throws Exception {
        this.mockMvc.perform(post("/restDoc/post").contentType(MediaType.APPLICATION_JSON).characterEncoding("UTF-8")
                .content(new JSONObject().fluentPut("k1", "v1").toJSONString()))
                .andExpect(status().isOk())
                .andDo(document("{ClassName}/{methodName}",
                        requestBody(
                                new JSONObject().fluentPut("k1", "v1")
                        ), requestFields(
                                fieldWithPath("k1").optional().description("l1")
                        ), responseFields(
                                fieldWithPath("test").optional().description("测试"),
                                fieldWithPath("code").optional().description("结果")
                        )
                ))
                .andDo(print());

    }
}