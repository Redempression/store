package com.cy.store.entity;

import java.util.Date;
import java.util.Objects;


/**
 * 订单数据的实体类
 *
 * oid INT AUTO_INCREMENT COMMENT '订单id',
 * 	uid INT NOT NULL COMMENT '用户id',
 * 	recv_name VARCHAR(20) NOT NULL COMMENT '收货人姓名',
 * 	recv_phone VARCHAR(20) COMMENT '收货人电话',
 * 	recv_province VARCHAR(15) COMMENT '收货人所在省',
 * 	recv_city VARCHAR(15) COMMENT '收货人所在市',
 * 	recv_area VARCHAR(15) COMMENT '收货人所在区',
 * 	recv_address VARCHAR(50) COMMENT '收货详细地址',
 * 	total_price BIGINT COMMENT '总价',
 * 	STATUS INT COMMENT '状态：0-未支付，1-已支付，2-已取消，3-已关闭，4-已完成',
 * 	order_time DATETIME COMMENT '下单时间',
 * 	pay_time DATETIME COMMENT '支付时间',
 *
 * @author 魏敏捷
 * @version 1.0
 */
public class Order extends BaseEntity {
    private Integer oid;
    private Integer uid;
    private String recvName;
    private String recvPhone;
    private String recvProvince;
    private String recvCity;
    private String recvArea;
    private String recvAddress;
    private Double totalPrice;
    private Integer status;
    private Date orderTime;
    private Date payTime;
    /**
     * get,set
     * equals和hashCode
     * toString
     */

    public Order() {
    }

    public Order(Integer oid, Integer uid, String recvName, String recvPhone, String recvProvince, String recvCity, String recvArea, String recvAddress, Double totalPrice, Integer status, Date orderTime, Date payTime) {
        this.oid = oid;
        this.uid = uid;
        this.recvName = recvName;
        this.recvPhone = recvPhone;
        this.recvProvince = recvProvince;
        this.recvCity = recvCity;
        this.recvArea = recvArea;
        this.recvAddress = recvAddress;
        this.totalPrice = totalPrice;
        this.status = status;
        this.orderTime = orderTime;
        this.payTime = payTime;
    }

    public Integer getOid() {
        return oid;
    }

    public void setOid(Integer oid) {
        this.oid = oid;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public String getRecvName() {
        return recvName;
    }

    public void setRecvName(String recvName) {
        this.recvName = recvName;
    }

    public String getRecvPhone() {
        return recvPhone;
    }

    public void setRecvPhone(String recvPhone) {
        this.recvPhone = recvPhone;
    }

    public String getRecvProvince() {
        return recvProvince;
    }

    public void setRecvProvince(String recvProvince) {
        this.recvProvince = recvProvince;
    }

    public String getRecvCity() {
        return recvCity;
    }

    public void setRecvCity(String recvCity) {
        this.recvCity = recvCity;
    }

    public String getRecvArea() {
        return recvArea;
    }

    public void setRecvArea(String recvArea) {
        this.recvArea = recvArea;
    }

    public String getRecvAddress() {
        return recvAddress;
    }

    public void setRecvAddress(String recvAddress) {
        this.recvAddress = recvAddress;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(Date orderTime) {
        this.orderTime = orderTime;
    }

    public Date getPayTime() {
        return payTime;
    }

    public void setPayTime(Date payTime) {
        this.payTime = payTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Order)) return false;
        if (!super.equals(o)) return false;
        Order order = (Order) o;
        return Objects.equals(getOid(), order.getOid()) &&
                Objects.equals(getUid(), order.getUid()) &&
                Objects.equals(getRecvName(), order.getRecvName()) &&
                Objects.equals(getRecvPhone(), order.getRecvPhone()) &&
                Objects.equals(getRecvProvince(), order.getRecvProvince()) &&
                Objects.equals(getRecvCity(), order.getRecvCity()) &&
                Objects.equals(getRecvArea(), order.getRecvArea()) &&
                Objects.equals(getRecvAddress(), order.getRecvAddress()) &&
                Objects.equals(getTotalPrice(), order.getTotalPrice()) &&
                Objects.equals(getStatus(), order.getStatus()) &&
                Objects.equals(getOrderTime(), order.getOrderTime()) &&
                Objects.equals(getPayTime(), order.getPayTime());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getOid(), getUid(), getRecvName(), getRecvPhone(), getRecvProvince(), getRecvCity(), getRecvArea(), getRecvAddress(), getTotalPrice(), getStatus(), getOrderTime(), getPayTime());
    }

    @Override
    public String toString() {
        return "Order{" +
                "oid=" + oid +
                ", uid=" + uid +
                ", recvName='" + recvName + '\'' +
                ", recvPhone='" + recvPhone + '\'' +
                ", recvProvince='" + recvProvince + '\'' +
                ", recvCity='" + recvCity + '\'' +
                ", recvArea='" + recvArea + '\'' +
                ", recvAddress='" + recvAddress + '\'' +
                ", totalPrice=" + totalPrice +
                ", status=" + status +
                ", orderTime=" + orderTime +
                ", payTime=" + payTime +
                '}';
    }
}

