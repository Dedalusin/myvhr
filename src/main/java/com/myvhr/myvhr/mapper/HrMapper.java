package com.myvhr.myvhr.mapper;

import com.myvhr.myvhr.model.Hr;
import com.myvhr.myvhr.model.Role;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by sang on 2020/11/29.
 */
public interface HrMapper {
    Hr loadUserByUsername(String s);

    List<Role> getRolesByHrId(Long id);

    int hrReg(@Param("username") String username, @Param("password") String password);

    List<Hr> getHrsByKeywords(@Param("keywords") String keywords);

    int updateHr(Hr hr);

    int deleteRoleByHrId(Long hrId);

    int addRolesForHr(@Param("hrId") Long hrId, @Param("rids") Long[] rids);

    Hr getHrById(Long hrId);

    int deleteHr(Long hrId);
}
