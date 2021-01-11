package com.sun.czjkxm.service;

import com.sun.czjkxm.pojo.OrderSetting;

import java.util.List;
import java.util.Map;

public interface OrderSettingService {
    /**
     * 加入多条预约限制信息
     * @param osList
     */
    void addBatch(List<OrderSetting> osList) throws Exception;

    /**
     * 通过月份查询预约信息
     * @param month
     * @return
     */
    List<Map<String, Integer>> getOrderSettingByMonth(String month);
}
