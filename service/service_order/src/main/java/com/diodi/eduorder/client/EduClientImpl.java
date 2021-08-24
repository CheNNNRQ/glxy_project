package com.diodi.eduorder.client;

import com.diodi.commonutils.CourseWebVoOrder;
import org.springframework.stereotype.Component;

/**
 * @author diodi
 * @create 2021-08-23-16:58
 */
@Component
public class EduClientImpl implements EduClient {
    /**
     * 根据课程id查询课程信息
     * @param courseId
     * @return
     */
    @Override
    public CourseWebVoOrder getCourseOrderInfo(String courseId) {
        return null;
    }
}
