package com.cy.store.service.impl;


import com.cy.store.entity.Address;
import com.cy.store.entity.Order;
import com.cy.store.entity.OrderItem;
import com.cy.store.mapper.OrderMapper;
import com.cy.store.service.IAddressService;
import com.cy.store.service.ICartService;
import com.cy.store.service.IOrderService;
import com.cy.store.service.IUserService;
import com.cy.store.service.ex.InsertException;
import com.cy.store.vo.CartVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 用户收货订单业务层接口的实现类
 *
 * @author 魏敏捷
 * @version 1.0
 */
//因为要将这个实现类交给spring管理,所以需要在类上加@Service
@Service
public class OrderServiceImpl implements IOrderService {

    @Autowired
    private OrderMapper orderMapper;

    //需要调用业务层的getByAid方法
    @Autowired
    private IAddressService addressService;

    //需要调用业务层的getVOByCids方法
    @Autowired
    private ICartService cartService;

    /**
     * 创建订单
     * @param aid 该订单的地址id
     * @param uid 该订单的用户id
     * @param username 该订单的用户名
     * @param cids 该订单所买的商品所对应的购物车项的id
     * @return
     */
    @Override
    public Order create(Integer aid, Integer uid, String username, Integer[] cids ) {

        //返回的购物车项列表中的对象都是即将下单的
        List<CartVO> list = cartService.getVOByCids(uid, cids);

        //获取该订单一共所需价钱
        Double totalMoney = new CartVO().getTotalMoney(list);

        //获取该订单的收货地址
        Address address = addressService.getByAid(aid, uid);


        /**插入Order数据 */

        Order order = new Order();

        order.setUid(uid);
        //封装收货地址
        order.setRecvName(address.getName());
        order.setRecvPhone(address.getPhone());
        order.setRecvProvince(address.getProvinceName());
        order.setRecvCity(address.getCityName());
        order.setRecvArea(address.getAreaName());
        order.setRecvAddress(address.getAddress());

        //封装创建时间,支付状态和总价
        order.setOrderTime(new Date());
        order.setStatus(0);
        order.setTotalPrice(totalMoney);

        //封装四个日志
        order.setCreatedUser(username);
        order.setCreatedTime(new Date());
        order.setModifiedUser(username);
        order.setModifiedTime(new Date());

        //将这些数据加入到订单中
        Integer rows = orderMapper.insertOrder(order);
        if (rows != 1) {
            throw new InsertException("插入数据时产生未知的异常");
        }

        //插入数据——将某条订单的所有商品的详细数据(如商品图片，名字等)插入
        for (CartVO cartVO : list) {
            OrderItem orderItem = new OrderItem();

            /**
             * 此时获取的oid不为空,因为在配置文件里面开启了oid主
             * 键自增,所以上面的代码执行插入时就自动将oid赋值了
             */
            orderItem.setOid(order.getOid());

            orderItem.setPid(cartVO.getPid());
            orderItem.setTitle(cartVO.getTitle());
            orderItem.setImage(cartVO.getImage());
            orderItem.setPrice(cartVO.getRealPrice());
            orderItem.setNum(cartVO.getNum());

            //日志字段
            orderItem.setCreatedUser(username);
            orderItem.setCreatedTime(new Date());
            orderItem.setModifiedUser(username);
            orderItem.setModifiedTime(new Date());

            //将这些数据加入到订单项中
            rows = orderMapper.insertOrderItem(orderItem);
            if (rows != 1) {
                throw new InsertException("插入数据时产生未知的异常");
            }
        }
        return order;
    }
}


