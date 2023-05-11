package com.cy.store.service;

import com.cy.store.entity.User;

/**
 * 用户模块业务层接口
 * @author 魏敏捷
 * @version 1.0
 */
public interface IUserService {
    /**
     * 用户注册功能
     * @param user 用户的数据对象
     */
    void reg(User user);


    /**
     * 用户登录功能
     * @param username 用户名
     * @param password 密码
     * @return 当前匹配的用户数据，如果没有则返回null值
     */
    User login(String username, String password);


    /**
     * 用户修改密码
     * @param uid 用户uid
     * @param username 用户名
     * @param oldPassword 用户老密码
     * @param newPassword 用户新密码
     */
    void changePassword(Integer uid,
                        String username,
                        String oldPassword,
                        String newPassword);


    /**
     * 根据用户的id查询用户的数据
     * @param uid 用户的id
     * @return 如果找到则返回对象,反之返回null值
     */
    User getInfoByUid(Integer uid);


    /**
     * uid通过控制层在session中获取然后传递给业务层,并在业务层封装到User对象中
     * @param uid
     * @param user
     */
    void changeInfo(Integer uid,User user);


    /**
     * 判断用户是否存在
     * @param uid
     * @return 如果找到则返回对象,反之返回null值
     */
    User getUserById(Integer uid);


    /**
     * 更新用户头像
     * @param uid 用户id
     * @param avatar 用户头像的路径
     * @param modifiedUser 修改者
     */
    void updateAvatarByUid(Integer uid, String avatar, String modifiedUser);

}

