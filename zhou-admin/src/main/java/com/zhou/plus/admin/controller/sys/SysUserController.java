package com.zhou.plus.admin.controller.sys;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zhou.plus.busi.common.controller.BaseController;
import com.zhou.plus.busi.common.utils.UserUtils;
import com.zhou.plus.busi.entity.SysUser;
import com.zhou.plus.busi.service.SysUserService;
import com.zhou.plus.framework.annotation.Log;
import com.zhou.plus.framework.resp.R;
import com.zhou.plus.framework.utils.CryptoUtils;
import com.zhou.plus.framework.utils.StringUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.catalina.User;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

/**
 * <p>
 * 系统用户 前端控制器
 * </p>
 *
 * @author zhoushengyuan
 * @since 2018-11-08
 */
@Controller
@RequestMapping("/sys/user")
@Api(value = "SysUserController",description = "系统用户相关api")
public class SysUserController extends BaseController<SysUserService, SysUser> {

    public String getViewPath() {
        return "sys/user/";
    }

    /**
     *
     * 页面跳转
     */
    @GetMapping("list")
    @RequiresPermissions("sys:user:view")
    @ApiIgnore
    public String listView(){
        return getViewPath() + "list";
    }

    /**
     * 页面跳转
     * @return
     */
    @GetMapping(value = {"modifyPwd"})
    @RequiresPermissions("sys:user:view")
    public String modifyPwd() {
        return getViewPath()+"modifyPwd";
    }


    /**
     * 查询系统用户列表
     * @param pageNum
     * @param pageSize
     * @return
     */
    @GetMapping("listData")
    @ApiOperation(value = "用户列表",notes = "查询系统用户列表",httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "当前页码",required = true, paramType = "query", dataType = "Integer"),
            @ApiImplicitParam(name = "pageSize", value = "显示条数",required = true, paramType = "query", dataType = "Integer")
    })
    public @ResponseBody
    R listData(SysUser user, Integer pageNum, Integer pageSize) {
        QueryWrapper wrapper=new QueryWrapper<SysUser>()
                .like(StringUtils.isNotEmpty(user.getName()),"name",user.getName())
                .like(StringUtils.isNotEmpty(user.getUsername()),"username",user.getUsername());
        return super.listData(wrapper,pageNum,pageSize);
    }

    /**
     * 新增用户
     * @param user
     * @return
     */
    @Log(value = "新增用户")
    @PostMapping("save")
    @RequiresPermissions("sys:user:save")
    public @ResponseBody
    R save(@RequestBody SysUser user) {
        beanValidator(user);
        if(StringUtils.isEmpty(user.getPassword())){
            return R.fail("用户密码信息不能为空");
        }
        if(baseService.getOne(new QueryWrapper<SysUser>()
                .lambda()
                .eq(SysUser::getUsername,user.getUsername())) !=null){
            return R.fail("登录账号重复");
        }
        return super.save(user);
    }


    /**
     * 更新用户
     * @return
     */
    @Log(value = "更新用户")
    @PostMapping("update")
    @RequiresPermissions("sys:user:update")
    public @ResponseBody
    R update(@RequestBody SysUser user, String oldUsername) {

        beanValidator(user);
        if(!StringUtils.equals(oldUsername,user.getUsername())){
            SysUser sysUser=baseService.getOne(new QueryWrapper<SysUser>()
                    .lambda()
                    .eq(SysUser::getUsername,user.getUsername()));
            if(sysUser !=null){
                return R.fail("更新失败,登录账号已经存在");
            }
        }
        return super.update(user);
    }

    /**
     * 删除用户信息
     * @param id
     * @return
     */
    @Log(value = "删除管理")
    @PostMapping(value = {"remove/{id}"})
    @RequiresPermissions("sys:user:remove")
    @ApiOperation(value = "删除用户",notes = "根据用户id删除用户",httpMethod = "POST")
    @ApiImplicitParam(name = "id", value = "用户id",required = true, dataType = "String")
    public @ResponseBody
    R remove(@PathVariable("id") String id) {
        super.remove(id);
        UserUtils.clearCache(new SysUser(id));
        return R.ok();
    }

    /**
     * Ajax权限检验
     * @return
     */
    @GetMapping("checkPermission")
    @RequiresPermissions(value = { "sys:user:save", "sys:user:update"}, logical = Logical.OR)
    @ApiIgnore
    public @ResponseBody
    R checkPermission() {
        return R.ok();
    }


    /**
     * 根据id获取用户信息
     * @param id
     * @return
     */
    @GetMapping(value = {"get/{id}"})
    @RequiresPermissions("sys:user:update")
    @ApiOperation(value = "获取用户",notes = "根据用户id获取用户信息",httpMethod = "GET")
    @ApiImplicitParam(name = "id", value = "用户id",required = true, dataType = "String")
    public @ResponseBody
    R get(@PathVariable("id") String id) {
        return  R.ok(UserUtils.get(id));
    }


    /**
     * 修改个人用户密码
     * @param oldPassword
     * @param newPassword
     * @return
     */
    @Log(value = "修改密码")
    @RequiresPermissions("sys:user:update")
    @RequestMapping(value = "modifyPwd")
    @ResponseBody
    public R modifyPwd(String oldPassword, String newPassword) {
        SysUser user = UserUtils.getUser();
        if (StringUtils.isNotBlank(oldPassword) && StringUtils.isNotBlank(newPassword)){
            if (!CryptoUtils.validatePassword(oldPassword,user.getPassword())){
                return R.fail("修改密码失败，旧密码错误");
            }
        }else{
            return R.fail("旧密码或新密码不能为空");
        }
        user.setPassword(CryptoUtils.encryptPassword(newPassword));
        super.update(user);
        return R.ok("修改密码成功,请重新登录！");
    }
}
