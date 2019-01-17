package com.zhou.plus.admin.controller.sys;


import com.zhou.plus.busi.common.controller.BaseController;
import com.zhou.plus.busi.common.utils.UserUtils;
import com.zhou.plus.busi.entity.SysMenu;
import com.zhou.plus.busi.service.SysMenuService;
import com.zhou.plus.framework.annotation.Log;
import com.zhou.plus.framework.resp.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zhoushengyuan
 * @since 2018-11-12
 */
@Controller
@RequestMapping("/sys/menu")
@Api(value = "SysMenuController",description = "系统菜单相关api")
public class SysMenuController extends BaseController<SysMenuService, SysMenu> {

    public String getViewPath() {
        return "sys/menu/";
    }


    @GetMapping("list")
    @RequiresPermissions("sys:menu:view")
    public String listView() {
        return getViewPath()+"list";
    }


    @GetMapping("listTree")
    public String listTree() {
        return getViewPath() + "listTree";
    }

    /**
     * 获取系统菜单
     * @return
     */
    @GetMapping("getMenuTree")
    public @ResponseBody
    R getMenuTree() {
        return R.ok(UserUtils.getAuthMenuList());
    }

    /**
     * 获取角色授权菜单
     * @return
     */
    @GetMapping("getAuthMenuTree")
    public @ResponseBody
    R getAuthMenuTree() {
        return R.ok(UserUtils.getAllMenuList());
    }

    /**
     * 新增菜单
     * @param obj
     * @return
     */
    @Log(value = "新增菜单")
    @PostMapping("save")
    @RequiresPermissions("sys:menu:save")
    public @ResponseBody
    R save(SysMenu obj) {
        beanValidator(obj);
        baseService.save(obj);
        UserUtils.removeCache(UserUtils.CACHE_MENU_LIST);
        return  R.ok(get(obj.getId()));
    }

    /**
     * 更新菜单
     * @param entity
     * @return
     */
    @Log(value = "更新菜单")
    @PostMapping("update")
    @RequiresPermissions("sys:menu:update")
    public @ResponseBody
    R update(SysMenu entity) {
        beanValidator(entity);
        baseService.updateById(entity);
        UserUtils.removeCache(UserUtils.CACHE_MENU_LIST);
        return  R.ok(get(entity.getId()));
    }

    /**
     * 删除菜单
     * @param id
     * @return
     */
    @Log(value = "删除菜单")
    @PostMapping(value = {"remove/{id}"})
    @RequiresPermissions("sys:menu:remove")
    @ApiOperation(value = "删除菜单",notes = "根据菜单id删除菜单信息",httpMethod = "POST")
    @ApiImplicitParam(name = "id", value = "菜单id",required = true, dataType = "String")
    public @ResponseBody
    R remove(@PathVariable String id) {
        baseService.removeById(id);
        UserUtils.removeCache(UserUtils.CACHE_MENU_LIST);
        return R.ok();
    }

    /**
     * 根据id获取信息
     * @param id
     * @return
     */
    @GetMapping(value = {"get/{id}"})
    @RequiresPermissions("sys:menu:update")
    @ApiOperation(value = "获取菜单",notes = "根据菜单id获取菜单信息",httpMethod = "GET")
    @ApiImplicitParam(name = "id", value = "菜单id",required = true, dataType = "String")
    public @ResponseBody
    R get(@PathVariable("id") String id) {
        return super.get(id);
    }

    /**
     * Ajax权限检验
     * @return
     */
    @GetMapping("checkPermission")
    @RequiresPermissions(value = { "sys:menu:save", "sys:menu:update"}, logical = Logical.OR)
    public @ResponseBody
    R checkPermission() {
        return R.ok();
    }
}
