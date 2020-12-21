package com.myvhr.myvhr.service;

import com.myvhr.myvhr.mapper.MenuRoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class MenuRoleService {
    @Autowired
    MenuRoleMapper menuRoleMapper;

    public int updateMenuRole(Long rid, Long[] mids){
        menuRoleMapper.deleteMenuByRid(rid);
        if(mids.length == 0)return 0;
        return menuRoleMapper.addMenu(rid, mids);
    }
}
