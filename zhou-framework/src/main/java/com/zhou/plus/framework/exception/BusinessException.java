package com.zhou.plus.framework.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 业务异常
 */
public class BusinessException extends  RuntimeException{
    private static final long serialVersionUID = 9179464151158474134L;

    protected Logger logger = LoggerFactory.getLogger(getClass());

    public BusinessException(String message){
        super(message);
    }

    public BusinessException(String message, Throwable cause){
        super(message, cause);
    }

    public BusinessException(Throwable cause){
        super(cause);
    }

    public BusinessException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace){
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
