package com.sun.czjkxm.jobs;

import com.alibaba.dubbo.config.annotation.Reference;
import com.sun.czjkxm.service.SetmealService;
import com.sun.czjkxm.utils.QiNiuUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CleanImgJob {
    //打印日志
    private static final Logger log = LoggerFactory.getLogger(CleanImgJob.class);
    @Reference
    private SetmealService setmealService;

    /**
     * 清理垃圾图片（数据库上不存在的七牛云上的图片）
     */
    @Scheduled(initialDelay = 3000,fixedDelay = 1800000)//3s后开始，每半个小时执行一次
    //@Scheduled(cron = "0 0 4 * * ?")每天4点执行一次
    public void cleanImg(){
        log.info("定时任务开始：清理垃圾图片");
        //1.调用QiNiuUtils查询所有图片
        List<String> imgIn7Niu = QiNiuUtils.listFile();
        //{}点为符
        log.debug("文件服务器上有{}张图片",imgIn7Niu.size());
        //2.调用服务器查询数据库上有多少张图片
        List<String> imgInDb = setmealService.findImgs();
        log.debug("数据库上有{}张图片",imgInDb==null?0:imgInDb.size());
        //3.找到要删除的图片
        imgIn7Niu.removeAll(imgInDb);
        if (imgIn7Niu.size()>0) {
            log.debug("要清理的垃圾图片有{}张",imgIn7Niu.size());
            //4.调用七牛的api删除图片
            QiNiuUtils.removeFiles(imgIn7Niu.toArray(new String[]{}));
        }else{
            log.debug("没有需要清理的图片");
        }
        log.info("清理垃圾图片完成");
    }
}
