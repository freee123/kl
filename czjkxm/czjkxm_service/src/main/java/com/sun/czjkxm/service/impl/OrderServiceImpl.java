package com.sun.czjkxm.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.sun.czjkxm.dao.MemberDao;
import com.sun.czjkxm.dao.OrderDao;
import com.sun.czjkxm.dao.OrderSettingDao;
import com.sun.czjkxm.pojo.Member;
import com.sun.czjkxm.pojo.Order;
import com.sun.czjkxm.pojo.OrderSetting;
import com.sun.czjkxm.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service(interfaceClass = OrderService.class)
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderDao orderDao;

    @Autowired
    MemberDao memberDao;

    @Autowired
    OrderSettingDao orderSettingDao;

    /**
     *  预约套餐
     * @param orderInfo
     * @return
     */
    @Override
    @Transactional
    public Order submitOrder(Map<String, String> orderInfo) throws Exception {
        //1.根据体检日期查询预约设置
        String orderDateStr = orderInfo.get("orderDate");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date orderDate = null;
        try{
            orderDate = sdf.parse(orderDateStr);
        }catch(ParseException e){
            throw new Exception("日期格式不真确");
        }

        //通过预约日期查询设置
        OrderSetting osInDB = orderSettingDao.findByOrderDate(orderDate);
        if (null!=osInDB) {
            //有设置
                //判断预约是否已满
            int reservations = osInDB.getReservations();//已预约人数
            int number =osInDB.getNumber();
            if (reservations>=number) {
                //预约已满
                throw new Exception("这天已预约满了");
            }
        }else {
            throw new Exception("未设置不可选");
        }
        //2判断是否为会员操作
        String telephone = orderInfo.get("telephone");
        //手机号码查询是否存在
        Member member = memberDao.findByTelephone(telephone);

        //构建订单信息
        Order order = new Order();
        String setmealId = orderInfo.get("setmealId");

        //订单的套餐id
        order.setSetmealId(Integer.valueOf(setmealId));
        //订单预约日期//orderDate:前端
        order.setOrderDate(orderDate);
        if (null == member){
            //不存在
            member = new Member();
            //添加会员
            member.setPhoneNumber(telephone);
            member.setRegTime(new Date());
            member.setRemark("微信预约自动注册");
            member.setName(orderInfo.get("name"));
            member.setSex(orderInfo.get("sex"));
            member.setIdCard(orderInfo.get("idCard"));
            member.setPassword(telephone.substring(5));//设置默认密码
            memberDao.add(member);
            //member_id 查询、添加可以获取
            order.setMemberId(member.getId());
        }else{
            //存在
            //判断是否重复预约
            //通过套餐id,会员id,预约日期
            Integer memberId = member.getId();
            order.setMemberId(memberId);
            List<Order> orderList = orderDao.findByCondition(order);
            //存在：报错：不能重复预约
            if (!CollectionUtils.isEmpty(orderList)) {
                throw new Exception("不能重复预约");
            }
        }
        //更新已预约人数，防止超卖，行锁，更新成功返回1，失败返回0
        int count = orderSettingDao.editReservationsByOrderDate(osInDB);
        if (count == 0) {
            throw new Exception("人已满");
        }

        //订单表操作，添加预约
        order.setOrderType(orderInfo.get("orderType"));
        order.setOrderStatus(Order.ORDERSTATUS_NO);
        orderDao.add(order);
        return order;
    }

    /**
     * 查询预约成功的订单信息
     * @param id
     * @return
     */
    @Override
    public Map<String, String> findDetailById(int id) {
        return orderDao.findById4Detail(id);
    }
}
