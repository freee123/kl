package com.sun.czjkxm.service;

import com.sun.czjkxm.entity.PageResult;
import com.sun.czjkxm.entity.QueryPageBean;
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


    /**
     * 检查项分页查询
     * @param queryPageBean
     * @return
     */
    PageResult<CheckItem> findPage(QueryPageBean queryPageBean);

    CheckItem findById(int id);

    void update(CheckItem checkItem);

    void deleteById(int id);
}
