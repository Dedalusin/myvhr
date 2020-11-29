package com.myvhr.myvhr.mapper;

import com.myvhr.myvhr.model.Menu;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * Created by sang on 2020/11/29.
 */
@Mapper
public interface MenuMapper {
    List<Menu> getAllMenu();

    List<Menu> getMenusByHrId(Long hrId);

    List<Menu> menuTree();

    List<Long> getMenusByRid(Long rid);
}
