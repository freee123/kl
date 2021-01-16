package com.sun.czjkxm.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.sun.czjkxm.constant.MessageConstant;
import com.sun.czjkxm.entity.Result;
import com.sun.czjkxm.pojo.Setmeal;
import com.sun.czjkxm.service.SetmealService;
import com.sun.czjkxm.utils.QiNiuUtils;
import org.aspectj.bridge.Message;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@ResponseBody
@RequestMapping("/setmeal")
public class SetmealMobileController {
    @Reference
    private SetmealService setmealService;

    /**
     * 套餐列表
     */
    @GetMapping("/getSetmeal")
    public Result getSetmeal(){
        //调用服务查询所有套餐
        List<Setmeal> list = setmealService.findAll();
        //拼接图片的完整路径
        list.forEach(s->s.setImg(QiNiuUtils.DOMAIN+s.getImg()));//域名+图片名（随机生成的在文件服务器上的名字）
        //返回给页面
        return new Result(true, MessageConstant.QUERY_SETMEAL_SUCCESS,list);
    }

    /**
     * 通过id查询套餐详情
     */         //findDetailById
    @GetMapping("/findDetailById")
    public Result findDetailById(int id){
        //调用服务查询套餐的详细信息
        Setmeal setmeal = setmealService.findDetailById(id);
        //拼接图片的完整路径
        setmeal.setImg(QiNiuUtils.DOMAIN+setmeal.getImg());
        return new Result(true, MessageConstant.QUERY_SETMEAL_SUCCESS, setmeal);
    }

    /**
     * 根据id查询套餐信息
     */
    public Result findById(int id){
        Setmeal setmeal = setmealService.findById(id);
        setmeal.setImg(QiNiuUtils.DOMAIN+setmeal.getImg());
        return new Result(true,MessageConstant.QUERY_SETMEAL_SUCCESS,setmeal);
    }

}