package com.zhou.plus.admin.controller.sys;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zhou.plus.admin.common.controller.BaseController;
import com.zhou.plus.admin.modules.entity.SysDict;
import com.zhou.plus.admin.modules.service.SysDictService;
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

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zhoushengyuan
 * @since 2018-11-17
 */
@Controller
@RequestMapping("/sys/dict")
@Api(value = "SysDictController",description = "系统字典相关api")
public class SysDictController extends BaseController<SysDictService, SysDict> {


    public String getViewPath() {
        return "sys/dict/";
    }


   /**
    *
    * 页面跳转
    */
    @GetMapping("list")
    @RequiresPermissions("sys:dict:view")
    public String listView() {
        return getViewPath() + "list";
    }

    /**
    * 分页查询列表
    * @param sysDict
    * @param pageNum
    * @param pageSize
    * @return
    */
    @GetMapping("listData")
    @ApiOperation(value = "字典列表",notes = "查询系统字典列表",httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sysDict", value = "系统字典详细信息",required = true, paramType = "query", dataType = "SysDict"),
            @ApiImplicitParam(name = "pageNum", value = "当前页码",required = true, paramType = "query", dataType = "Integer"),
            @ApiImplicitParam(name = "pageSize", value = "显示条数",required = true, paramType = "query", dataType = "Integer")
    })
    public @ResponseBody R listData(SysDict sysDict, Integer pageNum, Integer pageSize) {
        QueryWrapper wrapper=new QueryWrapper<SysDict>()
                .eq(StringUtils.isNotEmpty(sysDict.getType()),"type",sysDict.getType());
        return super.listData(wrapper,pageNum,pageSize);
    }

    /**
    * 新增
    * @param sysDict
    * @return
    */
    @Log(value = "新增字典")
    @PostMapping("save")
    @RequiresPermissions("sys:dict:save")
    public @ResponseBody
    R save(SysDict sysDict) {
        beanValidator(sysDict);
        return super.save(sysDict);
    }

    /**
    * 更新
    * @return
    */
    @Log(value = "更新字典")
    @PostMapping("update")
    @RequiresPermissions("sys:dict:update")
    public @ResponseBody
    R update(SysDict sysDict) {
        beanValidator(sysDict);
        return super.update(sysDict);
    }

    /**
    * 删除
    * @param id
    * @return
    */
    @PostMapping(value = {"remove/{id}"})
    @RequiresPermissions("sys:dict:remove")
    @ApiOperation(value = "删除字典",notes = "根据字典id删除字典信息",httpMethod = "POST")
    @ApiImplicitParam(name = "id", value = "字典id",required = true, dataType = "String")
    public @ResponseBody
    R remove(@PathVariable("id") String id) {
     return super.remove(id);
    }


    /**
    * 根据id获取
    * @param id
    * @return
    */
    @GetMapping(value = {"get/{id}"})
    @RequiresPermissions("sys:dict:update")
    @ApiOperation(value = "获取字典",notes = "根据字典id获取字典信息",httpMethod = "GET")
    @ApiImplicitParam(name = "id", value = "角色id",required = true, dataType = "String")
    public @ResponseBody
    R get(@PathVariable("id")  String id) {
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
