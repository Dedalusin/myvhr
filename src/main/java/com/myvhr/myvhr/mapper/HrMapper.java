package com.myvhr.myvhr.mapper;

import com.myvhr.myvhr.model.Hr;
import com.myvhr.myvhr.model.Role;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by sang on 2020/11/29.
 */
@Mapper
@Repository
public interface HrMapper {
    Hr loadUserByUsername(String s);

    List<Role> getRolesByHrId(Long id);

    int hrReg(String username, String password);

    List<Hr> getHrsByKeywords(String keywords);

    int updateHr(Hr hr);

    int deleteRoleByHrId(Long hrId);

    int addRolesForHr( Long hrId, Long[] rids);

    Hr getHrById(Long hrId);

    int deleteHr(Long hrId);
}
