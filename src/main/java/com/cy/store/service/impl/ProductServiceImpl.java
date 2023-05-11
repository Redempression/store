package com.cy.store.service.impl;



import com.cy.store.entity.Product;
import com.cy.store.mapper.ProductMapper;
import com.cy.store.service.IProductService;
import com.cy.store.service.ex.ProductNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * 处理商品数据的业务层实现类
 *
 * @author 魏敏捷
 * @version 1.0
 */
//因为要将这个实现类交给spring管理,所以需要在类上加@Service
@Service
public class ProductServiceImpl implements IProductService {

    @Autowired
    private ProductMapper productMapper;


    @Override
    public List<Product> findHotList() {
        List<Product> productList = productMapper.findHotList();
        for (Product product : productList) {
            //将无需展示的数据清空，节约内存
            product.setPriority(null);
            product.setCreatedUser(null);
            product.setCreatedTime(null);
            product.setModifiedUser(null);
            product.setModifiedTime(null);
        }

        return productList;
    }

    @Override
    public Product findById(Integer id) {
        Product product = productMapper.findById(id);
        // 判断查询结果是否为null
        if(product == null){
            throw new ProductNotFoundException("尝试访问的商品数据不存在");
        }
        // 将查询结果中的部分属性设置为null
        product.setPriority(null);
        product.setCreatedUser(null);
        product.setCreatedTime(null);
        product.setModifiedUser(null);
        product.setModifiedTime(null);

        return product;
    }
}

