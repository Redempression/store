package com.cy.store.entity;

import java.util.Objects;

/**
 * 购物车数据的实体类
 *
 * cid INT AUTO_INCREMENT COMMENT '购物车数据id',
 * 	uid INT NOT NULL COMMENT '用户id',
 * 	pid INT NOT NULL COMMENT '商品id',
 * 	price BIGINT COMMENT '加入时商品单价',
 * 	num INT COMMENT '商品数量',
 * @author 魏敏捷
 * @version 1.0
 */
public class Cart extends BaseEntity {
    private Integer cid;
    private Integer uid;
    private Integer pid;
    private Long price;
    private Integer num;
/**
 * get,set
 * equals和hashCode
 * toString
 */
    public Cart() {
    }

    public Cart(Integer cid, Integer uid, Integer pid, Long price, Integer num) {
        this.cid = cid;
        this.uid = uid;
        this.pid = pid;
        this.price = price;
        this.num = num;
    }

    public Integer getCid() {
        return cid;
    }

    public void setCid(Integer cid) {
        this.cid = cid;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Cart)) return false;
        if (!super.equals(o)) return false;
        Cart cart = (Cart) o;
        return Objects.equals(getCid(), cart.getCid()) &&
                Objects.equals(getUid(), cart.getUid()) &&
                Objects.equals(getPid(), cart.getPid()) &&
                Objects.equals(getPrice(), cart.getPrice()) &&
                Objects.equals(getNum(), cart.getNum());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getCid(), getUid(), getPid(), getPrice(), getNum());
    }

    @Override
    public String toString() {
        return "Cart{" +
                "cid=" + cid +
                ", uid=" + uid +
                ", pid=" + pid +
                ", price=" + price +
                ", num=" + num +
                '}';
    }
}

