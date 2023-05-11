package com.cy.store.service;


import com.cy.store.entity.Address;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * 用户收货地址业务层接口
 * @author 魏敏捷
 * @version 1.0
 */
public interface IAddressService {

    /**
     *这三个参数的由来:
     * 1.首先肯定要有address
     * 2.业务层需要根据uid查询该用户收货地址总数及新建地址时给字段uid赋值
     * 但新建收货地址的表单中并没有哪个控件让输入用户uid,所以需要控制层将uid传给业务层
     * 3.业务层在创建/修改收货地址时需要同时修改数据库中创建人/修改人的字段
     * 但新建收货地址的表单中并没有哪个控件让输入用户username,所以需要控制层将username传给业务层
     * 注意:> 可以用HttpSession session代替Integer uid, String username,但
     * 这样写的话就需要把BaseController类下获取uid,username的方法重新封装到一个
     * 类中并让IAddressServiceImp实现类继承该类,这样就需要微调一下代码逻辑,太麻
     * 烦,并且,最好每一层只处理该层需要做的事情,session对象是控制层传递的,所以就
     * 把session对象定义封装在控制层中,不需要在业务层中额外处理以降低耦合
     */
    void addNewAddress(Address address, Integer uid, String username);


    /**
     * 通过aid找到对应地址
     * @param aid
     * @return
     */
    Address findByAid(Integer aid);

    /**
     * 根据用户的uid查询用户的收货地址数据
     * @param uid 用户uid
     * @return 收货地址数据
     */
    List<Address> getByUid(Integer uid);



    /**
     * 查询用户收货地址的分页信息
     * */
    PageInfo<Address> getAddressPage(Integer pageNum, Integer uid);


    /**
     * 修改某个用户的某条收货地址数据为默认收货地址
     * @param aid 收货地址的id
     * @param uid 用户id
     * @param username 修改执行人
     */
    void setDefault(Integer aid,Integer uid,String username);



    /**
     * 根据分析可得,该抽象方法的实现依赖于持久层的以下方法:
     *
     * 1.findByAid:查询该条地址数据是否存在,参数是aid
     * 2.判断地址数据归属是否正确
     * 3.deleteByAid:删除地址数据,参数是aid
     * 4.判断删除的是否是默认地址
     * 5.countByUid:统计用户地址数量,参数是uid
     * 6.findLastModified:查询得到最后修改的一条地址,参数是uid
     * 7.updateDefaultByAid:设置默认收货地址,参数是aid,modifiedUser,modifiedTime
     *
     * 稍加分析可以得出接下来定义的抽象方法的参数是:aid,uid,username
     * */
    /**
     * 删除用户选中的收货地址数据
     * @param aid 收货地址id
     * @param uid 用户id
     * @param username 用户名
     */
    void delete(Integer aid,Integer uid,String username);


    /**
     * 修改用户的收货地址
     * @param address
     */
    void update(Address address);


    /**
     * 通过用户给定的地址，来填充到收获订单里
     * @param aid  地址id
     * @param uid  用户id
     * @return
     */
    Address getByAid(Integer aid, Integer uid);

}

