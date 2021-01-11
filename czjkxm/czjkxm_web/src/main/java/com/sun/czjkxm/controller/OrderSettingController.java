package com.sun.czjkxm.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.sun.czjkxm.constant.MessageConstant;
import com.sun.czjkxm.entity.Result;
import com.sun.czjkxm.pojo.OrderSetting;
import com.sun.czjkxm.service.OrderSettingService;
import com.sun.czjkxm.utils.POIUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
//import sun.rmi.runtime.Log;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@ResponseBody
@RequestMapping("/ordersetting")
public class OrderSettingController {
    //打印日志
    //为什么要加final
    private static final Logger log= LoggerFactory.getLogger(SetmealController.class);
  //private static final Logger log = LoggerFactory.getLogger(SetmealController.class);
    @Reference
    private OrderSettingService orderSettingService;

    /**
     * 批量导入预约设置
     */
    @PostMapping("/upload")
    public Result upload(MultipartFile excelFile){
        //解析excel文件，调用POIUItls解析文件，得到List<String[]>
        //每一个数组就是一条记录（一行）

        try {
            List<String[]> excelData = POIUtils.readExcel(excelFile);
            log.debug("导入excle记录{}条",excelData.size());
            //转成List<Ordersetting,再调用service方法做导入，返回给页面
            //String[]数组 有两个信息 0：日期，1：数量
            //为什么要加final
            //用来解析日期信息
            final SimpleDateFormat simpleDateFormat = new SimpleDateFormat(POIUtils.DATE_FORMAT);
            List<OrderSetting> osList = excelData.stream().map(arr -> {
                OrderSetting orderSetting = new OrderSetting();
                try {
                    orderSetting.setOrderDate(simpleDateFormat.parse(arr[0]));
                    orderSetting.setNumber(Integer.valueOf(arr[1]));
                } catch (ParseException e) {
                }
                return orderSetting;
            }).collect(Collectors.toList());
            //调用服务导入
            orderSettingService.addBatch(osList);
            return new Result(true, MessageConstant.IMPORT_ORDERSETTING_SUCCESS);
        } catch (IOException e) {
            log.error("导入预约设置失败",e);
            //e.printStackTrace();
            return new Result(false, MessageConstant.IMPORT_ORDERSETTING_FAIL);
        } catch (Exception e){
            log.error("导入预约设置失败",e);
            return new Result(false, "人数问题");
        }
    }

    /**
     * 通过月份查询预约信息
     */
    @GetMapping("/getOrderSettingByMonth")
    public Result getOrderSettingByMonth(String month){
        //按月查询预约设置信息
        List<Map<String,Integer>> data = orderSettingService.getOrderSettingByMonth(month);
        return new Result(true, MessageConstant.GET_ORDERSETTING_SUCCESS, data);
    }
}
