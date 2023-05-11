package com.cy.store.controller;


import com.cy.store.service.ICartService;
import com.cy.store.util.JsonResult;
import com.cy.store.vo.CartVO;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * 购物车控制层
 * @author 魏敏捷
 * @version 1.0
 */
@RestController //其作用等同于@Controller+@ResponseBody
//@Controller
@RequestMapping("carts")
public class CartController extends BaseController {

    @Autowired
    private ICartService cartService;

    /**
     * 将商品添加到购物车
     * @param pid 商品的id
     * @param amount 增加的数量
     * @param session
     * @return
     */
    @GetMapping("add_to_cart")
    public JsonResult<Void> addToCart(Integer pid,
                                      Integer amount,
                                      HttpSession session) {

        cartService.addToCart(getUidFromSession(session),
                pid,
                amount,
                getUsernameFromSession(session));


        return new JsonResult<>(OK);
    }

    /**
     * 查询某用户的购物车数据列表 --分页
     *
     * @param session
     * @param pageNum 表示显示第几页数据
     * @return
     */
    @GetMapping({"", "/"})
    public JsonResult<PageInfo<CartVO>> getVOByUid(HttpSession session,
                                                   Integer pageNum) {
        PageInfo<CartVO> data = cartService.getVOByUidPage(pageNum, getUidFromSession(session));
        return new JsonResult<>(OK, data);
    }


    /**
     * 增加用户的购物车中某商品的数量
     *
     * @param cid
     * @param session
     * @return
     */
    @PostMapping("{cid}/num/add")
    public JsonResult<Integer> addNum(@PathVariable("cid") Integer cid, HttpSession session) {
        Integer data = cartService.addNum(
                cid,
                getUidFromSession(session),
                getUsernameFromSession(session));
        return new JsonResult<Integer>(OK, data);
    }


    /**
     * 查询指定cid的数据 这里对应前端页面中用户在购物车里所选中的(也就是想买的)购物车项的清单
     * @param cids
     * @param session
     * @return
     */
    @GetMapping("list")
    public JsonResult<List<CartVO>> getVOByCid(Integer[] cids, HttpSession session) {
        List<CartVO> data = cartService.getVOByCids(getUidFromSession(session), cids);
        return new JsonResult<>(OK, data);
    }


}
