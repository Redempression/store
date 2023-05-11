package com.cy.store.service.impl;


import com.cy.store.entity.District;
import com.cy.store.mapper.DistrictMapper;
import com.cy.store.service.IDistrictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 省市区的数据查询业务层接口的实现类
 *
 * @author 魏敏捷
 * @version 1.0
 */
//因为要将这个实现类交给spring管理,所以需要在类上加@Service
@Service
public class DistrictServiceImpl implements IDistrictService {

    @Autowired
    private DistrictMapper districtMapper;


    /**
     * 根据父代号来查询区域信息(省市区)
     * @param parent 父代号
     * @return
     */
    @Override
    public List<District> getByParent(String parent) {

        List<District> list = districtMapper.findByParent(parent);

        /**
         * 在进行网络数据传输时,为了尽量避免无效数据的传递,可以将无效数据
         * 设置为null,这样既节省流量,又提升了效率
         * 这里id和parent一个是唯一标识，一个是用来查数据的条件，所以在查到数据
         * 后都变成无效数据，由于该数据表的数据过多，对于这种无效数据就设置为null
         */

        for (District district : list) {
            district.setId(null);
            district.setParent(null);
        }
        return list;
    }

    /**
     * 根据code值获取对应名字
     * @param code 自身的代码号
     * @return
     */
    @Override
    public String getNameByCode(String code) {
        String name = districtMapper.findNameByCode(code);
        return name;
    }


}

