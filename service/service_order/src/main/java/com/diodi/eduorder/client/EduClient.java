package com.diodi.eduorder.client;

import com.diodi.commonutils.CourseWebVoOrder;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author diodi
 * @create 2021-08-23-16:58
 */
@Component
@FeignClient(value = "service-edu",fallback = EduClientImpl.class)
public interface EduClient {

    /**
     * 根据课程id查询课程信息
     * @param courseId
     * @return
     */
    @GetMapping("eduService/courseFront/getCourseOrderInfo/{courseId}")
    public CourseWebVoOrder getCourseOrderInfo(@PathVariable(required = false) String courseId);
}
