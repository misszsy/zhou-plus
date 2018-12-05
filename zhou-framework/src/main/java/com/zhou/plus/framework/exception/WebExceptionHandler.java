package com.zhou.plus.framework.exception;


import com.zhou.plus.framework.resp.R;
import com.zhou.plus.framework.resp.RespEnum;
import com.zhou.plus.framework.utils.Servlets;
import com.zhou.plus.framework.validator.BeanValidators;
import org.apache.commons.fileupload.FileUploadBase;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.UnauthorizedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;
import java.util.List;

@ControllerAdvice
public class WebExceptionHandler {

    private static Logger logger = LoggerFactory.getLogger(WebExceptionHandler.class);

    /**
     * 上传文件大小异常捕获
     * @param e
     * @return
     */
    @ExceptionHandler(FileUploadBase.SizeLimitExceededException.class)
    @ResponseBody
    public R handleFileUploadBaseSizeLimitExceededException(FileUploadBase.SizeLimitExceededException e){
        return R.fail("文件大小不能超过2M");
    }

    /**
     * 上传文件大小异常捕获
     * @param e
     * @return
     */
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    @ResponseBody
    public R handleMaxUploadSizeExceededException(MaxUploadSizeExceededException e){
        return R.fail("文件大小不能超过2M");
    }

    /**
     * 业务处理异常捕获
     * @param e
     * @return
     */
    @ExceptionHandler(BusinessException.class)
    @ResponseBody
    public R handleBusinessException(BusinessException e){
        return R.fail(e.getMessage());
    }

    /**
     * 验证参数异常捕获
     * @param e
     * @return
     */
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseBody
    public R handleConstraintViolationException(ConstraintViolationException e){
        List<String> list = BeanValidators.extractPropertyAndMessageAsList(e,": ");
        return R.fail(addMessage(list.toArray(new String[]{})));
    }

    /**
     * 唯一索引异常捕获
     * @param e
     * @return
     */
    @ExceptionHandler(DuplicateKeyException.class)
    @ResponseBody
    public R DuplicateKeyExceptionException(DuplicateKeyException e){
        return  R.fail("手机号码已存在");
    }

    /**
     * 全局其它异常捕获
     * @param e
     * @return
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public R handleException(Exception e){
        return R.fail("系统异常，请稍后再试!");
    }

    /**
     * 权限异常
     */
    @ExceptionHandler({ UnauthorizedException.class, AuthorizationException.class })
    public ModelAndView authorizationException(HttpServletRequest request, HttpServletResponse response) {
        if(Servlets.isAjaxRequest(request)){
            Servlets.renderObject(response, R.fail(RespEnum.NOT_AUTH.getCode(),"没有操作权限！",null));
        }else{
            ModelAndView model = new ModelAndView();
            model.setViewName("error/403");
            return model;
        }
        return null;
    }

        /**
         * 添加验证参数错误消息
         *
         */
    protected String addMessage(String... messages) {
        StringBuilder sb = new StringBuilder();
        for (String message : messages) {
            sb.append(message).append(messages.length > 1 ? "<br/>" : StringUtils.EMPTY);
        }
        return sb.toString();
    }
}
