package com.cy.store.controller;

import com.cy.store.controller.fileEx.*;
import com.cy.store.service.ex.*;
import com.cy.store.util.JsonResult;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpSession;

/**
 * 控制层类的基类
 *
 * @author 魏敏捷
 * @version 1.0
 */
public class BaseController {

    //操作成功的状态码
    public static final int OK = 200;

    /**
     * 1.@ExceptionHandler表示该方法用于统一处理捕获抛出的异常
     * 2.什么样的异常才会被这个方法处理呢?
     * 需要是ServiceException.class,这样的话只要是抛出ServiceException异常就会被拦截到handleException方法,
     * 此时handleException方法就是请求处理方法,返回值就是需要传递给前端的数据
     * 3.被ExceptionHandler修饰后如果项目发生异常,那么异常对象就会被自动传递给此方法的参数列表上,所以形参就需要写Throwable e用来接收异常对象
     */
    @ExceptionHandler({ServiceException.class, FileUploadException.class})
    public JsonResult<Void> handleException(Throwable e) {
        //创建响应结果对象即JsonResult对象
        JsonResult<Void> result = new JsonResult<>(e);
        if (e instanceof UsernameDuplicatedException) {
            result.setState(4000);
            result.setMessage("用户名已经被占用");
        } else if (e instanceof UserNotFoundException) {
            result.setState(4001);
            result.setMessage("用户数据不存在的异常");
        } else if (e instanceof PasswordNotMatchException) {
            result.setState(4002);
            result.setMessage("用户密码输入错误的异常");
        } else if (e instanceof AddressCountLimitException) {
            result.setState(4003);
            result.setMessage("用户的收货地址超出上限的异常");
        } else if (e instanceof AddressNotFoundException) {
            result.setState(4004);
            result.setMessage("用户的收货地址数据不存在的异常");
        } else if (e instanceof AccessDeniedException) {
            result.setState(4005);
            result.setMessage("收货地址数据非法访问的异常");
        } else if (e instanceof ProductNotFoundException) {
            result.setState(4006);
            result.setMessage("访问的商品数据不存在的异常");
        } else if (e instanceof CartNotFoundException) {
            result.setState(4007);
            result.setMessage("购物车表不存在该商品的异常");
        } else if (e instanceof InsertException) {
            result.setState(5000);
            result.setMessage("插入数据时产生未知的异常");
        } else if (e instanceof UpdateException) {
            result.setState(5001);
            result.setMessage("更新数据时产生未知的异常");
        } else if (e instanceof DeleteException) {
            result.setState(5002);
            result.setMessage("删除数据时产生未知的异常");
        } else if (e instanceof UpdateAddressException) {
            result.setState(5003);
            result.setMessage("修改地址时产生的异常");
        } else if (e instanceof FileEmptyException) {
            result.setState(6000);
            result.setMessage("上传的文件时文件为空的异常");
        } else if (e instanceof FileSizeException) {
            result.setState(6001);
            result.setMessage("上传的文件时文件大小超出限制的异常");
        } else if (e instanceof FileTypeException) {
            result.setState(6002);
            result.setMessage("上传的文件时文件类型异常");
        } else if (e instanceof FileStateException) {
            result.setState(6003);
            result.setMessage("上传的文件时文件状态异常");
        } else if (e instanceof FileUploadIOException) {
            result.setState(6004);
            result.setMessage("上传的文件时文件读写异常");
        }

        return result;
    }


    /**
     * 获取session对象中的uid
     *
     * @param session session对象
     * @return 当前登录的用户uid的值
     */
    protected final Integer getUidFromSession(HttpSession session) {
        //getAttribute返回的是Object对象,需要转换为字符串再转换为包装类
        return Integer.valueOf(session.getAttribute("uid").toString());
    }

    /**
     * 获取当前登录用户的username
     *
     * @param session session对象
     * @return 当前登录用户的用户名
     * 在实现类中重写父类中的toString()，不是句柄信息的输出
     */
    protected final String getUsernameFromSession(HttpSession session) {
        return session.getAttribute("username").toString();
    }
}

