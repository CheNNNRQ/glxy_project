package com.diodi.msmservice.service.impl;

import com.diodi.msmservice.service.MsmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * @author diodi
 * @create 2021-08-17-14:45
 */
@Service
public class MsmServiceImpl implements MsmService {
    //这个位置报错因为我把配置文件放到nacos中了 里面包含邮箱的配置
    @Autowired
    JavaMailSenderImpl mailSender;

    @Value("${spring.mail.username}")
    String sendEmail;
    /**
     * 发送邮件的方法
     * @param email 接收端的email
     * @return
     */
    @Override
    public Boolean send(String code,
                        String email) {
        if (StringUtils.isEmpty(email)) {
            return false;
        }
        SimpleMailMessage message = new SimpleMailMessage();
        //设置邮件标题
        message.setSubject("验证码");
//        sendTime = new Date();
        message.setText("尊敬的用户,您好:\n"
                                + "\n本次请求的邮件验证码为:" + code + ",本验证码5分钟内有效，请及时输入。（请勿泄露此验证码）\n"
                                + "\n如非本人操作，请忽略该邮件。\n(这是一封自动发送的邮件，请不要直接回复）");    //设置邮件正文
        //发件人
        message.setFrom(sendEmail);
        //收件人
        message.setTo(email);
        //发送邮件
        mailSender.send(message);
        return true;
    }
//    @Override
//    public boolean send(String phone,
//                        String num,
//                        Map<String, Object> param) {
//        HashMap<String, Object> result = null;
//
//        //初始化SDK
//        CCPRestSmsSDK restAPI = new CCPRestSmsSDK();
//
//        //******************************注释*********************************************
//        //*初始化服务器地址和端口                                                       *
//        //*沙盒环境（用于应用开发调试）：restAPI.init("sandboxapp.cloopen.com", "8883");*
//        //*生产环境（用户应用上线使用）：restAPI.init("app.cloopen.com", "8883");       *
//        //*******************************************************************************
//        restAPI.init("app.cloopen.com", "8883");
//
//        //******************************注释*********************************************
//        //*初始化主帐号和主帐号令牌,对应官网开发者主账号下的ACCOUNT SID和AUTH TOKEN     *
//        //*ACOUNT SID和AUTH TOKEN在登陆官网后，在“应用-管理控制台”中查看开发者主账号获取*
//        //*参数顺序：第一个参数是ACOUNT SID，第二个参数是AUTH TOKEN。                   *
//        //*******************************************************************************
//        restAPI.setAccount("8aaf07087b52c64e017b52c958310001", "74fdd763117c4d3687c6bb2e46720f95");
//
//        //******************************注释*********************************************
//        //*初始化应用ID                                                                 *
//        //*测试开发可使用“测试Demo”的APP ID，正式上线需要使用自己创建的应用的App ID     *
//        //*应用ID的获取：登陆官网，在“应用-应用列表”，点击应用名称，看应用详情获取APP ID*
//        //*******************************************************************************
//        restAPI.setAppId("8aaf07087b52c64e017b52c959b80007");
//
//        //******************************注释****************************************************************
//        //*调用发送模板短信的接口发送短信                                                                  *
//        //*参数顺序说明：                                                                                  *
//        //*第一个参数:是要发送的手机号码，可以用逗号分隔，一次最多支持100个手机号                          *
//        //*第二个参数:是模板ID，在平台上创建的短信模板的ID值；测试的时候可以使用系统的默认模板，id为1。    *
//        //*系统默认模板的内容为“【云通讯】您使用的是云通讯短信模板，您的验证码是{1}，请于{2}分钟内正确输入”*
//        //*第三个参数是要替换的内容数组。
//        // *
//        //**************************************************************************************************
//
//        //**************************************举例说明***********************************************************************
//        //*假设您用测试Demo的APP ID，则需使用默认模板ID 1，发送手机号是13800000000，传入参数为6532和5，则调用方式为           *
//        //*result = restAPI.sendTemplateSMS("13800000000","1" ,new String[]{"6532","5"});
//        // *
//        //*则13800000000手机号收到的短信内容是：【云通讯】您使用的是云通讯短信模板，您的验证码是6532，请于5分钟内正确输入     *
//        //*********************************************************************************************************************
//        String code = (String) param.get("code");
//        result = restAPI.sendTemplateSMS(phone, "1", new String[]{code, "5"});
//        System.out.println("SDKTestGetSubAccounts result=" + result);
//        if ("000000".equals(result.get("statusCode"))) {
//            //正常返回输出data包体信息（map）
//            HashMap<String, Object> data = (HashMap<String, Object>) result.get("data");
//            Set<String> keySet = data.keySet();
//            for (String key : keySet) {
//                Object object = data.get(key);
//                System.out.println(key + " = " + object);
//            }
//            return true;
//        } else {
//            //异常返回输出错误码和错误信息
//            System.out.println("错误码=" + result.get("statusCode") + " 错误信息= " + result.get("statusMsg"));
//            return false;
//        }
//    }
}
