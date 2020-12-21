package com.myvhr.myvhr.mapper;

import com.myvhr.myvhr.model.Role;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by sang on 2020/11/29.
 */
@Mapper
@Repository
public interface RoleMapper {
    List<Role> getRoles();

    int addNewRole(@Param("role") String role, @Param("roleZh") String roleZh);

    int deleteRoleById(Long rid);
}
