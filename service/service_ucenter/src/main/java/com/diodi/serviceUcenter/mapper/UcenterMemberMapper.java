package com.diodi.serviceUcenter.mapper;

import com.diodi.serviceUcenter.entity.UcenterMember;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 会员表 Mapper 接口
 * </p>
 *
 * @author diodi
 * @since 2021-08-18
 */
public interface UcenterMemberMapper extends BaseMapper<UcenterMember> {

    Integer countRegisterSql(String day);
}
