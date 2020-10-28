package pers.johnx.email;

import org.apache.commons.io.IOUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring/spring-mail.xml")
public class emailTest {

    @Autowired
    private JavaMailSenderImpl javaMailSender;

    @Test
    public void t1() {

        //创建简单邮件
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom("XXXXXXXXX@126.com");
        simpleMailMessage.setTo("XXXXXXX@qq.com");
        simpleMailMessage.setSubject("Java测试邮件！！！");
        simpleMailMessage.setText("好好学习，天天向上!");

        //发送邮件
        javaMailSender.send(simpleMailMessage);
        System.out.println("发送成功！");
    }

    @Test
    public void t2(){

        //创建带附件的邮件
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        //创建助手类
        try {

            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
            mimeMessageHelper.setFrom("XXXXXX@126.com");
            mimeMessageHelper.setTo("XXXXXXXX@qq.com");
            mimeMessageHelper.setSubject("Java附件测试！");
            mimeMessageHelper.setText("图片");

            File file = new File("C:\\Users\\Administrator\\Documents\\Saved Pictures\\2024108.jpg");

            mimeMessageHelper.addAttachment("图图.jpg", file);

            javaMailSender.send(mimeMessage);
            System.out.println("发送成功！");

        } catch (MessagingException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void t3() throws MessagingException {

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();

        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);

        mimeMessageHelper.setFrom("XXXXX@126.com");
        mimeMessageHelper.setTo("XXXXX@qq.com");
        mimeMessageHelper.setSubject("Java HTML附件测试！");

        String html = "<!doctype html>\"+\n" +
                "                     \"<html >\"+\n" +
                "                      \"<head>\"+\n" +
                "                    \"<meta charset=\\\"UTF-8\\\"> \"+\n" +
                "                    \"<title>test mail</title>\"+\n" +
                "                    \"</head>\"+\n" +
                "                    \"<body>\"+\n" +
                "                    \"<h1>这是一个html格式的邮件8</h1>\"+\n" +
                "                    \"<i><b>演示斜体</b></i>\"+\n" +
                "                    \"<img src='cid:pic'>\"+\n" +
                "                     \"</body>\"+\n" +
                "                    \"</html>";

        //设定要发送的邮件内容,支持html
        mimeMessageHelper.setText(html, true);

        //在设定邮件嵌入图片， 设定嵌入id
        mimeMessageHelper.addInline("pic", new File("C:\\Users\\Administrator\\Documents\\Saved Pictures\\2008044.jpg"));

        javaMailSender.send(mimeMessage);
        System.out.println("发送成功！");
    }

    @Test
    public void t4() throws MessagingException, IOException {

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();

        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);

        mimeMessageHelper.setFrom("XXXXX@126.com");
        mimeMessageHelper.setTo("XXXXXXX@qq.com");
        mimeMessageHelper.setSubject("Java HTML附件测试！");

        String htmlStr = IOUtils.toString(new FileInputStream("D:\\Java_Code\\Java\\Java_Distributed\\SendEmailDemo\\src\\main\\resources\\html.txt"), "utf-8");

        //设定要发送的邮件内容
        mimeMessageHelper.setText(htmlStr, true);

        //在设定邮件嵌入图片， 设定嵌入id
        mimeMessageHelper.addInline("pic", new File("C:\\Users\\Administrator\\Documents\\Saved Pictures\\2008044.jpg"));

        javaMailSender.send(mimeMessage);
        System.out.println("发送成功！");
    }

}
