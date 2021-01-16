package com.sun.czjkxm.service;

import com.sun.czjkxm.pojo.Member;

public interface MemberService {

    /**
     * 添加会员
     * @param member
     */
    void add(Member member);

    /**
     * 根据手机号码查询会员
     * @param telephone
     * @return
     */
    Member findByTelephone(String telephone);
}
