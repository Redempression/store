package com.cy.store.util;

import java.io.Serializable;

/**
 *
 * 这里所有响应的结果都采用的是Json格式的数据
 * @author 魏敏捷
 * @version 1.0
 */
public class JsonResult<E> implements Serializable {

    /** 响应的状态码*/
    private Integer state;
    /** 描述信息*/
    private String message;
    /** 对应的数据(数据类型不确定，用泛型)*/
    //数据类型不确定,用E表示任何的数据类型,一个类里如果声明的有泛型的数据类型,类也要声明为泛型
    private E data;

    //无参构造
    public JsonResult() {
    }

    //将状态码传给构造方法初始化对象
    public JsonResult(Integer state) {
        this.state = state;
    }


    //将状态码和数据传给构造方法初始化对象
    public JsonResult(Integer state, E data) {
        this.state = state;
        this.data = data;
    }

    //如果有异常,直接将异常传递给构造方法初始化对象
    public JsonResult(Throwable e) {
        this.message=e.getMessage();
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public E getData() {
        return data;
    }

    public void setData(E data) {
        this.data = data;
    }
}
