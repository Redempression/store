package com.cy.store.controller;

import com.cy.store.entity.Order;
import com.cy.store.service.IOrderService;
import com.cy.store.util.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

/**
 * 订单控制层
 *
 * @author 魏敏捷
 * @version 1.0
 */
@RequestMapping("orders")
@RestController
public class OrderController extends BaseController {

    @Autowired
    private IOrderService orderService;


    @GetMapping("create")
    public JsonResult<Order> create(Integer aid,
                                    Integer[] cids,
                                    HttpSession session) {

        Order data = orderService.create(aid, getUidFromSession(session),
                                        getUsernameFromSession(session), cids);


        return new JsonResult<>(OK, data);

    }


}
