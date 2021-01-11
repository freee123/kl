package com.sun.czjkxm.service.impl;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sun.czjkxm.dao.SetMealDao;
import com.sun.czjkxm.entity.PageResult;
import com.sun.czjkxm.entity.QueryPageBean;
import com.sun.czjkxm.pojo.Setmeal;
import com.sun.czjkxm.service.SetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

//事务有关
@Service(interfaceClass = SetmealService.class)
public class SetmealServiceImpl implements SetmealService {
    @Autowired
    private SetMealDao setMealDao;
    /**
     * 分页查询
     * @param queryPageBean
     * @return
     */
    @Override
    public PageResult<Setmeal> findPage(QueryPageBean queryPageBean) {
        PageHelper.startPage(queryPageBean.getCurrentPage(),queryPageBean.getPageSize());

        if (StringUtils.isNotEmpty(queryPageBean.getQueryString())) {
            //模糊查询
            queryPageBean.setQueryString("%"+queryPageBean.getQueryString()+"%");
        }
        Page<Setmeal> setmealPage =setMealDao.findByCondition(queryPageBean.getQueryString());
        return new PageResult<Setmeal>(setmealPage.getTotal(),setmealPage.getResult());

    }

    /**
     * 添加套餐
     * @param setmeal
     * @param checkgroupIds
     */
    @Override
    @Transactional
    public void add(Setmeal setmeal, Integer[] checkgroupIds) {
        //添加套餐
        setMealDao.add(setmeal);
        //获取套餐id
        Integer setmealId = setmeal.getId();
        //添加套餐与检查组的关系
        if (null!=checkgroupIds) {
            for (Integer checkgroupId : checkgroupIds) {
                setMealDao.addSetMealCheckGroup(setmealId,checkgroupId);
            }
        }
    }

    /**
     * 根据id删除对应的套餐
     * @param id
     */
    @Override
    @Transactional
    public void deleteById(int id) {
        //删除套餐与其检查组的关系
        setMealDao.deleteSetmealCheckGroup(id);
        //删除套餐
        setMealDao.deletById(id);
        //存在事务控制
    }

    /**
     * 根据id获取套餐信息
     * @param id
     * @return
     */
    @Override
    public Setmeal findByid(int id) {
        Setmeal setmeal = setMealDao.findById(id);
        return setmeal;
    }

    /**
     * 根据id找到对应的套餐所拥有的检查组的id
     * @param id
     * @return
     */
    @Override
    public List<Integer> findCheckGroupIdsBySetmealId(int id) {
        List<Integer> list = setMealDao.findCheckGroupIdsBySetmealId(id);
        return list;
    }

    /**
     * 更新套餐数据
     * @param setmeal
     * @param checkgroupIds
     */
    @Override
    //添加事务
    @Transactional
    public void update(Setmeal setmeal, Integer[] checkgroupIds) {
        //更新套餐
        setMealDao.update(setmeal);
        //删除旧的关系
        setMealDao.deleteSetmealCheckGroup(setmeal.getId());
        //遍历添加 新关系
        if (null!=checkgroupIds) {
            for (Integer checkgroupId : checkgroupIds) {
                setMealDao.addSetMealCheckGroup(setmeal.getId(),checkgroupId);
            }
        }
        //有事务控制
    }

    /**
     * 查询所有数据库中使用的图片
     * @return
     */
    @Override
    public List<String> findImgs() {
        return setMealDao.findImgs();
    }
}