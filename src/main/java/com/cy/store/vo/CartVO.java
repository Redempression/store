package com.cy.store.vo;


import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

/**
 *    VO全称Value Object,值对象。当进行select查询时,查询的结果
 * 属于多张表中的内容,此时发现结果集不能直接使用某个POJO实体类来接
 * 收,因为POJO实体类不能包含多表查询出来的信息,解决方式是:重新去构
 * 建一个新的对象,这个对象用于存储所查询出来的结果集对应的映射,所以
 * 把这个对象称之为值对象.
 *
 * 购物车数据的Value Object类
 * @author 魏敏捷
 * @version 1.0
 */
public class CartVO implements Serializable {

    /**
     *  cid, #日后勾选购物车商品模块需要用到cid来确定勾选的是购物车表的哪一条数据
     * 	uid,
     * 	pid, #日后提交订单模块需要用到pid来确定购买的是商品表的哪件商品,然后对商品表的该商品的库存,销售热度等信息进行修改
     * 	t_cart.price, #两个表都有该字段,需要指定获取的是哪个数据表的
     * 	t_cart.num, #两个表都有该字段且含义不同,需要指定获取的是哪个数据表的
     * 	title,
     * 	t_product.price as realPrice, #为了在购物车列表页展示两个价格的差值
     * 	image
     */
    private Integer cid;        //购物车id
    private Integer uid;        //用户id
    private Integer pid;        //该商品id
    private Long price;         //该商品加入购物车时的价格，可能在原价格上进行打折等
    private Integer num;        //该商品在购物车里的数量
    private String title;       //该商品的全称
    private Long realPrice;     //该商品的原价格
    private String image;       //该商品的图片

    private Double xj;          //购物车项的价钱
    private Double totalMoney;  //计算购物清单所需的价钱


    public CartVO() {
    }

    public CartVO(Integer cid, Integer uid, Integer pid, Long price, Integer num, String title, Long realPrice, String image, Double xj, Double totalMoney) {
        this.cid = cid;
        this.uid = uid;
        this.pid = pid;
        this.price = price;
        this.num = num;
        this.title = title;
        this.realPrice = realPrice;
        this.image = image;
        this.xj = xj;
        this.totalMoney = totalMoney;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getRealPrice() {
        return realPrice;
    }

    public void setRealPrice(Long realPrice) {
        this.realPrice = realPrice;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Double getXj() {
        //商品单价
        BigDecimal bigDecimalPrice = new BigDecimal("" + price);
        //选定该商品的数量
        BigDecimal bigDecimalBuyCount = new BigDecimal("" + num);

        //BigDecimal里的multiply()方法是两个相乘
        BigDecimal bigDecimalXJ = bigDecimalBuyCount.multiply(bigDecimalPrice);
        xj = bigDecimalXJ.doubleValue();
        return xj;
    }

    public Double getTotalMoney(List<CartVO> cartVOList) {
        totalMoney=0.0;
        for (CartVO cartVO : cartVOList) {
            totalMoney += cartVO.getXj();
        }
        return totalMoney;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CartVO)) return false;
        CartVO cartVO = (CartVO) o;
        return Objects.equals(getCid(), cartVO.getCid()) &&
                Objects.equals(getUid(), cartVO.getUid()) &&
                Objects.equals(getPid(), cartVO.getPid()) &&
                Objects.equals(getPrice(), cartVO.getPrice()) &&
                Objects.equals(getNum(), cartVO.getNum()) &&
                Objects.equals(getTitle(), cartVO.getTitle()) &&
                Objects.equals(getRealPrice(), cartVO.getRealPrice()) &&
                Objects.equals(getImage(), cartVO.getImage());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCid(), getUid(), getPid(), getPrice(), getNum(), getTitle(), getRealPrice(), getImage());
    }

    @Override
    public String toString() {
        return "CartVO{" +
                "cid=" + cid +
                ", uid=" + uid +
                ", pid=" + pid +
                ", price=" + price +
                ", num=" + num +
                ", title='" + title + '\'' +
                ", realPrice=" + realPrice +
                ", image='" + image + '\'' +
                '}';
    }


    /**
     * get,set
     * equals和hashCode
     * toString
    */


}

