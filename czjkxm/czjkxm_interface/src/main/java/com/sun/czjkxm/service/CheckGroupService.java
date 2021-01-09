package com.sun.czjkxm.service;

import com.sun.czjkxm.entity.PageResult;
import com.sun.czjkxm.entity.QueryPageBean;
import com.sun.czjkxm.pojo.CheckGroup;

import java.util.List;

public interface CheckGroupService {
    /**
     * 添加检查组
     */
    void add(CheckGroup checkGroup,Integer[] checkitemIds);

    /**
     * 分页查询
     */
    PageResult<CheckGroup> findPage(QueryPageBean queryPageBean);

    /**
     * 通过id查询检查组
     */
    CheckGroup findById(int id);

    /**
     * 通过检查组id,找到对应的检查项id
     */
    List<Integer> findCheckItemIdsByCheckGroupId(int id);

    /**
     * 修改检查组
     */
    void update(CheckGroup checkGroup,Integer[] checkitemIds);
}
