package com.cy.store.mapper;

import com.cy.store.entity.Cart;
import com.cy.store.vo.CartVO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;


/**
 * @author 魏敏捷
 * @version 1.0
 */
//@SpringBootTest表示当前的类是一个测试类,不会随同项目一块打包
@SpringBootTest

/**
 * 1.@RunWith表示启动这个单元测试类,否则这个单元测试类是不能运行的,需要传递
 * 一个参数,该参数必须是SpringRunner.class即SpringRunner的实例类型
 * 2.敲完@RunWith(SpringRunner.class)后鼠标分别放在SpringRunner和@RunWith上按alt+enter分别导入包
 * 3.单元测试类中出现的方法必须是单元测试方法
 * 4.单元测试方法的特点:必须被@Test注解修饰;返回值类型必须是void;方法的参数列表不指定任何类型;方法的访问修饰符必须是public
 */
@RunWith(SpringRunner.class)
public class CartMapperTests {

    /**
     * 单元测试方法的特点:
     * 1、必须被@Test注解修饰;
     * 2、返回值类型必须是void;
     * 3、方法的参数列表不指定任何类型;
     * 4、方法的访问修饰符必须是public
     * <p>
     * 四个同时满足就可以单独独立运行，不用启动整个项目，可以做单元测试，提升了代码的测试效率
     */

    //在java中接口是不能够直接创建bean的,所以idea认为这个语法不合理,
    // 但本质上在项目启动时mybatis自动创建了接口的动态代理实现类,所以从项目的运行角度讲这不能算是错
    //解决办法:
    //在Settings里面搜索inspections,依次找到Spring->Spring Core->Code->Autowiring for Bean Class
    // 然后将Severity的Error改为Warning
    @Autowired
    private CartMapper cartMapper;

    @Test
    public void insert() {
        Cart cart = new Cart();
        cart.setUid(13);
        cart.setPid(10000011);
        cart.setNum(2);
        cart.setPrice(1000L);
        cartMapper.insert(cart);
    }

    @Test
    public void updateNumByCid() {

        cartMapper.updateNumByCid(1,4,"丽丽",new Date());

    }

    @Test
    public void findByUidAndPid(){
        System.out.println(cartMapper.findByUidAndPid(13,10000011));
    }


    @Test
    public void findVOByUid() {

        cartMapper.findVOByUid(13).forEach(System.err::println);

    }


    @Test
    public void findByCid() {
        System.out.println(cartMapper.findByCid(1));
    }


    @Test
    public void findVOByCids() {
        Integer[] cids = {1, 2, 6, 8, 100};//可以写表中不存在的,无非就是查不到数据,并不会报错
        List<CartVO> list = cartMapper.findVOByCids(cids);
        for (CartVO item : list) {
            System.out.println(item);
        }
    }


}