package com.cy.store.controller;


import com.cy.store.entity.Product;
import com.cy.store.service.IProductService;
import com.cy.store.util.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author 魏敏捷
 * @version 1.0
 */
@RestController //其作用等同于@Controller+@ResponseBody
//@Controller
@RequestMapping("products")
public class ProductController extends BaseController {

    @Autowired
    private IProductService productService;


    /**
     * 获取热销排行前四名商品
     * @return
     */
    @GetMapping("hot_list")
    public JsonResult<List<Product>> getHotList(){

        return new JsonResult<>(OK,productService.findHotList());
    }


    /**
     * 通过商品的id获取商品详情
     * @param id
     * @return
     */
    @GetMapping("{id}/details")
    public JsonResult<Product> findById(@PathVariable("id") Integer id){


        return new JsonResult<>(OK,productService.findById(id));

    }
}
