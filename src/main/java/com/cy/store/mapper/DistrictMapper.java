package com.cy.store.mapper;

import com.cy.store.entity.District;

import java.util.List;

/**
 * 省市区数据模块的持久层接口
 * @author 魏敏捷
 * @version 1.0
 */
public interface DistrictMapper {


    /**
     * 根据父代码号查询区域信息
     * @param parent 父代码号
     * @return 某个父区域下所有的区域列表
     */
    List<District> findByParent(String parent);//查询的结果可能是多个,所以放在集合中


    /**
     * 根据code值获取对应名字
     * @param code 自身的代码号
     * @return 区域名
     */
    String findNameByCode(String code);


}
