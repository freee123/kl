package com.sun.czjkxm.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.sun.czjkxm.dao.CheckItemDao;
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
}
