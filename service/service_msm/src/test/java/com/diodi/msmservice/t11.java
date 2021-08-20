package com.diodi.msmservice;

import com.diodi.commonutils.R;
import com.diodi.commonutils.RandomUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.UUID;

/**
 * @author diodi
 * @create 2021-08-17-18:40
 */

@RunWith(SpringRunner.class)
@SpringBootTest(classes={ServiceMsmMain8005.class})// 指定启动类
public class t11 {
    @Autowired
    JavaMailSenderImpl mailSender;

    @Test
    public void t22() {

        getCode("dio_di@163.com",mailSender,"1310885336@qq.com" );

    }
        /**
     * 验证码
     */
    private String code;
    /**
     * 发送时间
     */
    private Date sendTime;

    public R getCode(String sender, JavaMailSenderImpl mailSender, String receiver) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setSubject("验证码");//设置邮件标题
        code = RandomUtil.getFourBitRandom();
        sendTime = new Date();
        message.setText("尊敬的用户,您好:\n"
                                + "\n本次请求的邮件验证码为:" + code + ",本验证码5分钟内有效，请及时输入。（请勿泄露此验证码）\n"
                                + "\n如非本人操作，请忽略该邮件。\n(这是一封自动发送的邮件，请不要直接回复）");    //设置邮件正文
        message.setFrom(sender);//发件人
        message.setTo(receiver);//收件人
        mailSender.send(message);//发送邮件
        return R.ok();
    }
    @Test
    public void contextLoads() {
        /*一封简单的邮件*/
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        /*邮件标题*/
        mailMessage.setSubject("邮件验证码");
        /*邮件内容(随机生成验证码)*/
        String code = UUID.randomUUID().toString().substring(0, 4);
        mailMessage.setText(code);
        /*发送人(必须和配置文件中的username相同)*/
        mailMessage.setFrom("dio_di@163.com");
        /*收件人*/
        mailMessage.setTo("1310885336@qq.com");
        /*发送*/
        mailSender.send(mailMessage);
    }
}
