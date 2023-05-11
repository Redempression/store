package com.cy.store.entity;


import java.util.Objects;

/**
 *
 * uid INT AUTO_INCREMENT COMMENT '用户id',
 * 	username VARCHAR(20) NOT NULL UNIQUE COMMENT '用户名',
 * 	`password` CHAR(32) NOT NULL COMMENT '密码',
 * 	salt CHAR(36) COMMENT '盐值',
 * 	phone VARCHAR(20) COMMENT '电话号码',
 * 	email VARCHAR(30) COMMENT '电子邮箱',
 * 	gender INT COMMENT '性别:0-女，1-男',
 * 	avatar VARCHAR(50) COMMENT '头像',
 * 	is_delete INT COMMENT '是否删除：0-未删除，1-已删除',
 *
 * @author 魏敏捷
 * @version 1.0
 */
// @Data
public class User extends BaseEntity {
    private Integer uid;
    private String username;
    private String password;
    private String salt;
    private String phone;
    private String email;
    private Integer gender;
    private String avatar;
    private Integer isDelete;
/**
 * get,set
 * equals和hashCode
 * toString
 */

    public User() {
    }

    public User(Integer uid, String username, String password, String salt, String phone, String email, Integer gender, String avatar, Integer isDelete) {
        this.uid = uid;
        this.username = username;
        this.password = password;
        this.salt = salt;
        this.phone = phone;
        this.email = email;
        this.gender = gender;
        this.avatar = avatar;
        this.isDelete = isDelete;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Integer getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        if (!super.equals(o)) return false;
        User user = (User) o;
        return Objects.equals(getUid(), user.getUid()) &&
                Objects.equals(getUsername(), user.getUsername()) &&
                Objects.equals(getPassword(), user.getPassword()) &&
                Objects.equals(getSalt(), user.getSalt()) &&
                Objects.equals(getPhone(), user.getPhone()) &&
                Objects.equals(getEmail(), user.getEmail()) &&
                Objects.equals(getGender(), user.getGender()) &&
                Objects.equals(getAvatar(), user.getAvatar()) &&
                Objects.equals(getIsDelete(), user.getIsDelete());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getUid(), getUsername(), getPassword(), getSalt(), getPhone(), getEmail(), getGender(), getAvatar(), getIsDelete());
    }

    @Override
    public String toString() {
        return "User{" +
                "uid=" + uid +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", salt='" + salt + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", gender=" + gender +
                ", avatar='" + avatar + '\'' +
                ", isDelete=" + isDelete +
                '}';
    }
}
