package com.cy.store.service.ex;

/**
 * 修改地址时产生的异常
 * @author 魏敏捷
 * @version 1.0
 */
public class UpdateAddressException extends ServiceException{
    public UpdateAddressException() {
        super();
    }

    public UpdateAddressException(String message) {
        super(message);
    }

    public UpdateAddressException(String message, Throwable cause) {
        super(message, cause);
    }

    public UpdateAddressException(Throwable cause) {
        super(cause);
    }

    protected UpdateAddressException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
