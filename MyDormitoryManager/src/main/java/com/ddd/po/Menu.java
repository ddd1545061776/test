package com.ddd.po;

import lombok.Data;

import java.util.List;

/**
 * @author 邓冬冬
 * @date 2021/8/14
 */
@Data
public class Menu {
    private Integer id;

    private String menuName;
    private String url;
    private Integer parentId;
    private List<Menu> children;
}
