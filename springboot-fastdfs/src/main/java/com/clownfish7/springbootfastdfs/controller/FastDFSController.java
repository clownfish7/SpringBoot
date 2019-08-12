package com.clownfish7.springbootfastdfs.controller;

import com.clownfish7.springbootfastdfs.utils.FastDFSClientWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author yzy
 * @classname FasrDFSController
 * @description TODO
 * @create 2019-08-12 18:02
 */
@RestController
public class FastDFSController {
    @Autowired
    private FastDFSClientWrapper dfsClient;

    @PostMapping("/files-anon/fdfsupload")
    public String upload(@RequestParam("file") MultipartFile file) throws Exception {
        String imgUrl = dfsClient.uploadFile(file);
        return imgUrl;
    }
}
