package com.cy.store.service.impl;

import com.cy.store.entity.Cart;
import com.cy.store.entity.Product;
import com.cy.store.mapper.CartMapper;
import com.cy.store.mapper.ProductMapper;
import com.cy.store.service.ICartService;
import com.cy.store.service.ex.AccessDeniedException;
import com.cy.store.service.ex.CartNotFoundException;
import com.cy.store.service.ex.InsertException;
import com.cy.store.service.ex.UpdateException;
import com.cy.store.vo.CartVO;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * 购物车业务层接口的实现类
 *
 * @author 魏敏捷
 * @version 1.0
 */

@Service
public class CartServiceImpl implements ICartService {

    @Autowired
    private CartMapper cartMapper;

    /**
     * 购物车的业务层依赖于购物车的持久层以及商品的持久层
     */
    @Autowired
    private ProductMapper productMapper;

    /**
     * 将商品添加到购物车
     * @param uid 当前登录用户的id
     * @param pid 商品的id
     * @param amount 增加的数量
     * @param username 当前登录的用户名
     */
    @Override
    public void addToCart(Integer uid, Integer pid, Integer amount, String username) {

        //根据参数pid和uid查询购物车中该商品是否已经存在
        Cart result = cartMapper.findByUidAndPid(uid, pid);

        Date date = new Date();
        if (result == null) {//表示这个商品从来没有被添加到购物车中，则进行新增操作
            Cart cart = new Cart();

            //封装数据：uid,pid,amount
            cart.setUid(uid);
            cart.setPid(pid);
            cart.setNum(amount);//注意前端传来amount时并没有和数据库商品数量进行求和，也就是具体后加的数量

            //查询商品数据，得到商品价格并封装
            Product product = productMapper.findById(pid);
            cart.setPrice(product.getPrice());

            //封装数据：4个日志
            cart.setCreatedUser(username);
            cart.setCreatedTime(date);
            cart.setModifiedUser(username);
            cart.setModifiedTime(date);

            //执行输入的插入操作
            Integer rows = cartMapper.insert(cart);
            if (rows != 1) {
                throw new InsertException("插入数据时出现未知异常");
            }
        } else {//表示当前的商品在购物车中已经存在，则更新这条数据的num值
            //从查询结果中取出原数量，与参数amount相加，得到新的数量
            Integer num = result.getNum() + amount;//加入购物车时只会有+不可能有-

            Integer rows = cartMapper.updateNumByCid(result.getCid(), num, username, date);
            if (rows != 1) {
                throw new InsertException("更新数据时产生未知异常");
            }
        }
    }


    /**
     * 查询某用户的购物车数据
     * @param pageNum 表示显示第几页数据
     * @param uid 用户id
     * @return 该用户的购物车数据的列表
     */
    @Override
    public PageInfo<CartVO> getVOByUidPage(Integer pageNum, Integer uid) {
        //开启分页功能
        PageHelper.startPage(pageNum, 7);

        //查询所有的该用户购物车列表信息
        List<CartVO> list = cartMapper.findVOByUid(uid);
        //在查询后将xj一个一个计算出来
        for (CartVO cartVO : list) {
           cartVO.getXj();
        }

        //获取分页相关数据
        PageInfo<CartVO> page = new PageInfo<>(list, 5);

        return page;
    }


    /**
     * 增加用户的购物车中某商品的数量
     * @param cid 购物车数据id
     * @param uid 用户id
     * @param username 用户名
     * @return 增加成功后新的数量
     */
    @Override
    public Integer addNum(Integer cid, Integer uid, String username) {
        Cart result = cartMapper.findByCid(cid);
        if (result == null) {
            throw new CartNotFoundException("数据不存在");
        }
        if (!result.getUid().equals(uid)) {
            throw new AccessDeniedException("数据非法访问");
        }
        Integer num = result.getNum() + 1;
        Integer rows = cartMapper.updateNumByCid(cid, num, username, new Date());
        if (rows != 1) {
            throw new UpdateException("更新数据时产生未知异常");
        }
        return num;
    }


    /**
     * 查询指定cid的数据 这里对应前端页面中用户在购物车里所选中的(也就是想买的)购物车项的清单
     * @param uid 用户id
     * @param cids 所选中的购物车数据id
     * @return 封装好的购物车订单列表
     */
    @Override
    public List<CartVO> getVOByCids(Integer uid, Integer[] cids) {
        List<CartVO> list = cartMapper.findVOByCids(cids);

        //可以使用for遍历,这里可以用迭代器遍历
        Iterator<CartVO> it = list.iterator();
        while (it.hasNext()) {//看是否有下一个元素

            //指向的是该元素之前,所以需要next得到该元素
            CartVO cart = it.next();

            if (!cart.getUid().equals(uid)) {//表示当前的数据不属于当前的用户
                /**
                 * 不能用list.remove(cart)
                 * 在迭代器进行遍历的时候不能使用集合的移除
                 * 方法,需要用迭代器特有的移除方法
                 */
                it.remove();
            }
        }
        return list;
    }


}
