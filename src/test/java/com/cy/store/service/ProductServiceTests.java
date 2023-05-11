package com.cy.store.service;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.cors.CorsConfiguration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.IntPredicate;
import java.util.stream.IntStream;

/**
 * @author 魏敏捷
 * @version 1.0
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class ProductServiceTests {


    @Autowired
    private IProductService productService;


    @Test
    public void findHotList() {
        productService.findHotList().forEach(System.err::println);
    }


    @Test
    public void findById() {
        System.out.println(productService.findById(10000001));
    }


    @Test
    /**在你面前有一个n阶的楼梯，你一步只能上1阶或2阶。请问，当N=11时，
     * 你可以采用多少种不同的方式爬完这个楼梯（）,当N=9时呢呢（）？  斐波那契数列  */
    public void sum() {
        System.out.println(fun(11)); //144
        System.out.println(fun(9)); //55

    }


    public static int fun(int n) {
        if (n == 1) {
            return 1;
        }
        if (n == 2) {
            return 2;
        }
        return fun(n - 2) + fun(n - 1);
    }


    @Test
    public void tt() {
        int[] nums = {0, 2, 3, 3, 4, 2, 5, 0};
        int k = 3;
        System.out.println(GetNumberOfK(nums, k));
    }


    /**
     * 代码中的类名、方法名、参数名已经指定，请勿修改，直接返回方法规定的值即可
     *
     * @param nums int整型一维数组
     * @param k    int整型
     * @return int整型
     */
    public int GetNumberOfK(int[] nums, int k) {
        // write code here
        IntStream stream = Arrays.stream(nums);
        long count = stream.filter(value -> value == k).count();

        return (int) count;
    }


}