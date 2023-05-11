package com.cy.store.service.impl;


import com.cy.store.entity.Address;
import com.cy.store.mapper.AddressMapper;
import com.cy.store.service.IAddressService;
import com.cy.store.service.IDistrictService;
import com.cy.store.service.ex.*;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 用户收货地址业务层接口的实现类
 *
 * @author 魏敏捷
 * @version 1.0
 */
//因为要将这个实现类交给spring管理,所以需要在类上加@Service
@Service
public class AddressServiceImpl implements IAddressService {

    @Autowired
    private AddressMapper addressMapper;


    /**
     *    在新增收货地址的业务层需要对address进行封装,使其存有所有数据,
     * 然后将address传给持久层(记住,持久层只会根据传过来的参数调用某个方法
     * 与数据库交互,永远不会有额外的实现),而此时新增收货地址的业务层并没有
     * 省市区的数据,所以需要依赖于获取省市区列表的业务层对应的接口中
     * 的getNameByCode方法
     * */
    @Autowired
    private IDistrictService districtService;



    /**
     * 为了方便日后修改最大收货地址数量,可以在配置文件
     * application.properties中定义user.address.max-count=20
     */
    //spring读取配置文件中数据:@Value("${user.address.max-count}")

    @Value("${user.address.max-count}")
    private Integer maxCount;


    /**
     * 添加新的收货地址
     * @param address
     * @param uid
     * @param username
     */
    @Override
    public void addNewAddress(Address address, Integer uid, String username) {
        //调用收货地址统计的方法
        Integer count = addressMapper.countByUid(uid);
        if(count >= maxCount){
            throw new AddressCountLimitException("用户收货地址超过上限");
        }

        /**
         * 对address对象中的数据进行补全:省市区的名字看前端代码发现前端传递过来的省市区的name分别为:
         * provinceCode,cityCode,areaCode,所以这里可以用address对象的get方法获取这三个的数据
         */
        String provinceName = districtService.getNameByCode(address.getProvinceCode());
        String cityName = districtService.getNameByCode(address.getCityCode());
        String areaName = districtService.getNameByCode(address.getAreaCode());
        address.setProvinceName(provinceName);
        address.setCityName(cityName);
        address.setAreaName(areaName);

        //uid,isDefault
        address.setUid(uid);
        //三元运算符
        Integer isDefault = count == 0 ? 1 : 0;//1表示默认收货地址,0反之
        address.setIsDefault(isDefault);

        //补全四项日志
        address.setCreatedUser(username);
        address.setModifiedUser(username);
        address.setCreatedTime(new Date());
        address.setModifiedTime(new Date());

        //调用插入收货地址的方法
        Integer rows = addressMapper.insert(address);
        if (rows != 1) {
            throw new InsertException("插入用户的收货地址时产生未知异常");
        }

    }

    /**
     * 通过aid找到对应地址
     * @param aid
     * @return
     */
    @Override
    public Address findByAid(Integer aid) {
        Address address = addressMapper.findByAid(aid);
        if(address == null){
            throw new AddressNotFoundException("收货地址不存在");
        }

        return address;

    }

    /**
     * 根据用户的uid查询用户的收货地址数据
     * @param uid 用户uid
     * @return 收货地址数据
     */
    @Override
    public List<Address> getByUid(Integer uid) {
        List<Address> list = addressMapper.findByUid(uid);
        /**
         * 收货地址列表在前端只展示了四个数据,这里让其他值为空降低数据量
         * ProvinceName,CityName,AreaName,aid,tel(确认订单页展示展示用户地
         * 址时用到tel)在展示地址列表用不到,但是后面提交订单时的地址会用到,所以这里不设为null
         * */
        for (Address address : list) {
            address.setProvinceCode(null);
            address.setCityCode(null);
            address.setAreaCode(null);
            address.setZip(null);
//            address.setIsDefault(null);
            address.setCreatedTime(null);
            address.setCreatedUser(null);
            address.setModifiedTime(null);
            address.setModifiedUser(null);

            //address.setAid(null);
            //address.setUid(null);
            //address.setProvinceName(null);
            //address.setCityName(null);
            //address.setAreaName(null);
            //address.setTel(null);
        }
        return list;
    }


