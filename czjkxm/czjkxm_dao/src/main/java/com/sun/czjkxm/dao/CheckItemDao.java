package com.sun.czjkxm.dao;

import com.github.pagehelper.Page;
import com.sun.czjkxm.pojo.CheckItem;

import java.util.List;

public interface CheckItemDao {
    /**
     * 查询所有检查项
     * @return
     */
    public List<CheckItem> findAll();

    /**
     * 添加检查项
     * @param checkItem
     */
    void add(CheckItem checkItem);

    /**
     * 分页查询检查项
     * @param queryString
     * @return
     */
    Page<CheckItem> findByCondition(String queryString);


    /**
     * 根据id找到对应的检查项
     * @param id
     * @return
     */
    CheckItem findById(int id);

    void update(CheckItem checkItem);

    void deleteById(int id);
}
