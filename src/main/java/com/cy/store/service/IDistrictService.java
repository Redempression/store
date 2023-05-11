package com.cy.store.service;


import com.cy.store.entity.District;

import java.util.List;

/**
 * 省市区的数据查询业务层接口
 * @author 魏敏捷
 * @version 1.0
 */
public interface IDistrictService {


    /**
     * 根据父代号来查询区域信息(省市区)
     * @param parent 父代号
     * @return 多个区域的信息
     */
    List<District> getByParent(String parent);


    /**
     * 根据code值获取对应名字
     * @param code 自身的代码号
     * @return 区域名
     */
    String getNameByCode(String code);

}

