package com.cy.store.mapper;

import com.cy.store.entity.Order;
import com.cy.store.entity.OrderItem;

/**
 * 订单数据模块的持久层接口
 * @author 魏敏捷
 * @version 1.0
 */
public interface OrderMapper {

    /**
     * 插入订单数据
     * @param order 订单数据
     * @return 受影响的行数
     */
    Integer insertOrder(Order order);

    /**
     * 插入某一个订单中商品数据
     * @param orderItem 订单中商品数据  订单项
     * @return 受影响的行数
     */
    Integer insertOrderItem(OrderItem orderItem);
}

