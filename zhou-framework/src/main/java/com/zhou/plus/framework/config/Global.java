package com.zhou.plus.framework.config;



/**
 * 全局配置属性读取工具类
 * 读取config.properties的配置信息
 * @author bone
 * @version 2017-07-25
 */
public class Global {

	public static final String TURE = "1";

	public static final String FALSE = "0";


	public static final String CACHE_DICT_MAP = "M_dictMap";

	public static final String CACHE_COLUMN_MAP = "M_columnMap";

	public static final String CACHE_COLUMN_CHILDREN_MAP = "M_childrenMap";

	/**
	 * 项目上传文件路径总路口
	 */
	public static final String PROJECT_FILE_ROOT_PATH ="/usr/local";

	/**
	 * 上传图片基础虚拟路径
	 */
	public static final String IMG_BASE_PATH ="/assets/images/";

	/**
	 * 上传百度编辑器基础虚拟路径
	 */
	public static final String IMG_BASE_EDITOR ="/assets/editor/";

	private Global(){

	}
}
