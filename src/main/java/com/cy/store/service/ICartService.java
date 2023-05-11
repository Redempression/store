package com.cy.store.service;

import com.cy.store.vo.CartVO;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * 购物车业务层接口
 * @author 魏敏捷
 * @version 1.0
 */
public interface ICartService {
    /**
     * 将商品添加到购物车
     * @param uid 当前登录用户的id
     * @param pid 商品的id
     * @param amount 增加的数量
     * @param username 当前登录的用户名
     */
    void addToCart(Integer uid, Integer pid, Integer amount, String username);

    /**
     * 查询某用户的购物车数据
     * @param uid 用户id
     * @return 该用户的购物车数据的列表
     */
    PageInfo<CartVO> getVOByUidPage(Integer pageNum, Integer uid);



    /**
     * 增加用户的购物车中某商品的数量
     * @param cid
     * @param uid
     * @param username
     * @return 增加成功后新的数量
     */
    Integer addNum(Integer cid,Integer uid, String username);


    /**
     * 查询指定cid的数据 这里对应前端页面中用户在购物车里所选中的(也就是想买的)购物车项的清单
     * @param uid 用户id
     * @param cids 所选中的购物车数据id
     * @return 封装好的购物车订单列表
     */
    List<CartVO> getVOByCids(Integer uid, Integer[] cids);//uid是为了判断数据归属是否正确

}

