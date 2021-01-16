package com.sun.czjkxm.dao;

import com.sun.czjkxm.pojo.OrderSetting;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface OrderSettingDao {
    /**
     * 根据日期查询
     * @param orderDate
     * @return
     */
    OrderSetting findByOrderDate(Date orderDate);

    /**
     * 添加预约设置
     * @param osInDB
     */
    void add(OrderSetting osInDB);

    /**
     * 更新
     * @param osInDB
     */
    void updateNumber(OrderSetting osInDB);


    /**
     * 通过月份查询预约信息
     * @param month
     * @return
     */
    List<Map<String, Integer>> getOrderSettingByMonth(String month);

    int editReservationsByOrderDate(OrderSetting osInDB);
}
