package com.myvhr.myvhr.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * Created by sang on 2020/11/29.
 */
@Mapper
@Repository
public interface MenuRoleMapper {
    int deleteMenuByRid(@Param("rid") Long rid);

    int addMenu(@Param("rid") Long rid, @Param("mids") Long[] mids);
}
