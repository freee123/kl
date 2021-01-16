package com.sun.czjkxm.dao;

import com.github.pagehelper.Page;
import com.sun.czjkxm.pojo.Member;

import java.util.List;

public interface MemberDao {

    /**
     * 添加会员
     * @param member
     */
    void add(Member member);

    /**
     * 根据手机号找到对应的会员
     * @param telephone
     * @return
     */
    Member findByTelephone(String telephone);

    public List<Member> findAll();
    public Page<Member> selectByCondition(String queryString);
    public void deleteById(Integer id);
    public Member findById(Integer id);
    public void edit(Member member);
    public Integer findMemberCountBeforeDate(String date);
    public Integer findMemberCountByDate(String date);
    public Integer findMemberCountAfterDate(String date);
    public Integer findMemberTotalCount();
}
