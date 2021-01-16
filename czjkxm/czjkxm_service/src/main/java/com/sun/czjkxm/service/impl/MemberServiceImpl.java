package com.sun.czjkxm.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.sun.czjkxm.dao.MemberDao;
import com.sun.czjkxm.pojo.Member;
import com.sun.czjkxm.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;

@Service(interfaceClass = MemberService.class)
public class MemberServiceImpl implements MemberService {
    @Autowired
    MemberDao memberDao;

    /**
     *  添加会员
     * @param member
     */
    @Override
    public void add(Member member) {
        memberDao.add(member);
    }

    /**
     * 根据手机号判断是否有此会员
     * @param telephone
     * @return
     */
    @Override
    public Member findByTelephone(String telephone) {
        return memberDao.findByTelephone(telephone);
    }
}
