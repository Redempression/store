package com.cy.store.service;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author 魏敏捷
 * @version 1.0
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class CartServiceTests {

    @Autowired
    private ICartService cartService;

    @Test
    public void addToCart(){

        //批量买买买，魏公子买单
        for (int i = 1; i < 20; i++) {
            int pid = 10000000 + i;
            cartService.addToCart(13,pid, 4, "张三");
        }

    }


}