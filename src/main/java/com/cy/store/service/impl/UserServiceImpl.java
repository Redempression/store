package com.cy.store.service.impl;

import com.cy.store.entity.User;
import com.cy.store.mapper.UserMapper;
import com.cy.store.service.IUserService;
import com.cy.store.service.ex.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.Date;
import java.util.UUID;

/**
 * 用户模块业务层的实现类
 *
 * @author 魏敏捷
 * @version 1.0
 */
//因为要将这个实现类交给spring管理,所以需要在类上加@Service
@Service
public class
UserServiceImpl implements IUserService {

    //reg方法核心就是调用mapper层的方法,所以要声明UserMapper对象并加@Autowired注解
    @Autowired
    private UserMapper userMapper;

    /**
     * 用户注册功能
     */
    @Override
    public void reg(User user) {
        //通过user参数来获取传递过来的username
        String username = user.getUsername();
        //调用mapper的findByUsername(username)判断用户是否被注册过了
        User result = userMapper.findByUsername(username);
        //判断结果集是否为null,不为null的话则需抛出用户名被占用的异常
        if (result != null) {
            //抛出异常
            throw new UsernameDuplicatedException("用户名被占用");
        }

        /**
         * 密码加密处理作用:
         * 1.后端不再能直接看到用户的密码
         * 2.忽略了密码原来的强度,提升了数据安全性
         * 密码加密处理的实现:
         * 串 + password + 串 ——->交给md5算法连续加密三次
         * 串就是数据库字段中的盐值,是一个随机字符串
         */
        //0.获取用户密码
        String oldpassword = user.getPassword();
        //1.随机生成一个盐值(大写的随机字符串)
        String salt = UUID.randomUUID().toString().toUpperCase();
        //2.将密码和盐值作为一个整体进行加密处理，忽略原有密码强度提升数据的安全性
        String md5Password = getMD5Password(oldpassword, salt);
        //3.将盐值保存到数据库
        // -- 原因：每个用户注册时所用的盐值是随机生成的，不一样，在当时生成的盐值与用户设置密码进行加密得到的md5密文，
        //   如果不保存当时的盐值，用户在后续登录时就算密码正确也会因为盐值不一样而登录失败
        user.setSalt(salt);
        //4.将加密之后的密码重新补全设置到user对象当中
        user.setPassword(md5Password);


        //补全数据:is_delete设置为0 表示未删除
        user.setIsDelete(0);
        //补全数据:四个日志字段信息
        user.setCreatedUser(user.getUsername());
        user.setModifiedUser(user.getUsername());
        Date date = new Date();//java.util.Date
        user.setCreatedTime(date);
        user.setModifiedTime(date);


        //执行注册业务功能的实现
        Integer rows = userMapper.insert(user);
        if (rows != 1) {
            throw new InsertException("在用户注册过程中产生了未知的异常");
        }
    }

    /**
     * 定义一个md5算法的加密处理
     */
    //md5加密算法以后可能还要多次用到,
    // 为了方便在UserServiceImpl类里面单独写一个getMD5Password方法
    private String getMD5Password(String password, String salt) {
        for (int i = 0; i < 3; i++) {
            //md5加密算法方法的调用(进行三次加密)
            //.getBytes()是将前面的字符串转换成字节流
            //toUpperCase是转成大写
            password = DigestUtils.md5DigestAsHex((salt + password + salt).getBytes()).toUpperCase();
        }

        //返回加密之后的密码
        return password;
    }


    /**
     * 判断用户是否存在
     */
    @Override
    public User getUserById(Integer uid) {
        User result = userMapper.findByUid(uid);
        if (result == null || result.getIsDelete() == 1) {
            throw new UserNotFoundException("用户数据不存在");
        }
        return result;
    }

    /**
     * 用户登录功能
     */
    @Override
    public User login(String username, String password) {
        /** 根据用户名称来查询用户的数据是否存在,不存在则抛出异常*/
        User result = userMapper.findByUsername(username);
        if (result == null) {
            throw new UserNotFoundException("用户" + username + "不存在");
        }


        //检测用户的密码是否匹配:
        //1.先获取数据库中加密之后的密码
        String oldPassword = result.getPassword();
        //2.和用户传递过来的密码进行比较
        //2.1先获取盐值
        String salt = result.getSalt();
        //2.2将获取的用户密码按照相同的md5算法加密
        String newMD5Password = getMD5Password(password, salt);
        //3.将密码进行比较
        if (!oldPassword.equals(newMD5Password)) {
            throw new PasswordNotMatchException("用户密码错误");
        }

        //判断is_delete字段的值是否为1，1就是表示被标记为删除
        if (result.getIsDelete() == 1) {
            throw new UserNotFoundException("用户" + username + "不存在");
        }


        //方法login返回的用户数据是为了辅助其他页面做数据展示使用(只会用到uid,username,avatar)
        //所以可以new一个新的user只赋这三个变量的值,这样使层与层之间传输时数据体量变小,后台层与
        // 层之间传输时数据量越小性能越高,前端也是的,数据量小了前端响应速度就变快了
        User user = new User();
        user.setUid(result.getUid());
        user.setUsername(result.getUsername());
        user.setAvatar(result.getAvatar());
        return user;
    }


    /**
     * 修改密码功能
     */
    @Override
    public void changePassword(Integer uid,
                               String username,
                               String oldPassword,
                               String newPassword) {

        User result = getUserById(uid);

        //原始密码和数据库中密码进行比较
        String oldMd5Password = getMD5Password(oldPassword, result.getSalt());
        if (!oldMd5Password.equals(result.getPassword())) {
            throw new PasswordNotMatchException("原始密码输入不正确");
        }

        //将新的密码设置到数据库中，将新的密码进行加密再去更新
        String newMd5Password = getMD5Password(newPassword, result.getSalt());
        Integer rows = userMapper.updatePasswordByUid(uid, newMd5Password, username, new Date());

        if (rows != 1) {
            throw new UpdateException("更新数据产生未知的异常");
        }
    }


    /**
     * 获取用户基本信息
     */
    @Override
    public User getInfoByUid(Integer uid) {
        User result = getUserById(uid);

        User user = new User();
        user.setPhone(result.getPhone());
        user.setEmail(result.getEmail());
        user.setGender(result.getGender());
        user.setUsername(result.getUsername());

        return user;
    }

    /**
     * 更新用户基本信息
     * <p>
     * User对象中的数据只有phone,email,gender,username,
     * 因为springboot进行依赖注入的时候只注入表单中数据的值,所以需要手动将uid封装到user中
     */
    @Override
    public void changeInfo(Integer uid, User user) {
        User result = getUserById(uid);

        user.setUid(uid);
        user.setModifiedUser(result.getUsername());
        user.setModifiedTime(new Date());

        Integer rows = userMapper.updateInfoByUid(user);
        if (rows != 1) {
            throw new UpdateException("更新数据时产生异常");
        }

    }

    /**
     * 更新用户的头像
     */
    @Override
    public void updateAvatarByUid(Integer uid, String avatar, String modifiedUser) {
        //查询当前用户是否存在
        getUserById(uid);

        Integer rows = userMapper.updateAvatarByUid(uid, avatar, modifiedUser, new Date());
        if(rows != 1){
            throw new UpdateException("更新用户头像时产生未知的异常");
        }
    }

}

