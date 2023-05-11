package com.cy.store.entity;

import java.util.Objects;

/**
 * 订单中的商品数据  即订单项的数据
 *
 * 针对该模块可以将t_order_item表和t_order表合并,
 * 但是以后可能开发某个模块可能单独用到t_order_item
 * (比如用户查看订单时只需要t_order_item表数据就可以实现)
 * 所以,建议这两个表分开创建
 *
 * id INT AUTO_INCREMENT COMMENT '订单中的商品记录的id',
 * 	oid INT NOT NULL COMMENT '所归属的订单的id',
 * 	pid INT NOT NULL COMMENT '商品的id',
 * 	title VARCHAR(100) NOT NULL COMMENT '商品标题',
 * 	image VARCHAR(500) COMMENT '商品图片',
 * 	price BIGINT COMMENT '商品价格',
 * 	num INT COMMENT '购买数量',
 *
 * @author 魏敏捷
 * @version 1.0
 */
public class OrderItem extends BaseEntity{

    private Integer id;
    private Integer oid;
    private Integer pid;
    private String title;
    private String image;
    private Long price;
    private Integer num;
    /**
     * get,set
     * equals和hashCode
     * toString
     */

    public OrderItem() {
    }

    public OrderItem(Integer id, Integer oid, Integer pid, String title, String image, Long price, Integer num) {
        this.id = id;
        this.oid = oid;
        this.pid = pid;
        this.title = title;
        this.image = image;
        this.price = price;
        this.num = num;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getOid() {
        return oid;
    }

    public void setOid(Integer oid) {
        this.oid = oid;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
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
        if (!(o instanceof OrderItem)) return false;
        if (!super.equals(o)) return false;
        OrderItem orderItem = (OrderItem) o;
        return Objects.equals(getId(), orderItem.getId()) &&
                Objects.equals(getOid(), orderItem.getOid()) &&
                Objects.equals(getPid(), orderItem.getPid()) &&
                Objects.equals(getTitle(), orderItem.getTitle()) &&
                Objects.equals(getImage(), orderItem.getImage()) &&
                Objects.equals(getPrice(), orderItem.getPrice()) &&
                Objects.equals(getNum(), orderItem.getNum());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getId(), getOid(), getPid(), getTitle(), getImage(), getPrice(), getNum());
    }


    @Override
    public String toString() {
        return "OrderItem{" +
                "id=" + id +
                ", oid=" + oid +
                ", pid=" + pid +
                ", title='" + title + '\'' +
                ", image='" + image + '\'' +
                ", price=" + price +
                ", num=" + num +
                '}';
    }
}
