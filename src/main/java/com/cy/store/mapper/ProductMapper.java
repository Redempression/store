package com.cy.store.mapper;


import com.cy.store.entity.Product;

import java.util.List;

/**
 * 商品的持久层接口
 * @author 魏敏捷
 * @version 1.0
 */
public interface ProductMapper {


    /**
     * 查询热销商品的前四名
     * @return 热销商品前四名的集合
     */
    List<Product> findHotList();


    /**
     * 通过商品的id获取商品详情
     * @param id 商品的id
     * @return 匹配的商品详情，如果没有匹配的数据则返回null
     */
    Product findById(Integer id);
}
