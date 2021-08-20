package com.diodi.serviceUcenter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.diodi.commonutils.JwtUtils;
import com.diodi.commonutils.MD5;
import com.diodi.serviceUcenter.entity.UcenterMember;
import com.diodi.serviceUcenter.mapper.UcenterMemberMapper;
import com.diodi.serviceUcenter.service.UcenterMemberService;
import com.diodi.serviceUcenter.vo.RegisterVo;
import com.diodi.servicebase.exceptionhandler.GuliException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * <p>
 * 会员表 服务实现类
 * </p>
 * @author diodi
 * @since 2021-08-18
 */
@Service
public class UcenterMemberServiceImpl extends ServiceImpl<UcenterMemberMapper, UcenterMember> implements UcenterMemberService {
    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    /**
     * 用户注册
     * @param registerVo 用户对象
     * @return
     */
    @Override
    public void register(RegisterVo registerVo) {
        //获取值
        String code = registerVo.getCode();
        String email = registerVo.getEmail();
        String nickname = registerVo.getNickname();
        String password = registerVo.getPassword();
        //非空判断
        if (StringUtils.isEmpty(nickname)
                || StringUtils.isEmpty(code)
                || StringUtils.isEmpty(email)
                || StringUtils.isEmpty(password)) {
            throw new GuliException(20001, "注册项不能为空，注册失败");
        }
        //判断用户名是否存在
        QueryWrapper<UcenterMember> ucenterMemberQueryWrapper = new QueryWrapper<>();
        ucenterMemberQueryWrapper.eq("nickname", nickname);
        UcenterMember ucenterMember1 = baseMapper.selectOne(ucenterMemberQueryWrapper);
        if (ucenterMember1 != null) {
            throw new GuliException(20001, "用户名已存在");
        }
        //判断邮箱是否存在
        QueryWrapper<UcenterMember> ucenterMemberQueryWrapper1 = new QueryWrapper<>();
        ucenterMemberQueryWrapper1.eq("email", email);
        UcenterMember ucenterMember2 = baseMapper.selectOne(ucenterMemberQueryWrapper1);
        if (ucenterMember2 != null) {
            throw new GuliException(20001, "邮箱已注册");
        }
        //判断验证码是否正确
        String code1 = redisTemplate.opsForValue().get(email);
        if (!code.equals(code1)) {
            throw new GuliException(20001, "验证码错误");
        }
        registerVo.setPassword(MD5.encrypt(password));
        //TODO 头像
        UcenterMember ucenterMember = new UcenterMember();
        BeanUtils.copyProperties(registerVo, ucenterMember);
        baseMapper.insert(ucenterMember);
    }

    /**
     * 用户登录
     * @param ucenterMember 用户对象
     * @return token
     */
    @Override
    public String login(UcenterMember ucenterMember) {
        //获取登录邮箱和密码
        String email = ucenterMember.getEmail();
        String password = ucenterMember.getPassword();
        //非空判断
        if (StringUtils.isEmpty(email) || StringUtils.isEmpty(password)) {
            throw new GuliException(20001, "用户名或密码为空");
        }
        //判断邮箱是否正确
        QueryWrapper<UcenterMember> Wrapper = new QueryWrapper<>();
        Wrapper.eq("email", email);
        //返回邮箱对象
        UcenterMember ucenterMember1 = baseMapper.selectOne(Wrapper);
        if (StringUtils.isEmpty(ucenterMember1)) {
            throw new GuliException(20001, "用户不存在");
        }
        String password1 = ucenterMember1.getPassword();
        //数据库中的密码经过MD5加密 我们也需要把密码加密后与数据库进行比对
        if (!password1.equals(MD5.encrypt(password))) {
            throw new GuliException(20001, "密码错误");
        }
        if (ucenterMember1.getIsDisabled()) {
            throw new GuliException(20001, "用户被封禁");
        }
        if (ucenterMember1.getIsDeleted()) {
            throw new GuliException(20001, "用户已注销");
        }
        //登陆成功
        //生成token字符串 使用jwt工具类
        //注意这里传的是ucenterMember1 也就是数据库查询出的对象 而不是页面传过来的对象
        String jwtToken = JwtUtils.getJwtToken(ucenterMember1.getId(), ucenterMember1.getNickname());
        return jwtToken;
    }
}
