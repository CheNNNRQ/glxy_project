package com.diodi.eduService.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.diodi.commonutils.JwtUtils;
import com.diodi.commonutils.R;
import com.diodi.commonutils.UcenterMemberVo;
import com.diodi.eduService.client.UcenterClient;
import com.diodi.eduService.entity.EduComment;
import com.diodi.eduService.service.EduCommentService;
import com.diodi.servicebase.exceptionhandler.GuliException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 课程评论
 * </p>
 * @author diodi
 * @since 2021-08-22
 */
@Api(tags = "课程评论")
@RestController
@RequestMapping("/eduService/comment")
public class EduCommentController {
    @Autowired
    EduCommentService commentService;

    @Autowired
    UcenterClient ucenterClient;
    /**
     * 分页查询课程评论
     * @param page  当前页
     * @param limit 每页记录数
     * @return 课程评论
     */
    @GetMapping("getCommentPage/{page}/{limit}")
    public R getCommentPage(@PathVariable Long page,
                            @PathVariable Long limit,
                            String courseId) {
        Page<EduComment> commentPage = new Page<>(page, limit);
        QueryWrapper<EduComment> queryWrapper = new QueryWrapper<>();
        System.out.println("*******************************************" + courseId);
        //判断课程id是否为空
        if (!StringUtils.isEmpty(courseId)) {
            queryWrapper.eq("course_id", courseId);
        }
        queryWrapper.orderByDesc("gmt_create");
        commentService.page(commentPage, queryWrapper);
        return R.ok().data("commentInfo", commentPage);
    }

    /**
     * 添加评论
     * @param comment 前端传来的评论对象 包含课程id 讲师id 评论内容
     * @param request 内含token
     * @return
     */
    @ApiOperation("添加评论")
    @PostMapping("addComment")
    public R addComment(@RequestBody EduComment comment,
                        HttpServletRequest request) {
        //工具类的方法 传入前台的HttpServletRequest对象从中获取id值
        String id = JwtUtils.getMemberIdByJwtToken(request);
        //判断是否登录
        if (StringUtils.isEmpty(id)) {
            throw new GuliException(20001, "用户未登录");
        }
        //远程调用 返回用户信息
        UcenterMemberVo ucenterMemberVo = ucenterClient.getMemberInfoById(id);
        String avatar = ucenterMemberVo.getAvatar();
        String nickname = ucenterMemberVo.getNickname();
        //将用户id 头像 用户名set到comment中
        comment.setNickname(nickname);
        comment.setMemberId(id);
        comment.setAvatar(avatar);
        //执行添加方法
        boolean save = commentService.save(comment);
        if (save){
        return R.ok();
        }else {
            return R.error();
        }
    }
}

