package com.sun.czjkxm.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.sun.czjkxm.constant.MessageConstant;
import com.sun.czjkxm.entity.PageResult;
import com.sun.czjkxm.entity.QueryPageBean;
import com.sun.czjkxm.entity.Result;
import com.sun.czjkxm.pojo.CheckItem;
import com.sun.czjkxm.service.CheckItemService;
import org.apache.ibatis.mapping.ResultMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//@RequestBody这是什么意思？只接受一个请求体吗？
@Controller
@ResponseBody
@RequestMapping("/checkItem")
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
}
