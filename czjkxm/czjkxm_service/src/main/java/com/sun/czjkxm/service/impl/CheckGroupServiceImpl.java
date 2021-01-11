package com.sun.czjkxm.service.impl;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sun.czjkxm.dao.CheckGroupDao;
import com.sun.czjkxm.entity.PageResult;
import com.sun.czjkxm.entity.QueryPageBean;
import com.sun.czjkxm.pojo.CheckGroup;
import com.sun.czjkxm.service.CheckGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service(interfaceClass = CheckGroupService.class)
public class CheckGroupServiceImpl implements CheckGroupService {
    @Autowired
    private CheckGroupDao checkGroupDao;

    /**
     * 添加检查组
     * @param checkGroup
     * @param checkitemIds
     */

    @Override
    @Transactional//添加事务控制
    public void add(CheckGroup checkGroup, Integer[] checkitemIds) {
        //添加检查组
        checkGroupDao.add(checkGroup);
        //获取检查组的id
        Integer checkgroupId=checkGroup.getId();
        //遍历选中的检查项id的数组
        if (null!=checkitemIds) {
            for (Integer checkitemId : checkitemIds) {
                //添加检查项与检查组的关系
                checkGroupDao.addCheckGroupCheckItem(checkgroupId,checkitemId);
            }
        }
    }

    /**
     * 分页查询
     * @param queryPageBean
     * @return
     */
    @Override
    public PageResult<CheckGroup> findPage(QueryPageBean queryPageBean) {
        PageHelper.startPage(queryPageBean.getCurrentPage(),queryPageBean.getPageSize());
        //条件查询
        if (StringUtils.isNotEmpty(queryPageBean.getQueryString())) {
            //有查询条件，模糊查询
            queryPageBean.setQueryString("%"+queryPageBean.getQueryString()+"%");
        }
        //page 继承于 arrayList
        Page<CheckGroup> page = checkGroupDao.findByConditeion(queryPageBean.getQueryString());
        PageResult<CheckGroup> pageResult = new PageResult<CheckGroup>(page.getTotal(),page.getResult());
        return pageResult;
    }

    /**
     * 根据id找到对应的检查组（编辑的回显）
     * @param id
     * @return
     */
    @Override
    public CheckGroup findById(int id) {
        return checkGroupDao.findById(id);
    }

    /**
     * 根据id找到对应的检查组所拥有的检查项
     * @param id
     * @return
     */
    @Override
    public List<Integer> findCheckItemIdsByCheckGroupId(int id) {
        return checkGroupDao.findCheckItemIdsByCheckGroupId(id);
    }

    /**
     * 修改检查项的内容
     * @param checkGroup
     * @param checkitemIds
     */
    @Override
    @Transactional
    public void update(CheckGroup checkGroup, Integer[] checkitemIds) {
        //更新检查组
        checkGroupDao.update(checkGroup);
        //删除检查组和检查项的关系
        checkGroupDao.deleteCheckGroupCheckItem(checkGroup.getId());
        //添加新的检查组和检查项的关系
        if (null!=checkitemIds) {
            for (Integer checkitemId : checkitemIds) {
                //添加检查与检查项的关系
                checkGroupDao.addCheckGroupCheckItem(checkGroup.getId(),checkitemId);
            }
        }
    }

    /**
     * 删除检查组
     * @param id
     */
    @Override
    @Transactional//事务控制
    public void delete(int id) {
        //删除检查组与检查项之间的关系
        checkGroupDao.deleteCheckGroupCheckItem(id);
        //删除检查组
        checkGroupDao.delete(id);
    }

    /**
     * 搜索所有检查组
     * @return
     */
    @Override
    public List<CheckGroup> findAll() {
        return checkGroupDao.findAll();
    }
}
