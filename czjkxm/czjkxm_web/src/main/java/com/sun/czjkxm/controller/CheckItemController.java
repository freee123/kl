package com.sun.czjkxm.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.sun.czjkxm.constant.MessageConstant;
import com.sun.czjkxm.entity.PageResult;
import com.sun.czjkxm.entity.QueryPageBean;
import com.sun.czjkxm.entity.Result;
import com.sun.czjkxm.pojo.CheckItem;
import com.sun.czjkxm.service.CheckItemService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//@RequestBody这是什么意思？只接受一个请求体吗？
@Controller
@ResponseBody
@RequestMapping("/checkitem")
//@RestController
//@RequestMapping("/checkitem")
public class CheckItemController {
    @Reference
    private CheckItemService checkItemService;


    /**
     * 查询所有检查项
     * @return
     */
    @RequestMapping("/findAll")
    public Result findAll(){
        List<CheckItem> all = checkItemService.findAll();

        return new Result(true, MessageConstant.QUERY_CHECKITEM_SUCCESS,all);
    }

    /**
     * 添加检查项
     */
    @PostMapping("/add")
    public Result add(@RequestBody CheckItem checkItem){
        //调用服务添加
        checkItemService.add(checkItem);
        //返回操作的结果
        return new Result(true, MessageConstant.ADD_CHECKITEM_SUCCESS);
    }

    /**
     * 检查项的分页查询
     */
    @PostMapping("/findPage")
    public Result findPage(@RequestBody QueryPageBean queryPageBean){
        //调用服务 分页查询
        PageResult<CheckItem> pageResult =checkItemService.findPage(queryPageBean);
        return new Result(true, MessageConstant.QUERY_CHECKITEM_SUCCESS, pageResult);
    }

    /**
     * 通过id查询对应的检查项
     */
    @GetMapping("/findById")
    public Result findById(int id){
        CheckItem checkItem = checkItemService.findById(id);
        return new Result(true, MessageConstant.QUERY_CHECKITEM_SUCCESS, checkItem);
    }

    /**
     * 修改检查项
     */
    @PostMapping("/update")
    public Result update(@RequestBody CheckItem checkItem){
        //调用服务更新
        checkItemService.update(checkItem);
        //返回操作的结果
        return new Result(true, MessageConstant.EDIT_CHECKITEM_SUCCESS);
    }

    /**
     * 根据id删除对应的检查项
     */
    @PostMapping("/deleteById")
    public Result deleteById(int id){
        checkItemService.deleteById(id);
        return new Result(true, MessageConstant.DELETE_CHECKITEM_SUCCESS);
    }
}
