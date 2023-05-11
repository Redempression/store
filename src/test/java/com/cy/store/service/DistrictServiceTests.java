package com.cy.store.service;

import com.cy.store.entity.District;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @author 魏敏捷
 * @version 1.0
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class DistrictServiceTests {

    @Autowired
    private IDistrictService districtService;


    @Test
    public void getByParent(){
        //86代表中国,所有的省父代码号都是86
        List<District> list = districtService.getByParent("86");

        //list.forEach(System.out::println);

        for (District district : list) {

            //用错误输出流，红色看的更清楚一些
            System.err.println(district);
        }

    }

}