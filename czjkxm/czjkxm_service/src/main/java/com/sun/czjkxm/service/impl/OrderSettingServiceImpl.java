package com.sun.czjkxm.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.sun.czjkxm.dao.OrderSettingDao;
import com.sun.czjkxm.pojo.OrderSetting;
import com.sun.czjkxm.service.OrderSettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

@Service(interfaceClass = OrderSettingService.class)
public class OrderSettingServiceImpl implements OrderSettingService {
    @Autowired
    private OrderSettingDao orderSettingDao;

    /**
     * 加入多条预约限制信息
     * @param osList
     */
    @Override
    @Transactional//事务控制
    public void addBatch(List<OrderSetting> osList) throws Exception {
        //判断List<Ordersetting>不为空
        if (!CollectionUtils.isEmpty(osList)) {
            //遍历导入的预约设置信息List<Ordersettiing>
            //为什么不把日期格式的Date直接发送过来
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            for (OrderSetting orderSetting : osList) {
                //通过预约日期查询预约设置表，
                OrderSetting osInDB = orderSettingDao.findByOrderDate(orderSetting.getOrderDate());
                //没有预约设置（表中没有这个日期记录）
                if (null==osInDB) {
                    //调用dao插入数据
                    orderSettingDao.add(orderSetting);
                }else{
                    //有预约设置(表中有这个日期记录)
                    //判断已预约人数是否大于要跟新的最大预约数
                    //已预约人数
                    int oldReservations = osInDB.getReservations();
                    //需要改变成的最大预约人数
                    int newReservations = orderSetting.getReservations();
                    if (newReservations>oldReservations) {
                        //现有人数过多无法改变
                        throw new Exception("这天的现有人数过多:"+ osInDB.getOrderDate());
                    }else{
                        //可以改变预约设置的人数
                        orderSettingDao.updateNumber(orderSetting);
                    }
                }
            }
        }
    }

    /**
     * 通过月份查询预约信息
     * @param month
     * @return
     */
    @Override
    public List<Map<String, Integer>> getOrderSettingByMonth(String month) {
        month+="%";
        return orderSettingDao.getOrderSettingByMonth(month);
    }

    /**
     * 通过月份设置单个月份的预约设置
     * @param orderSetting
     */
    @Override
    public void editNumberByDate(OrderSetting orderSetting) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        OrderSetting byOrderDate = orderSettingDao.findByOrderDate(orderSetting.getOrderDate());

        if (null==byOrderDate) {
            //调用dao插入数据
            orderSettingDao.add(byOrderDate);
        }else{
            //更新数据
            int oldReservations = byOrderDate.getReservations();
            int newSetting = orderSetting.getNumber();
            if (oldReservations>newSetting) {
                //不可修改
                throw new Exception("人数问题");
            }else{
                //可以修改
                orderSettingDao.updateNumber(orderSetting);
            }
        }
    }
}
