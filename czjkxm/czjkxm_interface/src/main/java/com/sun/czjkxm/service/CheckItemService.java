package com.sun.czjkxm.service;

import com.sun.czjkxm.pojo.CheckItem;

import java.util.List;

public interface CheckItemService {
    /**
     * 查询所有检查项
     * @return
     */
    public List<CheckItem> findAll();

    /**
     * 添加检查项
     * @param checkItem
     */
    public void add(CheckItem checkItem);
}
