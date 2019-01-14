package com.zhou.plus.admin.common.authc;

import com.zhou.plus.admin.common.utils.UserUtils;
import com.zhou.plus.framework.config.Global;
import com.zhou.plus.framework.utils.StringUtils;
import org.apache.shiro.session.SessionException;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * shiro退出拦截器
 */
public class LogoutFilter extends org.apache.shiro.web.filter.authc.LogoutFilter{

    private static final Logger logger = LoggerFactory.getLogger(LogoutFilter.class);

    @Override
    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
        Subject subject = getSubject(request, response);
        String redirectUrl = getRedirectUrl(request, response, subject);
        try {
            UserUtils.clearCache();
            subject.logout();
            return false;
        } catch (SessionException ise) {
            logger.debug("Encountered session exception during logout.  This can generally safely be ignored.", ise);
        }
        issueRedirect(request, response, redirectUrl);
        return false;
    }


    @Override
    protected String getRedirectUrl(ServletRequest request, ServletResponse response, Subject subject) {
        String url = "/login";
        // 如果配置了登出之后跳转的url，并且url不能为 ${adminPath}/logout 否则会造成死循环。
        if (StringUtils.isNoneBlank(url) && !url.equals(("/api/logout"))){
            return url;
        }
        return super.getRedirectUrl(request, response, subject);
    }
}
