package com.sun.czjkxm.service.impl;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sun.czjkxm.dao.CheckItemDao;
import com.sun.czjkxm.entity.PageResult;
import com.sun.czjkxm.entity.QueryPageBean;
import com.sun.czjkxm.pojo.CheckItem;
import com.sun.czjkxm.service.CheckItemService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

//使用alibaba的包，发布服务 interfaceClass这顶服务的接口类
@Service(interfaceClass = CheckItemService.class)
public class CheckItemServiceImpl implements CheckItemService {
    @Autowired
    private CheckItemDao checkItemDao;

    /**
     * 查询所有检查项
     * @return
     */
    @Override
    public List<CheckItem> findAll() {
        return checkItemDao.findAll();
        //if (allCheckItem.isEmpty()) {
        //    return new Result(false, "查询失败");
        //}else {
        //    return new Result(true, "查询成功", allCheckItem);
        //}
    }

    /**
     * 添加检查项
     * @param checkItem
     */
    @Override
    public void add(CheckItem checkItem) {
        checkItemDao.add(checkItem);
    }

    /**
     * 检查项分页查询
     * @param queryPageBean
     * @return
     */
    @Override
    public PageResult<CheckItem> findPage(QueryPageBean queryPageBean) {
        //pageSize不可以过大，应该做检查、判断
        PageHelper.startPage(queryPageBean.getCurrentPage(),queryPageBean.getPageSize());
        //条件查询
        if (StringUtils.isNotEmpty(queryPageBean.getQueryString())) {
            //条件查询，like查询
            queryPageBean.setQueryString("%"+queryPageBean.getQueryString()+"%");
        }
        //page 继承 arrayList
        Page<CheckItem> page = checkItemDao.findByCondition(queryPageBean.getQueryString());
        PageResult<CheckItem> pageResult =new PageResult<CheckItem>(page.getTotal(),page.getResult());
        return pageResult;
    }

    /**
     * 通过id查询检查项
     * @param id
     * @return
     */
    @Override
    public CheckItem findById(int id) {
        return checkItemDao.findById(id);
    }

    /**
     * 修改检查项
     * @param checkItem
     */
    @Override
    public void update(CheckItem checkItem) {
        checkItemDao.update(checkItem);
    }

    /**
     * 根据id删除对应的检查项
     * @param id
     */
    @Override
    public void deleteById(int id) {
        checkItemDao.deleteById(id);
    }

}
