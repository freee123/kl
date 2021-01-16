package com.sun.czjkxm.controller;

import com.aliyuncs.exceptions.ClientException;
import com.sun.czjkxm.constant.MessageConstant;
import com.sun.czjkxm.constant.RedisMessageConstant;
import com.sun.czjkxm.entity.Result;
import com.sun.czjkxm.utils.SMSUtils;
import com.sun.czjkxm.utils.ValidateCodeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@RestController
@RequestMapping("/validateCode")
public class ValidateCodeController {
    private static final Logger log = LoggerFactory.getLogger(ValidateCodeController.class);

    @Autowired
    private JedisPool jedisPool;

    /**
     * 体检预约验证码
     */
    @PostMapping("/send4Order")
    public Result send4Order(String telephone){
        Jedis jedis = jedisPool.getResource();
        //判断redis中是否有验证码
        String key = RedisMessageConstant.SENDTYPE_ORDER+"_"+ telephone;
        if (jedis.exists(key)) {
            //存在则提示已经发送了
            return new Result(false,"验证码已发送");
        }
        //不存在
        //生成用于发送的验证码
//        Integer validateCode = ValidateCodeUtils.generateValidateCode(6);
        Integer validateCode = 666;

        log.debug("生成的验证码：{}",validateCode);

//        //调用SMSUtils发送验证码
//        try {
//            SMSUtils.sendShortMessage(SMSUtils.VALIDATE_CODE,telephone,validateCode+"");
//        } catch (ClientException e) {
//            //e.printStackTrace();
//            log.error("发送验证码失败：{}：{}",telephone,validateCode);
//            return new Result(false, MessageConstant.SEND_VALIDATECODE_FAIL);
//        }

        //存入redis,设置有效期
        jedis.setex(key,10*60,validateCode+"");
        jedis.close();
        return new Result(true,MessageConstant.SEND_VALIDATECODE_SUCCESS);
    }

    //登录验证码
    @PostMapping("/send4Login")
    public Result send4Login(String telephone){
        Jedis jedis = jedisPool.getResource();
        //判断redis中是否存在验证码
        String key = RedisMessageConstant.SENDTYPE_LOGIN+"_"+ telephone;
        if (jedis.exists(key)) {
            //存在则提示已经发送了，请注意查收
            new Result(false,"验证码已经发送");
        }
        //不存在
        //生成验证码
//        Integer validateCode = ValidateCodeUtils.generateValidateCode(6);
        Integer validateCode = 666;
        log.debug("生成的验证码：{}",validateCode);

//        //调用SMSUtils发送验证码
//        try {
//            SMSUtils.sendShortMessage(SMSUtils.VALIDATE_CODE,telephone,validateCode+"");
//        } catch (ClientException e) {
//            //e.printStackTrace();
//            log.error("发送验证码失败：{}：{}",telephone,validateCode);
//            return new Result(false, MessageConstant.SEND_VALIDATECODE_FAIL);
//        }

        //存入redis,设置有效期
        jedis.setex(key,10*60,validateCode+"");
        jedis.close();
        return new Result(true,MessageConstant.SEND_VALIDATECODE_SUCCESS);
    }
}
