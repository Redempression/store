package com.cy.store.service;

import com.cy.store.entity.User;
import com.cy.store.service.ex.ServiceException;
import com.cy.store.service.ex.UpdateException;
import com.cy.store.service.ex.UserNotFoundException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

/**
 * @author 魏敏捷
 * @version 1.0
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class UserServiceTests {

    @Autowired
    private IUserService userService;

    /**用户注册方法测试*/
    @Test
    public void reg() {
        /**
         * 进行插入时可能会出错抛出异常,这时需要捕获异常:
         * 1.选中    User user = new User();
         *           user.setUsername("张7");
         *           user.setPassword("123456");
         *           userService.reg(user);
         *           System.out.println("OK");
         * 2.点击导航栏的Code,然后依次点击SurroundWith->try/catch就可以捕获异常了
         * 3.Exception e没有问题,但这里我们知道是Service层的异常,所以可以改为ServiceException e
         * 4.System.out.println(e.getClass().getSimpleName());获取异常对象再获取类的名称然后输出
         * 5.System.out.println(e.getMessage());输出异常信息(是自己在ServiceException的子类类具体设置的信息)
         */
        try {
            User user = new User();
            user.setUsername("www");
            user.setPassword("123456");
            userService.reg(user);
            System.out.println("OK");
        } catch (ServiceException e) {
            //获取类的对象，再获取类的名称
            System.out.println(e.getClass().getSimpleName());
            //获取异常的具体描述信息
            System.out.println(e.getMessage());
        }
    }


    @Test
    public void login(){
        User user = userService.login("www", "123456");
        System.out.println(user);
    }

    @Test
    public void changePassword(){
        userService.changePassword(13,"mm","1234","324");
    }

    @Test
    public void getUserById() {
        System.out.println(userService.getUserById(8));

    }


    @Test
    public void getInfoByUid() {
        System.out.println(userService.getInfoByUid(8));
    }


    @Test
    public void changeInfo() {
        User user = new User();
        user.setPhone("111111111");
        user.setGender(0);
        user.setEmail("22222@qq.com");
        userService.changeInfo(13,user);

    }


    @Test
    public void updateAvatarByUid(){
        userService.updateAvatarByUid(8, "/bb/dd/aa.png","小三");
    }


    /**
     * 测试普通代码块，静态代码块，构造器
     */
    @Test
    public void vv(){
        AA aa = new AA();
        AA aa1 = new AA();

        /**
         * static
         * 普通代码块
         * 构造器
         * 普通代码块
         * 构造器
         * */
    }



}


class AA{
    static {
        System.out.println("static");
    }
    {
        System.out.println("普通代码块");
    }

    public AA() {

        System.out.println("构造器");
    }
}