package com.cy.store.service;

import com.cy.store.entity.Address;
import com.cy.store.entity.Order;

/**
 * 订单业务层接口
 * @author 魏敏捷
 * @version 1.0
 */
public interface IOrderService {

    /**
     * 创建订单
     * @param aid 该订单的地址id
     * @param uid 该订单的用户id
     * @param username 该订单的用户名
     * @param cids 该订单所买的商品所对应的购物车项的id
     * @return
     */
    Order create(Integer aid, Integer uid, String username, Integer[] cids);
}
