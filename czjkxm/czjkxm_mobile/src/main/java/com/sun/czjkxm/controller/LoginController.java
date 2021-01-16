package com.sun.czjkxm.controller;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.alibaba.dubbo.config.annotation.Reference;
import com.sun.czjkxm.constant.MessageConstant;
import com.sun.czjkxm.constant.RedisMessageConstant;
import com.sun.czjkxm.entity.Result;
import com.sun.czjkxm.pojo.Member;
import com.sun.czjkxm.service.MemberService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.Map;

@RestController
@RequestMapping("/login")
public class LoginController {
    private static final Logger log = LoggerFactory.getLogger(LoginController.class);

    @Reference
    private MemberService memberService;

    @Autowired
    private JedisPool jedisPool;

    @PostMapping("/check")
    public Result login(@RequestBody Map<String,String> loginInfo, HttpServletResponse res){
        //校验验证码
        //拼接出一个可以用的key,获取redis中的验证码
        String telephone = loginInfo.get("telephone");
        String key = RedisMessageConstant.SENDTYPE_LOGIN+"_"+telephone;
        //Jedis jedis = jedisPool.getResource();
        Jedis jedis = jedisPool.getResource();
        String codeInRedis = jedis.get(key);
        log.info("codeInRedis:{}",codeInRedis);

        if (StringUtils.isEmpty(codeInRedis)) {
            //没有值，要求发送验证码
            return new Result(false, "请重新获取验证码");
        }
        log.info("codeFromUI:{}",loginInfo.get("validateCode"));
        //有值
        //开始校验
        if (!codeInRedis.equals(loginInfo.get("validateCode"))) {
            //不同
            return new Result(false, "验证码不正确");
        }
        //相同，删除key,防止重复提交
        jedis.del(key);
        //通过手机号判断是否为会员
        Member member=memberService.findByTelephone(telephone);
        if (null==member) {
            //顺便注册为会员
            member = new Member();
            member.setRegTime(new Date());
            member.setPhoneNumber(telephone);
            member.setRemark("快速登录");
            memberService.add(member);
        }
        //添加cookie
        Cookie cookie = new Cookie("login_member_telephone",telephone);
        cookie.setMaxAge(30*24*60*60);//设置存活时间
        cookie.setPath("/");//所有的访问url都会携带cookie
        //将cookie给客户端
        res.addCookie(cookie);
        return new Result(true, MessageConstant.LOGIN_SUCCESS);
    }

    /**
     * 获取cookie（其中是手机号）
     * @param telephtone
     * @return
     */
    @RequestMapping("/getTelephone")
    public Result getTelephone(@CookieValue("login_member_telephone")String telephtone){
        return new Result(true, "获取手机号码成功", telephtone);
    }
}
