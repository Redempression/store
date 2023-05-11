package com.cy.store.mapper;

import com.cy.store.entity.Cart;
import com.cy.store.vo.CartVO;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * 购物车模块的持久层接口
 * @author 魏敏捷
 * @version 1.0
 */
public interface CartMapper {

    /**
     * 插入购物车数据
     * @param cart 购物车数据
     * @return 受影响的行数
     */
    Integer insert(Cart cart);

    /**
     * 修改购物车数据中商品的数量
     * @param cid 购物车数据的id
     * @param num 新的数量
     * @param modifiedUser 修改执行人
     * @param modifiedTime 修改时间
     * @return 受影响的行数
     */
    Integer updateNumByCid(
            @Param("cid") Integer cid,
            @Param("num") Integer num,
            @Param("modifiedUser") String modifiedUser,
            @Param("modifiedTime") Date modifiedTime);

    /**
     * 根据用户id和商品id查询购物车中的数据
     * @param uid 用户id
     * @param pid 商品id
     * @return 匹配的购物车数据，如果该用户的购物车中并没有该商品，则返回null
     */
    Cart findByUidAndPid(
            @Param("uid") Integer uid,
            @Param("pid") Integer pid);


    /**
     * 查询某用户的购物车数据
     * @param uid 用户id
     * @return 该用户的购物车与商品信息值对象数据的列表
     */
    List<CartVO> findVOByUid(Integer uid);


    /**
     * 通过cid获取某条购物数据信息
     * @param cid 购物车数据id
     * @return
     */
    Cart findByCid(Integer cid);

    /**
     * 查询指定cid的数据
     * @param cids 购物车数据id列表
     * @return 购物车与商品信息值对象列表
     */
    List<CartVO> findVOByCids(Integer[] cids);


}

