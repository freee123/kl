package com.sun.czjkxm.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.sun.czjkxm.constant.MessageConstant;
import com.sun.czjkxm.constant.RedisMessageConstant;
import com.sun.czjkxm.entity.Result;
import com.sun.czjkxm.pojo.Order;
import com.sun.czjkxm.service.OrderService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.Map;

@RestController
@RequestMapping("/order")
public class OrderController {
    private static final Logger log = LoggerFactory.getLogger(OrderController.class);

    @Autowired
    private JedisPool jedisPool;

    @Reference
    private OrderService orderService;

    /**
     * 提交申请
     */
    @PostMapping("/submit")
    public Result submit(@RequestBody Map<String,String> orderInfo) throws Exception {
        //校验验证码
        //拼接redis的key,获取redis中的验证码
        String telephone = orderInfo.get("telephone");
        String key = RedisMessageConstant.SENDTYPE_ORDER+"_"+ telephone;
        Jedis jedis = jedisPool.getResource();
        String codeInRedis = jedis.get(key);
        log.info("codeInRedis:{}",codeInRedis);
        //有值
        if (StringUtils.isEmpty(codeInRedis)) {
            //空，要求重新获取验证码
            return new Result(false,"请重新获取验证码");
        }
        log.info("codeFromUI:{}",orderInfo.get("validateCode"));
        if (!codeInRedis.equals(orderInfo.get("validateCode"))) {
            //不相同，提交验证码错误
            return new Result(false, "验证码不正确");
        }
        //相同，删除key,则可以提交订单
        jedis.del(key);//防止重复提交
        //设置预约的类型，health_mobile,微信预约
        orderInfo.put("ordrType", Order.ORDERTYPE_WEIXIN);
        //调用预约服务
        Order order = orderService.submitOrder(orderInfo);
        //返回结果给页面，返回订单信息
        return new Result(true, MessageConstant.ORDER_SUCCESS, order);
    }

    /**
     * 查询预约成功订单信息
     */
    @GetMapping("/findById")
    public Result findById(int id){
        Map<String,String> orderInfo = orderService.findDetailById(id);
        return new Result(true, MessageConstant.QUERY_ORDER_SUCCESS, orderInfo);
    }
}
