package com.obeast.mail;

import com.obeast.mail.service.MailService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Random;
import java.util.RandomAccess;
import java.util.UUID;
import java.util.random.RandomGenerator;

@SpringBootTest
class BendanMailApplicationTests {


    @Autowired
    private TemplateEngine templateEngine;
    @Autowired
    private JavaMailSenderImpl mailSender;

    @Autowired
    MailService mailService;

    @Value("${project.name}")
    String project;

    @Value("${project.author}")
    String author;

    @Test
    void contextLoads() {
        String to = "obeast.gym@gmail.com";
        System.out.println(mailService.sendVerificationCode(to));
    }



}
