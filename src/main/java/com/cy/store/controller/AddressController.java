package com.cy.store.controller;


import com.cy.store.entity.Address;
import com.cy.store.service.IAddressService;
import com.cy.store.util.JsonResult;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @author 魏敏捷
 * @version 1.0
 */
@RestController //其作用等同于@Controller+@ResponseBody
//@Controller
@RequestMapping("addresses")
public class AddressController extends BaseController {

    @Autowired
    private IAddressService addressService;


    /**
     * 新增收货地址
     *
     * @param address 地址详情信息
     * @param session 通过session获取用户id和姓名(姓名便于确定是谁新增的地址)
     * @return 返回 200 成功码
     */
    @PostMapping("add_new_address")
    public JsonResult<Void> addNewAddress(Address address,
                                          HttpSession session) {

        addressService.addNewAddress(address,
                getUidFromSession(session),
                getUsernameFromSession(session));

        return new JsonResult<>(OK);
    }


    /**
     * 获取用户所有收货地址 --分页11111111
     * @param session
     * @param pageNum 表示显示第几页数据
     * @return
     */
    @GetMapping({"", "/"})
    public JsonResult<PageInfo<Address>> getByUidWithPage(HttpSession session, Integer pageNum) {
        Integer uid = getUidFromSession(session);
        PageInfo<Address> data = addressService.getAddressPage(pageNum, uid);
        return new JsonResult<>(OK, data);
    }


    /**
     * 获取用户所有收货地址 --不分页
     * @param session
     * @return
     */
    @GetMapping("get_by_uid")
    public JsonResult<List<Address>> getByUid(HttpSession session) {
        Integer uid = getUidFromSession(session);
        List<Address> data = addressService.getByUid(uid);
        return new JsonResult<>(OK, data);
    }



    //RestFul风格的请求编写
    //{aid} 和 set_default 前后无所谓， 只要能做到前端与之一一对应即可
    @GetMapping("{aid}/set_default")
    public JsonResult<Void> setDefault(HttpSession session,
                                       @PathVariable("aid") Integer aid){
        addressService.setDefault(aid,
                getUidFromSession(session),
                getUsernameFromSession(session));

        return new JsonResult<>(OK);
    }


    /**
     *   /address/delete/{aid}
     *   GET
     *   JsonResult<Void>
     *   Integer aid   HttpSession session
     * */
    @GetMapping("delete/{aid}")
    public JsonResult<Void> delete(HttpSession session,
                                   @PathVariable("aid") Integer aid){
        addressService.delete(aid,
                getUidFromSession(session),
                getUsernameFromSession(session));
        return new JsonResult<>(OK);
    }


    /**
     * 修改地址
     * /address/update/{oldAddressId}
     * JsonResult<Void>
     * POST
     * Address address
     */
    @PostMapping("update/{oldAddressId}")
    public JsonResult<Void> updateAddress(@PathVariable("oldAddressId") Integer aid,
                                          Address address, HttpSession session){
        address.setAid(aid);
        address.setUid(getUidFromSession(session));
        address.setModifiedUser(getUsernameFromSession(session));

        addressService.update(address);
        return new JsonResult<>(OK);
    }

    /**
     * 获取指定地址详细信息
     * @param oldAddressId
     * @return
     */
    @GetMapping("find_by_aid/{oldAddressId}")
    public JsonResult<Address> findByAid(@PathVariable("oldAddressId") Integer oldAddressId){

        return new JsonResult<>(OK,addressService.findByAid(oldAddressId));
    }
}
