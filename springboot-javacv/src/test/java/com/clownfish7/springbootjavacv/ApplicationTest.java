package com.clownfish7.springbootjavacv;

import org.bytedeco.ffmpeg.ffmpeg;
import org.bytedeco.javacpp.Loader;
import org.junit.jupiter.api.Test;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

/**
 * classname ApplicationTest
 * description TODO
 * create 2023-01-15 14:40
 */
public class ApplicationTest {

    @Test
    void test() throws IOException, InterruptedException {
        String ffmpeg = Loader.load(ffmpeg.class);
        ProcessBuilder processBuilder = new ProcessBuilder(ffmpeg, "-version");
        processBuilder.redirectErrorStream(true);
        Process process = processBuilder.start();
        process.waitFor();
        InputStream inputStream = process.getInputStream();
        System.out.println(StreamUtils.copyToString(inputStream, Charset.defaultCharset()));
        inputStream.close();
        System.out.println(process.exitValue());
    }
}
