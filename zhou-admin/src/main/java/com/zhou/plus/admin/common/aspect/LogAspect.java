package com.zhou.plus.admin.common.aspect;

import com.zhou.plus.admin.common.utils.UserUtils;
import com.zhou.plus.admin.modules.entity.SysLog;
import com.zhou.plus.admin.modules.service.SysLogService;
import com.zhou.plus.framework.annotation.Log;
import com.zhou.plus.framework.resp.R;
import com.zhou.plus.framework.utils.Servlets;
import com.zhou.plus.framework.utils.StringUtils;
import com.zhou.plus.framework.utils.UserAgentUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.time.LocalDateTime;

/**
 * 日志切面类
 * @author zhoushengyuan
 * @since 2018-12-03
 */
@Aspect
@Component
public class LogAspect {

    private static Logger logger= LoggerFactory.getLogger(LogAspect.class);

    @Autowired
    private SysLogService sysLogService;


    @Pointcut("@annotation(com.zhou.plus.framework.annotation.Log)")
    public void addLog() {

    }


    @Around("addLog()")
    public Object around(ProceedingJoinPoint point) {
        long beginTime = System.currentTimeMillis();
        //执行方法
        Object result = null;
        String type="1";
        try {
            result = point.proceed();
        } catch (Throwable e) {
            type="2";
            logger.error("异常错误",e);
        }
        R r=(R) result;

        if(r.getCode()==200){
            //执行时长(毫秒)
            long time = System.currentTimeMillis() - beginTime;
            //保存日志
            saveSysLog(point,time,type);
        }
        return result;
    }


    private void saveSysLog(ProceedingJoinPoint joinPoint, long time, String type) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();

        SysLog sysLog = new SysLog();
        Log log = method.getAnnotation(Log.class);
        if(log != null){
            //注解上的描述
            sysLog.setOperation(log.value());
        }

        //请求的方法名
        String className = joinPoint.getTarget().getClass().getName();
        String methodName = signature.getName();
        sysLog.setMethod(className + "." + methodName + "()");

        sysLog.setType(type);
        //获取request
        HttpServletRequest request = Servlets.getRequest();
        //设置IP地址
        sysLog.setRequestIp( StringUtils.getRemoteAddr(request));
        sysLog.setRequestUrl(request.getRequestURI());
        //用户名
        String userId = UserUtils.getUser().getId();
        sysLog.setUserId(userId);

        if(UserAgentUtils.isComputer(request)){
            sysLog.setEquipMent("PC");
        }else if(UserAgentUtils.isMobile(request)){
            sysLog.setEquipMent("mobile");
        }else{
            sysLog.setEquipMent("tablet");
        }
        sysLog.setOperationSystem(UserAgentUtils.getOperatingSystem(request).getName());
        sysLog.setBrowser(UserAgentUtils.getBrowser(request).getName());

        sysLog.setRespTime(time);
        sysLog.setCreateDate(LocalDateTime.now());
        //保存系统日志
        sysLogService.save(sysLog);
    }
}
