package com.diodi.eduService.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.diodi.eduService.client.VodClient;
import com.diodi.eduService.entity.EduVideo;
import com.diodi.eduService.mapper.EduVideoMapper;
import com.diodi.eduService.service.EduVideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程视频 服务实现类
 * </p>
 * @author diodi
 * @since 2021-08-08
 */
@Service
public class EduVideoServiceImpl extends ServiceImpl<EduVideoMapper, EduVideo> implements EduVideoService {

    @Autowired
    VodClient vodClient;


    /**
     * 根据课程id删除小节
     * @param courseId 课程id
     */
    @Override
    public void removeVideoByCourseId(String courseId) {

        QueryWrapper<EduVideo> eduVideoQueryWrapper = new QueryWrapper<>();
        eduVideoQueryWrapper.eq("course_id", courseId);
        List<EduVideo> eduVideos = baseMapper.selectList(eduVideoQueryWrapper);
        ArrayList<String> videoSourceIds = new ArrayList<>();
        for (EduVideo eduVideo : eduVideos) {
            String videoSourceId = eduVideo.getVideoSourceId();
            if (!StringUtils.isEmpty(videoSourceId)) {
                videoSourceIds.add(videoSourceId);
            }
        }
        if (videoSourceIds.size()>0) {
            vodClient.deleteBatch(videoSourceIds);
        }
        baseMapper.delete(eduVideoQueryWrapper);
    }
}