    /**
     * 查询用户收货地址的分页信息
     * @param pageNum
     * @param uid
     * @return
     */
    @Override
    public PageInfo<Address> getAddressPage(Integer pageNum, Integer uid) {

        //开启分页功能
        PageHelper.startPage(pageNum, 4);
        //查询所有的收货地址信息
        List<Address> list = getByUid(uid);
        //获取分页相关数据
        PageInfo<Address> page = new PageInfo<>(list, 5);

        return page;

    }

    /**
     * 修改某个用户的某条收货地址数据为默认收货地址
     * @param aid 收货地址的id
     * @param uid 用户id
     * @param username 修改执行人
     */
    @Override
    public void setDefault(Integer aid, Integer uid, String username) {

        //1.检测是否有该条收货地址数据
        Address address = findByAid(aid);

        //2.检测当前获取到的收货地址数据的归属
        if (!address.getUid().equals(uid)) {
            throw new AccessDeniedException("非法数据访问");
        }

        //3.先将所有的收货地址设置为非默认
        Integer rows = addressMapper.updateNonDefault(uid);
        if (rows < 1) {
            throw new UpdateException("更新数据时产生未知的异常");
        }

        //4.将用户选中的地址设置为默认收货地址
        rows = addressMapper.updateDefaultByAid(aid, username, new Date());
        if (rows != 1) {
            throw new UpdateException("更新数据时产生未知的异常");
        }
    }


    /**
     * 删除用户选中的收货地址数据
     * @param aid 收货地址id
     * @param uid 用户id
     * @param username 用户名
     */
    @Override
    public void delete(Integer aid, Integer uid, String username) {
        //1.查询该条地址数据是否存在
        Address result = findByAid(aid);
        //2.判断地址数据归属是否正确
        if (!result.getUid().equals(uid)) {
            throw new AccessDeniedException("非法数据访问");
        }
        //3.删除地址数据
        Integer rows = addressMapper.deleteByAid(aid);
        if (rows != 1) {
            throw new DeleteException("删除数据时产生未知的异常");
        }
        //4.如果删除的是非默认地址则不需要再做后面的任何操作,终止程序
        if (result.getIsDefault() == 0) {
            return;
        }

        /** 删除的是默认地址做以下操作*/
        //5.统计用户地址数量
        Integer count = addressMapper.countByUid(uid);
        //如果该用户就一条默认地址，且删除了，统计为0，就不需要再做后面的任何操作,终止程序
        if (count == 0) {
            return;
        }
        //6.查询得到最后修改的一条地址
        Address address = addressMapper.findLastModified(uid);
        //7.设置默认收货地址
        rows = addressMapper.updateDefaultByAid(address.getAid(), username, new Date());
        if (rows != 1) {
            throw new UpdateException("更新数据时产生未知的异常");
        }
    }

    /**
     * 修改用户的收货地址
     * @param address
     */
    @Override
    public void update(Address address) {
        //1.查询该条地址数据是否存在
        Address result = findByAid(address.getAid());
        //2.判断地址数据归属是否正确
        if (!result.getUid().equals(address.getUid())) {
            throw new AccessDeniedException("非法数据访问");
        }
        //3.修改地址数据
        Integer rows = addressMapper.updateAddressByAid(address);
        if (rows != 1) {
            throw new UpdateAddressException("修改数据时产生未知的异常");
        }
    }

    /**
     * 通过用户给定的地址，来填充到收获订单里
     * @param aid  地址id
     * @param uid  用户id
     * @return
     */
    @Override
    public Address getByAid(Integer aid, Integer uid) {
        //1.查询该条地址数据是否存在
        Address address = findByAid(aid);
        //2.判断地址数据归属是否正确
        if (!address.getUid().equals(uid)) {
            throw new AccessDeniedException("非法数据访问");
        }
        address.setProvinceCode(null);
        address.setCityCode(null);
        address.setAreaCode(null);
        address.setCreatedUser(null);
        address.setCreatedTime(null);
        address.setModifiedUser(null);
        address.setModifiedTime(null);
        return address;

    }

}

