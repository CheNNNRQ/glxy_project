//import com.cloopen.rest.sdk.BodyType;
//import com.cloopen.rest.sdk.CCPRestSmsSDK;
//import org.junit.Test;
//
//import java.util.HashMap;
//import java.util.Set;
//
///**
// * @author diodi
// * @create 2021-08-17-14:51
// */
//public class t1 {
//    @Test
//    public void t11() {
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
//        result = restAPI.sendTemplateSMS("17633063531", "1", new String[]{"111111", "5"});
//        System.out.println("SDKTestGetSubAccounts result=" + result);
//        if ("000000".equals(result.get("statusCode"))) {
//            //正常返回输出data包体信息（map）
//            HashMap<String, Object> data = (HashMap<String, Object>) result.get("data");
//            Set<String> keySet = data.keySet();
//            for (String key : keySet) {
//                Object object = data.get(key);
//                System.out.println(key + " = " + object);
//            }
//        } else {
//            //异常返回输出错误码和错误信息
//            System.out.println("错误码=" + result.get("statusCode") + " 错误信息= " + result.get("statusMsg"));
//        }
//    }
//
//    @Test
//    public void t12() {
//        //生产环境请求地址：app.cloopen.com
//        String serverIp = "app.cloopen.com";
//        //请求端口
//        String serverPort = "8883";
//        //主账号,登陆云通讯网站后,可在控制台首页看到开发者主账号ACCOUNT SID和主账号令牌AUTH TOKEN
//        String accountSId = "8aaf07087b52c64e017b52c958310001";
//        String accountToken = "74fdd763117c4d3687c6bb2e46720f95";
//        //请使用管理控制台中已创建应用的APPID
//        String appId = "8aaf07087b52c64e017b52c959b80007";
//        CCPRestSmsSDK sdk = new CCPRestSmsSDK();
//        sdk.init(serverIp, serverPort);
//        sdk.setAccount(accountSId, accountToken);
//        sdk.setAppId(appId);
//        sdk.setBodyType(BodyType.Type_JSON);
//        String to = "17633063531";
//        String templateId = "1";
//        String[] datas = {"变量1", "变量2", "变量3"};
//        String subAppend = "1234";  //可选 扩展码，四位数字 0~9999
//        String reqId = "fadfafas1";  //可选 第三方自定义消息id，最大支持32位英文数字，同账号下同一自然天内不允许重复
//        //HashMap<String, Object> result = sdk.sendTemplateSMS(to,templateId,datas);
//        HashMap<String, Object> result = sdk.sendTemplateSMS(to, templateId, datas, subAppend, reqId);
//        if ("000000".equals(result.get("statusCode"))) {
//            //正常返回输出data包体信息（map）
//            HashMap<String, Object> data = (HashMap<String, Object>) result.get("data");
//            Set<String> keySet = data.keySet();
//            for (String key : keySet) {
//                Object object = data.get(key);
//                System.out.println(key + " = " + object);
//            }
//        } else {
//            //异常返回输出错误码和错误信息
//            System.out.println("错误码=" + result.get("statusCode") + " 错误信息= " + result.get("statusMsg"));
//        }
//    }
//}
