package com.cy.store.service;

import com.cy.store.entity.Address;
import com.cy.store.entity.Order;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;

/**
 * @author 魏敏捷
 * @version 1.0
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class OrderServiceTests {

    @Autowired
    private IOrderService orderService;


    @Test
    public void create(){
        Order order = orderService.create(10, 13, "魏敏捷", new Integer[]{3, 4, 5, 6});
        System.out.println(order);
    }


}