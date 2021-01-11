package com.sun.czjkxm.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.sun.czjkxm.constant.MessageConstant;
import com.sun.czjkxm.entity.PageResult;
import com.sun.czjkxm.entity.QueryPageBean;
import com.sun.czjkxm.entity.Result;
import com.sun.czjkxm.pojo.Setmeal;
import com.sun.czjkxm.service.SetmealService;
import com.sun.czjkxm.utils.QiNiuUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

@Controller
@ResponseBody
@RequestMapping("/setmeal")
public class SetmealController {
    @Reference
    private SetmealService setmealService;

    //输出日志
    private static Logger log = LoggerFactory.getLogger(SetmealController.class);

    /**
     * 分页查询套餐
     */
    @PostMapping("/findPage")
    public Result findPage(@RequestBody QueryPageBean queryPageBean){
        PageResult<Setmeal> setmealPageResult = setmealService.findPage(queryPageBean);
        return new Result(true, MessageConstant.QUERY_SETMEAL_SUCCESS, setmealPageResult);
    }

    /**
     * 添加套餐
     */
    @PostMapping("/add")
    public Result add(@RequestBody Setmeal setmeal,Integer[] checkgroupIds){
        //调用服务添加套餐
        setmealService.add(setmeal,checkgroupIds);
        return new Result(true, MessageConstant.ADD_SETMEAL_SUCCESS);
    }

    /**
     * 删除套餐
     */
    @PostMapping("/deleteById")
    public Result delteById(int id){
        setmealService.deleteById(id);
        return new Result(true, MessageConstant.SEND_VALIDATECODE_FAIL);
    }

    /**
     * 通过id查询对应的套餐信息
     */
    @GetMapping("/findById")
    public Result findById(int id){
        //套餐信息
        Setmeal setmeal =setmealService.findByid(id);
        //关于域名
        HashMap<String, Object> map = new HashMap<>(2);
        map.put("setmeal",setmeal);
        map.put("domain", QiNiuUtils.DOMAIN);
        return new Result(true, MessageConstant.QUERY_SETMEAL_SUCCESS, map);
    }
    /**
     * 通过id查询对应套餐拥有的检查组的id
     */
    @GetMapping("/findCheckGroupIdsBySetmealId")
    public Result findCheckGroupIdsBySetmealId(int id){
        List<Integer> checkgroupIds = setmealService.findCheckGroupIdsBySetmealId(id);
        return new Result(true, MessageConstant.QUERY_SETMEAL_SUCCESS, checkgroupIds);
    }

    /**
     * 修改套餐的信息
     */
    @PostMapping("/update")
    public Result update(@RequestBody Setmeal setmeal,Integer[] checkgroupIds){
        setmealService.update(setmeal,checkgroupIds);
        return new Result(true, "成功修改套餐");
    }

    @PostMapping("/upload")
    public Result upload(MultipartFile imgFile){
        //1.获取源文件名
        String originalFilename = imgFile.getOriginalFilename();
        //2.截取后缀名
        String substring = originalFilename.substring(originalFilename.lastIndexOf("."));
        //3.生成唯一id
        String uniqueId = UUID.randomUUID().toString();
        //4.拼接唯一文件名
        String filename = uniqueId + substring;
        //5.调用七牛工具上传
        try{
            QiNiuUtils.uploadViaByte(imgFile.getBytes(),filename);
            //6.构建返回的数据
            /*
                imgName:图片名（补全forData.img）
                domain:七牛云的域名（文件系统的url:domain+图片名）
             */
            HashMap<String, String> map = new HashMap<>(2);
            map.put("imgName",filename);
            map.put("domain",QiNiuUtils.DOMAIN);
            //7.放到result里，再返回给页面
            return new Result(true, MessageConstant.PIC_UPLOAD_SUCCESS, map);
        }catch (Exception e){
            log.error("文件上传失败",e);
            return new Result(false, MessageConstant.PIC_UPLOAD_FAIL);
        }
    }
}
