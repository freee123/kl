package com.sun.czjkxm.service;

import com.sun.czjkxm.pojo.Order;

import java.util.Map;

public interface OrderService {
    /**
     * 提交预约
     * @param orderInfo
     * @return
     */
    Order submitOrder(Map<String, String> orderInfo) throws Exception;

    /**
     * 查询成功预约的订单信息
     * @param id
     * @return
     */
    Map<String, String> findDetailById(int id);
}
