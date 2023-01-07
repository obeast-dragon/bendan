package com.obeast.common.mail.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.internet.MimeMessage;
import java.util.random.RandomGenerator;

/**
 * @author wxl
 * Date 2022/10/28 9:48
 * @version 1.0
 * Description: 邮箱发送
 */
@Service("MailService")
@RequiredArgsConstructor
public class MailServiceImpl implements MailService {
    private final TemplateEngine templateEngine;

    private final JavaMailSenderImpl mailSender;

    @Value("${project.name}")
    String project;

    @Value("${project.author}")
    String author;

    //todo 发送的时候需要多线程；发送完的消息应该丢到MQ然后让消费者消费
    @Override
    public boolean sendVerificationCode(String to) {
        String randomNums = String.valueOf(RandomGenerator.getDefault().nextLong(100000L, 999999L));
        return sendText(to, "验证码", randomNums, project, author);
    }


    @Override
    public boolean sendText(String to, String subject, String text, String project, String author) {

        Context context = new Context();
        context.setVariable("project", project);
        context.setVariable("author", "obeast");
        context.setVariable("code", text);
        String emailContent = templateEngine.process("mail", context);
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = null;
        try {
            helper = new MimeMessageHelper(message, true);
            helper.setFrom("1468258057@qq.com");
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(emailContent, true);
            mailSender.send(message);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
