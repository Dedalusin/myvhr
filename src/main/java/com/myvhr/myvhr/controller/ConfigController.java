package com.myvhr.myvhr.controller;

import com.myvhr.myvhr.common.HrUtils;
import com.myvhr.myvhr.model.Hr;
import com.myvhr.myvhr.model.Menu;
import com.myvhr.myvhr.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/config")
public class ConfigController {
    @Autowired
    MenuService menuService;
    @RequestMapping("/sysmenu")
    public List<Menu> sysmenu() {
        return menuService.getMenusByHrId();
    }

    @RequestMapping("/hr")
    public Hr currentUser() {
        return HrUtils.getCurrentHr();
    }
}