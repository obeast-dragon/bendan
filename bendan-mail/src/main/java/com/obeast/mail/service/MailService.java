package com.obeast.mail.service;



/**
 * @author wxl
 * Date 2022/10/28 9:44
 * @version 1.0
 * Description: 邮件发送
 */
public interface MailService {


    /**
     * Description: 发送邮件
     * @author wxl
     * Date: 2022/10/28 9:51
     * @param to 发送给
     * @param subject 邮件标题
     * @param text 发送内容
     * @param project 项目名
     * @param author 作者
     */
    boolean sendText(String to, String subject, String text, String project, String author);

    /**
     * Description:
     * @author wxl
     * Date: 2022/10/28 9:53
     * @param to 发送给
     */
    boolean sendVerificationCode(String to);
}
