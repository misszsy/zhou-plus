package com.zhou.plus.busi.common.utils;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zhou.plus.busi.entity.SysMenu;
import com.zhou.plus.busi.entity.SysRole;
import com.zhou.plus.busi.entity.SysUser;
import com.zhou.plus.busi.service.SysMenuService;
import com.zhou.plus.busi.service.SysRoleService;
import com.zhou.plus.busi.service.SysUserService;
import com.zhou.plus.framework.security.Principal;
import com.zhou.plus.framework.utils.SpringContextHolder;
import com.zhou.plus.framework.utils.StringUtils;
import org.springframework.util.CollectionUtils;

import java.util.*;


/**
 * 用户工具类
 * @author bone
 * @version 2017-07-27
 */
public class UserUtils extends CommonUtils{

	private static SysUserService sysUserService = SpringContextHolder.getBean(SysUserService.class);

	private static SysRoleService sysRoleService = SpringContextHolder.getBean(SysRoleService.class);

	private static SysMenuService sysMenuService = SpringContextHolder.getBean(SysMenuService.class);

	public static final String CACHE_AUTH_INFO = "authInfo";

	public static final String CACHE_ROLE_LIST = "sysRoleList";

	public static final String CACHE_MENU_LIST = "sysMenuList";

	public static final String USER_CACHE_ID_ = "id_";


	/**
	 * 根据ID获取用户
	 * @param id
	 * @return 取不到返回null
	 */
	public static SysUser get(String id){
		SysUser user = (SysUser) getCache(USER_CACHE_ID_ + id);
		if (user ==  null){
			user = sysUserService.getById(id);
			if (user == null){
				return null;
			}
			user.setRoleList(sysUserService.getRoleByUserId(user.getId()));
			putCache(USER_CACHE_ID_ + user.getId(), user);
		}
		return user;
	}

	/**
	 * 清除当前用户缓存
	 */
	public static void clearCache(){
		removeCache(CACHE_ROLE_LIST);
		removeCache(CACHE_MENU_LIST);
		clearCache(getUser());
	}


	/**
	 * 清除指定用户缓存
	 * @param user
	 */
	public static void clearCache(SysUser user){
		removeCache(USER_CACHE_ID_ + user.getId());
	}


	/**
	 * 获取当前用户
	 * @return 取不到返回 new Member()
	 */
	public static SysUser getUser(){
		Principal principal = getPrincipal();
		if (principal!=null){
			return Optional.ofNullable(get(principal.getId())).orElseGet(SysUser::new);
		}
		// 如果没有登录，则返回实例化空的User对象。
		return new SysUser();
	}


	/**
	 * 获取角色列表
	 * @return
	 */
	public static List<SysRole> getRoleList(){
		List<SysRole> roleList =(List<SysRole>) getCache(CACHE_ROLE_LIST);
		if (roleList==null){
			roleList = sysRoleService.list(null);
			putCache(CACHE_ROLE_LIST,roleList);
		}
		return roleList;
	}


	/**
	 * 获取系统登录菜单树
	 * @return
	 */
	public static List<SysMenu> getAuthMenuList() {
		List<SysMenu> menuList =(List<SysMenu>) getCache(CACHE_MENU_LIST);
		if(menuList==null){
			SysUser user=getUser();
			if(isAdmin(user.getId())){
				menuList = sysMenuService.list(new QueryWrapper<SysMenu>().lambda().ne(SysMenu::getType,"2")
																					.orderByAsc(SysMenu::getSort));
			}else{
				menuList = sysMenuService.getRoleMenuList(user.getId());
			}
			menuList=tree(menuList);
			putCache(CACHE_MENU_LIST, menuList);
		}
		return menuList;
	}

	/**
	 * 获取系统登录菜单树
	 * @return
	 */
	public static List<SysMenu> getAllMenuList() {
		List<SysMenu> menuList =sysMenuService.list(new QueryWrapper<SysMenu>().lambda().orderByAsc(SysMenu::getSort));
		return tree(menuList);
	}

	/**
	 * 获取获取当前用户的权限菜单标识
	 * @return
	 */
	public static List<String> getPermissionMenuList(String userId) {
		return sysMenuService.getPermissionMenuList(userId);
	}

	/**
	 * 获取系统登录菜单树
	 * @return
	 */
	public static boolean isAdmin(String userId) {
		String isAdmin=sysRoleService.getAdmin(userId);
		return StringUtils.isNotEmpty(isAdmin)&& isAdmin.equals("0000")?true:false;
	}

	/**
	 * 构建树
	 */
	private static List<SysMenu> tree(List<SysMenu> list){
		Map<String, SysMenu> map = new LinkedHashMap<>();
		for (SysMenu menu : list){
			map.put(menu.getId(), menu);
		}

		for (SysMenu menu : list){
			String parentId = menu.getParentId();

			if(menu.getParentId() == null || "0".equals(menu.getParentId())){
				continue;
			}

			SysMenu parent = map.get(parentId);
			List<SysMenu> childrenList = parent.getChildren();

			if(CollectionUtils.isEmpty(childrenList)){
				childrenList = new ArrayList<>();
				parent.setChildren(childrenList);
			}

			childrenList.add(menu);
		}

		List<SysMenu> firstLevel = new ArrayList<>();

		for (SysMenu menu : list){
			if(menu.getParentId() == null || "0".equals(menu.getParentId())){
				firstLevel.add(menu);
			}
		}

		return firstLevel;
	}
}
