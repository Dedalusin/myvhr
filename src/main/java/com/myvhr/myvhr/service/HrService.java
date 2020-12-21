package com.myvhr.myvhr.service;

import com.myvhr.myvhr.mapper.HrMapper;
import com.myvhr.myvhr.model.Hr;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class HrService implements UserDetailsService {

    @Autowired
    HrMapper hrMapper;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Hr hr = hrMapper.loadUserByUsername(s);
        if(hr == null){
            throw new UsernameNotFoundException("用户名错误");
        }
        return hr;
    }
    public int hrReg(String username, String password){
        if(loadUserByUsername(username) != null ){
            return -1;
        }
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String encodedPwd = encoder.encode(password);
        return hrMapper.hrReg(username, encodedPwd);
    }
    public List<Hr> getHrsByKeywords(String keywords) {
        return hrMapper.getHrsByKeywords(keywords);
    }

    public int updateHr(Hr hr) {
        return hrMapper.updateHr(hr);
    }

    public int updateHrRoles(Long hrId, Long[] rids) {
        hrMapper.deleteRoleByHrId(hrId);
        return hrMapper.addRolesForHr(hrId, rids);
    }

    public Hr getHrById(Long hrId) {
        return hrMapper.getHrById(hrId);
    }

    public int deleteHr(Long hrId) {
        return hrMapper.deleteHr(hrId);
    }
}
