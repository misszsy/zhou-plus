package com.zhou.plus.framework.config;


import com.zhou.plus.framework.utils.PropertiesLoader;
import com.zhou.plus.framework.utils.StringUtils;
import org.springframework.core.io.DefaultResourceLoader;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 全局配置属性读取工具类
 * 读取config.properties的配置信息
 * @author bone
 * @version 2017-07-25
 */
public class Global {

	/**
	 * 配置文件的路径，默认为classpath下的config.properties
	 */
	private static final String configPath =  "config.properties";

	/**
	 * 保存全局属性值
	 */
	private static Map<String, String> map = new HashMap<>();
	
	/**
	 * 属性文件加载对象
	 */
	private static PropertiesLoader loader = new PropertiesLoader(configPath);

	public static final String TURE = "1";

	public static final String FALSE = "0";

	private Global(){

	}

	/**
	 * 根据key获取配置的中的值
	 * @param key 键
	 * @return
	 */
	public static String getConfig(String key) {
		String value = map.get(key);
		if (value == null){
			value = loader.getProperty(key);
			map.put(key, value != null ? value : StringUtils.EMPTY);
		}
		return value;
	}
}
