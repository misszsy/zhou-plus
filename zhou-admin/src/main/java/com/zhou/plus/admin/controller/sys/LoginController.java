package com.zhou.plus.admin.controller.sys;


import com.zhou.plus.busi.common.utils.CommonUtils;
import com.zhou.plus.busi.common.utils.UserUtils;
import com.zhou.plus.framework.resp.R;
import com.zhou.plus.framework.security.Principal;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;


/**
 * 登录Controller
 * @author bone
 * @version 2017-07-27
 */
@Controller
@Api(value = "LoginController",description = "系统登录相关api")
public class LoginController {

	/**
	 * 日志对象
	 */
	protected Logger logger = LoggerFactory.getLogger(getClass());


	/**
	 * 前后端分离，跳转到前端的主页
	 */
	@GetMapping("/index")
    @RequiresPermissions("user")
	public String index() {
		return "index";
	}


    /**
     * 前后端分离，跳转到前端的主页
     */
    @GetMapping(value = "/index_v3")
    public String index_v3() {
        return "/index_v3";
    }

    /**
     * 管理登录
     */
    @GetMapping(value = "/login")
    public String loginPage( ) {
        Principal principal = UserUtils.getPrincipal();

        //获取用户信息
        if(principal != null){
            return "redirect:"+"/index";
        }
        return "login";
    }

    /**
     * 登录失败，真正登录的POST请求由Filter完成
     */
    @PostMapping(value = "/login")
    @ApiOperation(value = "系统登录",notes = "根据用户名和密码登录",httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username", value = "用户名称",required = true, dataType = "String"),
            @ApiImplicitParam(name = "password", value = "登录密码",required = true, dataType = "String")
    })
    public @ResponseBody
    R loginHandle(HttpServletRequest request) {
        Object user = CommonUtils.getPrincipal();

        //获取用户信息
        if(user != null){
            return R.ok();
        }
        String message = (String) request.getAttribute("message");
        request.removeAttribute("message");

        return R.fail(message);
    }
}
