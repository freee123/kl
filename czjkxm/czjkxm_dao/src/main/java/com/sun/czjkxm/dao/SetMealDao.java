package com.sun.czjkxm.dao;

import com.github.pagehelper.Page;
import com.sun.czjkxm.pojo.Setmeal;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SetMealDao {
    /**
     * 有条件查询
     * @param
     * @return
     */
    Page<Setmeal> findByCondition(String s);

    /**
     * 添加套餐项
     * @param setmeal
     */
    void add(Setmeal setmeal);

    /**
     * 添加套餐项和的检查组的关系
     * @param setmealId
     * @param checkgroupId
     */
    void addSetMealCheckGroup(@Param("setmealId") Integer setmealId,@Param("checkgroupId") Integer checkgroupId);

    /**
     * 删除套餐
     * @param id
     */
    void deletById(int id);

    /**
     * 删除套餐和检查组的关系
     * @param id
     */
    void deleteSetmealCheckGroup(int id);

    /**
     * 根据id获取套餐信息
     * @return
     */
    Setmeal findById(int id);

    /**
     * 根据id找到对应的套餐所拥有的检查组的id
     * @param id
     * @return
     */
    List<Integer> findCheckGroupIdsBySetmealId(int id);

    /**
     * 更新检查组
     * @param setmeal
     */
    void update(Setmeal setmeal);

    /**
     * 查询所有数据库中使用的图片
     * @return
     */
    List<String> findImgs();
}
