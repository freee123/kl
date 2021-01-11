package com.sun.czjkxm.service;

import com.sun.czjkxm.entity.PageResult;
import com.sun.czjkxm.entity.QueryPageBean;
import com.sun.czjkxm.pojo.Setmeal;

import java.util.List;

public interface SetmealService {
    /**
     * 分页查询
     */
    PageResult<Setmeal> findPage(QueryPageBean queryPageBean);

    /**
     * 添加套餐
     * @param setmeal
     * @param checkgroupIds
     */
    void add(Setmeal setmeal, Integer[] checkgroupIds);

    /**
     * 根据id删除对应的套餐
     * @param id
     */
    void deleteById(int id);

    /**
     * 根据id查询对应套餐的信息
     * @param id
     * @return
     */
    Setmeal findByid(int id);

    /**
     * 通过id查询对应套餐所拥有的检查组的id
     * @param id
     * @return
     */
    List<Integer> findCheckGroupIdsBySetmealId(int id);


    /**
     * 修改套餐信息
     * @param setmeal
     * @param checkgroupIds
     */
    void update(Setmeal setmeal, Integer[] checkgroupIds);

    /**
     * 查询所有数据库中使用的图片
     * @return
     */
    List<String> findImgs();
}
