/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.zhou.plus.framework.utils;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.SessionIdGenerator;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * 会话Id生成
 * @author ThinkGem
 * @version 2013-01-15
 */
@Component
@Lazy(false)
public class IdGen implements SessionIdGenerator {

	@Override
	public Serializable generateId(Session session) {
		return IdUtils.randomUUID();
	}
	
}
