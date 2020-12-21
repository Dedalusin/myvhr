package com.myvhr.myvhr.service;

import com.myvhr.myvhr.mapper.RoleMapper;
import com.myvhr.myvhr.model.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class RoleService {
    @Autowired
    RoleMapper roleMapper;

    public List<Role> getRoles(){
        return roleMapper.getRoles();
    }

    public int addNewRole(String role, String roleZh){
        if(!role.startsWith("ROLE_")){
            role += "ROLE_";
        }
        return roleMapper.addNewRole(role, roleZh);
    }

    public int deleteRoleById(Long rid) {
        return roleMapper.deleteRoleById(rid);
    }

}
