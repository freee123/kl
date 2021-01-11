package com.sun.czjkxm.controller;

//import com.alibaba.dubbo.config.annotation.Reference;
//import com.sun.czjkxm.constant.MessageConstant;
//import com.sun.czjkxm.entity.Result;
//import com.sun.czjkxm.pojo.CheckGroup;
//import com.sun.czjkxm.service.CheckGroupService;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.dubbo.config.annotation.Reference;
import com.sun.czjkxm.constant.MessageConstant;
import com.sun.czjkxm.entity.PageResult;
import com.sun.czjkxm.entity.QueryPageBean;
import com.sun.czjkxm.entity.Result;
import com.sun.czjkxm.pojo.CheckGroup;
import com.sun.czjkxm.service.CheckGroupService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@ResponseBody
@RequestMapping("/checkgroup")
public class CheckGroupController {
    @Reference
    private CheckGroupService checkGroupService;

    /**
     * 添加检查组
     * @param checkitemIds
     * @return
     */
    @PostMapping("/add")
    //public Result add(@RequestBody CheckGroup checkGroup,Integer[] checkitemIds){
    public Result add(@RequestBody CheckGroup checkgroup,Integer[] checkitemIds){
        //调用服务 添加检查组
        checkGroupService.add(checkgroup,checkitemIds);
        return new Result(true, MessageConstant.ADD_CHECKGROUP_SUCCESS);
    }

//    @PostMapping("/add")
//    public Result add(@RequestBody CheckGroup checkgroup, Integer[] checkitemIds){
//        // 调用服务 添加检查组
//        checkGroupService.add(checkgroup, checkitemIds);
//        return new Result(true, MessageConstant.ADD_CHECKGROUP_SUCCESS);
//    }

    /**
     * 检查组的分页查询
     */
    @PostMapping("/findPage")
    public Result findPage(@RequestBody QueryPageBean queryPageBean){
        //调用服务 分页查询
        PageResult<CheckGroup> pageResult=checkGroupService.findPage(queryPageBean);
        return new Result(true,MessageConstant.QUERY_CHECKGROUP_SUCCESS,pageResult);
    }

    /**
     * 通过id查询检查组
     */
    @GetMapping("/findById")
    public Result findById(int id){
        CheckGroup checkGroup = checkGroupService.findById(id);
        return new Result(true, MessageConstant.QUERY_CHECKGROUP_SUCCESS, checkGroup);
    }

    /**
     * 通过检查组id查询选中的检查项id
     */
    @GetMapping("/findCheckItemIdsByCheckGroupId")
    public Result findCheckItemIdsByCheckGroupId(int id){
        //用id找到对应的检查组多拥有的检查项
        List<Integer> checkItemIds = checkGroupService.findCheckItemIdsByCheckGroupId(id);
        return new Result(true, MessageConstant.QUERY_CHECKITEM_SUCCESS, checkItemIds);
    }

    /**
     * 修改检查组
     */
//    @PostMapping("/update")
//    public Result update(@RequestBody CheckGroup checkGroup,Integer[] checkitemIds){
//        //调用服务 修改检查组
//        checkGroupService.update(checkGroup,checkitemIds);
//        return new Result(true, MessageConstant.DELETE_CHECKGROUP_SUCCESS);
//    }

    @PostMapping("/update")
    public Result update(@RequestBody CheckGroup checkgroup, Integer[] checkitemIds){
        // 调用服务 修改检查组
        checkGroupService.update(checkgroup, checkitemIds);
        return new Result(true, MessageConstant.EDIT_CHECKGROUP_SUCCESS);
    }


    /**
     * 删除检查组
     */
    @PostMapping("/deleteById")
    public Result deleteById(int id){
        //调用服务 删除检查组
        checkGroupService.delete(id);
        return new Result(true,MessageConstant.DELETE_CHECKGROUP_SUCCESS);
    }

    /**
     * 搜索所有检查组
     */
    @GetMapping("/findAll")
    public Result findAll(){
        List<CheckGroup> list = checkGroupService.findAll();
        return new Result(true,MessageConstant.QUERY_CHECKGROUP_SUCCESS,list);
    }
}
