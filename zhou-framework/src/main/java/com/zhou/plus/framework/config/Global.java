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

	/**
	 * 获取项目工程路径
	 * @return
	 */
	public static String getProjectPath(){
    	// 如果配置了工程路径，则直接返回，否则自动获取。
		String projectPath = Global.getConfig("projectPath");
		if (StringUtils.isNotBlank(projectPath)){
			return projectPath;
		}
		try {
			File file = new DefaultResourceLoader().getResource("").getFile();
			if (file != null){
				while(true){
					File f = new File(file.getPath() + File.separator + "src" + File.separator + "main");
					if (f == null || f.exists()){
						break;
					}
					if (file.getParentFile() != null){
						file = file.getParentFile();
					}else{
						break;
					}
				}
				projectPath = file.toString();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return projectPath;
    }


	/**
	 * 获取上传文件的根目录
	 * @return
	 */
	public static String getUserfilesBaseDir() {
		//String dir = getConfig("userfiles.basedir");
		String dir = getConfig("fileUploadDownloadPath");
		if (StringUtils.isBlank(dir)){
			try {
				//dir = ServletContextFactory.getServletContext().getRealPath("/");
			} catch (Exception e) {
				return "";
			}
		}
		if(!dir.endsWith("/")) {
			dir += "/";
		}
		return dir;
	}


	/**
	 * 获取管理端根路径
	 */
	public static String getTerminalType() {
		return getConfig("terminalType");
	}
}
