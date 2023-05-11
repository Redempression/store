package com.cy.store.service.ex;

/**
 * 业务层异常基类
 * 由于业务层异常是在程序运行的过程才会产生，不运行不会产生，所以继承RuntimeException
 *  RuntimeException：运行时异常，在项目运行之后出错则直接终止运行，异常由JVM虚拟机处理。
 * @author 魏敏捷
 * @version 1.0
 */
/**
 * 因为整个业务的异常只有一种情况下才会产生:只有运行时才会产生,不运行不会产生
 * 所以要求业务层的异常都要继承运行时异常RuntimeException并且重写父类的所有构造方法以便后期能抛出自已定义的异常
 */
public class ServiceException extends RuntimeException{
    public ServiceException() {
        super();
    }

    public ServiceException(String message) {
        super(message);
    }

    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public ServiceException(Throwable cause) {
        super(cause);
    }

    protected ServiceException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

