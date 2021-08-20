package com.diodi.commonutils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * @author helen
 * @since 2019/10/16
 */
public class JwtUtils {
    //常量
    /**
     * token过期时间
     */
    public static final long EXPIRE = 1000 * 60 * 60 * 24;
    /**
     * 秘钥 随便写的
     */
    public static final String APP_SECRET = "ukc8BDbRigUDaY6pZFfWus2jZWLPHO";

    /**
     * 生成token字符串的方法
     * @param id       用户id
     * @param nickname 用户名
     * @return
     */
    public static String getJwtToken(String id,
                                     String nickname) {

        String JwtToken = Jwts.builder()
                              //设置头信息
                              .setHeaderParam("typ", "JWT")
                              .setHeaderParam("alg", "HS256")
                              //设置过期时间
                              .setSubject("guli-user")
                              .setIssuedAt(new Date())
                              .setExpiration(new Date(System.currentTimeMillis() + EXPIRE))
                              //设置token主体部分,存储用户信息
                              .claim("id", id)
                              .claim("nickname", nickname)
                              //设置签名哈希（防伪标志）
                              .signWith(SignatureAlgorithm.HS256, APP_SECRET)
                              .compact();

        return JwtToken;
    }

    /**
     * 判断token是否存在与有效
     * @param jwtToken
     * @return
     */
    public static boolean checkToken(String jwtToken) {
        if (StringUtils.isEmpty(jwtToken)) {
            return false;
        }
        try {
            Jwts.parser().setSigningKey(APP_SECRET).parseClaimsJws(jwtToken);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 判断token是否存在与有效
     * @param request
     * @return
     */
    public static boolean checkToken(HttpServletRequest request) {
        try {
            //根据设置的防伪码解析token
            String jwtToken = request.getHeader("token");
            if (StringUtils.isEmpty(jwtToken)) {
                return false;
            }
            Jwts.parser().setSigningKey(APP_SECRET).parseClaimsJws(jwtToken);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 根据token获取会员id
     * @param request
     * @return
     */
    public static String getMemberIdByJwtToken(HttpServletRequest request) {
        String jwtToken = request.getHeader("token");
        if (StringUtils.isEmpty(jwtToken)) {
            return "";
        }
        Jws<Claims> claimsJws = Jwts.parser().setSigningKey(APP_SECRET).parseClaimsJws(jwtToken);
        //获取token有效载荷【用户信息】
        Claims claims = claimsJws.getBody();
        return (String) claims.get("id");
    }
}
