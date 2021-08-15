package com.ddd.service;

import com.ddd.po.Menu;

import java.util.List;

/**
 * @author 邓冬冬
 * @date 2021/8/14
 */
public interface MenuService {
    public List<Menu> findMenusByUsername(String username);
}
