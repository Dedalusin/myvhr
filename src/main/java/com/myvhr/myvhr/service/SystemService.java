package com.myvhr.myvhr.service;

import com.myvhr.myvhr.mapper.SystemMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class SystemService {
    @Autowired
    SystemMapper systemMapper;
}
