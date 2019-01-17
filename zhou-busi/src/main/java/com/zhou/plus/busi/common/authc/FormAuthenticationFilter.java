package com.zhou.plus.busi.common.authc;

import com.zhou.plus.framework.resp.R;
import com.zhou.plus.framework.utils.Servlets;
import com.zhou.plus.framework.utils.StringUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 表单验证（包含验证码）过滤类
 */
public class FormAuthenticationFilter extends org.apache.shiro.web.filter.authc.FormAuthenticationFilter {

	private Logger logger = LoggerFactory.getLogger(getClass());

	public static final String DEFAULT_CAPTCHA_PARAM = "validateCode";

	protected AuthenticationToken createToken(ServletRequest request, ServletResponse response) {
		String mobile = getUsername(request);
		String password = getPassword(request);

		boolean rememberMe = isRememberMe(request);
		String host = StringUtils.getRemoteAddr((HttpServletRequest)request);

		return new UsernamePasswordToken(mobile, password.toCharArray(), rememberMe, host);
	}

	public String getCaptchaParam() {
		return DEFAULT_CAPTCHA_PARAM;
	}

	protected String getCaptcha(ServletRequest request) {
		return WebUtils.getCleanParam(request, getCaptchaParam());
	}

	/**
	 * 登录成功之后跳转URL
	 */
	public String getSuccessUrl() {
		return super.getSuccessUrl();
	}

	/**
	 * 登录失败调用事件
	 */
	@Override
	protected boolean onLoginFailure(AuthenticationToken token, AuthenticationException e, ServletRequest request, ServletResponse response) {
		String className = e.getClass().getName(),message="";
		if (IncorrectCredentialsException.class.getName().equals(className)
				|| UnknownAccountException.class.getName().equals(className)){
			message = "用户名或密码错误, 请重试.";
		}else if (AuthenticationException.class.getName().equals(className)){
			message = e.getMessage();
		}else{
			message = "服务器繁忙，请稍后再试！";
			logger.error(message, e);
			e.printStackTrace();
		}
        request.setAttribute(getFailureKeyAttribute(), className);
        request.setAttribute("message", message);
        return true;
	}


	/**
	 * 覆盖父类实现，让请求继续往下执行
	 * @param token
	 * @param subject
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@Override
	protected boolean onLoginSuccess(AuthenticationToken token, Subject subject,
                                     ServletRequest request, ServletResponse response) throws Exception {
		/*if("system".equals(ConfigConsts.getConfig("web.project"))){
			Member member= MemberUtils.getMember();
			if(member!=null){
				memberService.updateMemberLoginInfo(member);
			}
		}*/
		return true;
	}

	@Override
	protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
		if (isLoginRequest(request, response)) {
			if (isLoginSubmission(request, response)) {
				if (logger.isTraceEnabled()) {
					logger.trace("Login submission detected.  Attempting to execute login.");
				}
				return executeLogin(request, response);
			} else {
				if (logger.isTraceEnabled()) {
					logger.trace("Login page view.");
				}
				//allow them to see the login page ;)
				return true;
			}
		} else {
			if (logger.isTraceEnabled()) {
				logger.trace("Attempting to access a path which requires authentication.  Forwarding to the " +
						"Authentication url [" + getLoginUrl() + "]");
			}
			if (Servlets.isAjaxRequest((HttpServletRequest) request)) {
				Servlets.renderObject((HttpServletResponse)response, R.tokenError("您未登录或会话已失效，请重新登录！"));
			} else {
				saveRequestAndRedirectToLogin(request, response);
			}
			return false;
		}
	}
}