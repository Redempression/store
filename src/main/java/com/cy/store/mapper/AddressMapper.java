package com.cy.store.mapper;

import com.cy.store.entity.Address;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * 用户收货地址模块的持久层接口
 * @author 魏敏捷
 * @version 1.0
 */
public interface AddressMapper {

    /**
     * 插入用户的收货地址的数据
     * @param address 收货地址数据
     * @return 受影响的行数
     */
    Integer insert(Address address);


    /**
     * 根据用户的id来统计用户收货地址的数量
     * @param uid 用户id
     * @return 当前用户收获地址的总数
     */
    Integer countByUid(Integer uid);


    /**
     * 根据用户的uid查询用户的收货地址数据
     * @param uid 用户uid
     * @return 收货地址数据
     */
    List<Address> findByUid(Integer uid);


    /**
     * 根据aid查询收货地址数据
     * @param aid 收货地址aid
     * @return 收货地址数据,如果没有找到则返回null值
     */
    Address findByAid(Integer aid);

    /**
     * 根据用户uid修改用户的收货地址统一设置为非默认
     * @param uid 用户uid
     * @return 受影响的行数
     */
    Integer updateNonDefault(Integer uid);


    /**
     * 将指定的收货地址修改为默认
     * @param aid 收货地址aid
     * @param modifiedUser 修改人
     * @param modifiedTime 修改时间
     * @return 受影响的行数
     */
    Integer updateDefaultByAid(
            @Param("aid") Integer aid,
            @Param("modifiedUser") String modifiedUser,
            @Param("modifiedTime") Date modifiedTime);


    /**
     * 将指定的收货地址删除
     * @param aid 收货地址aid
     * @return 受影响的行数
     */
    Integer deleteByAid(@Param("aid") Integer aid);


    /**
     * 根据用户uid查询用户最后一次被修改的收货地址数据
     * @param uid 用户id
     * @return 收货地址数据
     */
    Address findLastModified(Integer uid);


    /**
     * 将指定的收货地址修改
     * @param address 修改的地址
     * @return 受影响的行数
     */
    Integer updateAddressByAid(Address address);
}
