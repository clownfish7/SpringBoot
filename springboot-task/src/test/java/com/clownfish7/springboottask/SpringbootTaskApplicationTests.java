package com.clownfish7.springboottask;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.test.context.junit4.SpringRunner;

import javax.mail.internet.MimeMessage;
import java.io.File;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringbootTaskApplicationTests {

    @Autowired
    private JavaMailSenderImpl mailSender;

    /**
     * 发送简单邮件
     */
    @Test
    public void contextLoads() {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setSubject("通知：今晚开会");
        message.setText("今晚12点开会");
        message.setTo("279505647@qq.com");
        message.setFrom("279505647@qq.com");
        mailSender.send(message);
    }

    /**
     * 发送复杂邮件
     */
    @Test
    public void sendMsg() throws Exception {

        //1.创建一个复杂的消息邮件
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage,true);

        //邮件设置
        helper.setSubject("通知：今晚开会");
        helper.setText("<b style='color:red'>今晚12点开会</b>",true);
        helper.setTo("279505647@qq.com");
        helper.setFrom("279505647@qq.com");

        //上传文件
        helper.addAttachment("1.jpg", new File("E:\\YOU\\Pictures\\3a7bc35c10385343e00698bc9913b07ecb808836.jpg"));
        helper.addAttachment("2.jpg", new File("E:\\YOU\\Pictures\\c5d160380cd791237806ffeaa7345982b3b7801f.jpg"));

        mailSender.send(mimeMessage);
    }
}
