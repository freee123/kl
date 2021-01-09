package com.sun.czjkxm.dao;

import com.github.pagehelper.Page;
import com.sun.czjkxm.pojo.CheckGroup;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CheckGroupDao {

    /**
     * 添加检查组
     * @param checkGroup
     */
    void add(CheckGroup checkGroup);

    /**
     * 添加检查项与检查组之间的关系
     * @param checkgroupId
     * @param checkitemId
     */
    void addCheckGroupCheckItem(@Param("checkgroupId") Integer checkgroupId,@Param("checkitemId") Integer checkitemId);

    /**
     * 根据id在找到对应检查组拥有的检查项的id
     * @param id
     * @return
     */
    List<Integer> findCheckItemIdsByCheckGroupId(int id);

    /**
     * 分页查询
     * @param queryString
     * @return
     */
    Page<CheckGroup> findByConditeion(String queryString);

    /**
     * 更新检查组中的信息（除了检查组与检查项的关系）
     * @param checkGroup
     */
    void update(CheckGroup checkGroup);

    /**
     * 删除该检出组与其所有的检查项的关系
     * @param id
     */
    void deleteCheckGroupCheckItem(Integer id);

    /**
     * 查询检查组的信息（根据其id）
     * @param id
     * @return
     */
    CheckGroup findById(int id);
}
