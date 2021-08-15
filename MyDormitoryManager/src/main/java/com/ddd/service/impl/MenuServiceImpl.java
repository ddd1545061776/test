package com.ddd.service.impl;

import com.ddd.dao.MenuDao;
import com.ddd.po.Menu;
import com.ddd.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 邓冬冬
 * @date 2021/8/14
 */
@Service
public class MenuServiceImpl implements MenuService {
    @Autowired
    MenuDao menuDao;
    @Override
    public List<Menu> findMenusByUsername(String username) {
        return menuDao.findMenusByUsername(username);
    }
}
