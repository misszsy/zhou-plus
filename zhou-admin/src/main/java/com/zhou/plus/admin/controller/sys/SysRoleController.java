package com.zhou.plus.admin.controller.sys;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zhou.plus.admin.common.controller.BaseController;
import com.zhou.plus.admin.common.utils.UserUtils;
import com.zhou.plus.admin.modules.entity.SysRole;
import com.zhou.plus.admin.modules.service.SysRoleService;
import com.zhou.plus.framework.annotation.Log;
import com.zhou.plus.framework.resp.R;
import com.zhou.plus.framework.utils.StringUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zhoushengyuan
 * @since 2018-11-13
 */
@Controller
@RequestMapping("/sys/role")
@Api(value = "SysRoleController",description = "系统角色相关api")
public class SysRoleController extends BaseController<SysRoleService, SysRole> {


    public String getViewPath() {
        return "sys/role/";
    }


   /**
    *
    * 页面跳转
    */
    @GetMapping("list")
    @RequiresPermissions("sys:role:view")
    public String listView() {
        return getViewPath() + "list";
    }

    /**
     * 分页查询角色列表
     * @param sysRole
     * @param pageNum
     * @param pageSize
     * @return
     */
    @GetMapping("listData")
    @ApiOperation(value = "角色列表",notes = "查询系统角色列表",httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sysRole", value = "系统角色详细信息",required = true, paramType = "query", dataType = "SysRole"),
            @ApiImplicitParam(name = "pageNum", value = "当前页码",required = true, paramType = "query", dataType = "Integer"),
            @ApiImplicitParam(name = "pageSize", value = "显示条数",required = true, paramType = "query", dataType = "Integer")
    })
    public @ResponseBody
    R listData(SysRole sysRole, Integer pageNum, Integer pageSize) {
        QueryWrapper wrapper=new QueryWrapper<SysRole>()
                .like(StringUtils.isNotEmpty(sysRole.getName()),"name",sysRole.getName());
        return super.listData(wrapper,pageNum,pageSize);
    }

    /**
     * 获取用户id角色列表
     * @return
     */
    @GetMapping("getRoleList")
    public @ResponseBody
    R getRoleList() {
        List<SysRole> roleList = UserUtils.getRoleList();
        return R.ok(roleList);
    }

    /**
     * 新增角色信息
     * @param role
     * @return
     */
    @Log(value = "新增角色")
    @PostMapping("save")
    @RequiresPermissions("sys:role:save")
    public @ResponseBody
    R save(SysRole role) {
        beanValidator(role);

        SysRole sysRole=baseService.getOne(new QueryWrapper<SysRole>().lambda().eq(SysRole::getCode,role.getCode()));
        if(sysRole!=null){
            return R.fail("新增失败,角色编码已存在");
        }
        baseService.save(role);
        return  R.ok(get(role.getId()));
    }

    /**
     * 更新角色信息
     * @param role
     * @return
     */
    @Log(value = "更新角色")
    @PostMapping("update")
    @RequiresPermissions("sys:role:update")
    public @ResponseBody
    R update(SysRole role) {
        beanValidator(role);
        return  super.update(role);
    }

    /**
     * 根据id删除
     * @param id
     * @return
     */
    @Log(value = "删除角色")
    @PostMapping("remove/{id}")
    @RequiresPermissions("sys:role:remove")
    @ApiOperation(value = "删除角色",notes = "根据角色id删除角色信息",httpMethod = "POST")
    @ApiImplicitParam(name = "id", value = "角色id",required = true, dataType = "String")
    public @ResponseBody
    R remove(@PathVariable("id") String id) {
        return super.remove(id);
    }

    /**
     * 根据id获取信息
     * @param id
     * @return
     */
    @GetMapping("get/{id}")
    @RequiresPermissions("sys:role:update")
    @ApiOperation(value = "获取角色",notes = "根据角色id获取角色信息",httpMethod = "GET")
    @ApiImplicitParam(name = "id", value = "角色id",required = true, dataType = "String")
    public @ResponseBody
    R get(@PathVariable("id") String id) {
        return super.get(id);
    }

    /**
     * Ajax权限检验
     * @return
     */
    @GetMapping("checkPermission")
    @RequiresPermissions(value = { "sys:role:save", "sys:role:edit"}, logical = Logical.OR)
    public @ResponseBody
    R checkPermission() {
        return R.ok();
    }
}
