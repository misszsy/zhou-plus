package com.zhou.plus.admin.controller.sys;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zhou.plus.admin.common.controller.BaseController;
import com.zhou.plus.admin.modules.entity.SysLog;
import com.zhou.plus.admin.modules.service.SysLogService;
import com.zhou.plus.framework.resp.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zhoushengyuan
 * @since 2018-12-03
 */
@Controller
@RequestMapping("/sys/log")
@Api(value = "SysLogController",description = "系统日志相关api")
public class SysLogController extends BaseController<SysLogService, SysLog> {


    public String getViewPath() {
        return "sys/log/";
    }


   /**
    *
    * 页面跳转
    */
    @GetMapping("list")
    public String listView() {
        return getViewPath() + "list";
    }

    /**
    * 分页查询列表
    * @param sysLog
    * @param pageNum
    * @param pageSize
    * @return
    */
    @GetMapping("listData")
    @ApiOperation(value = "日志列表",notes = "查询系统日志列表",httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "SysLog", value = "系统日志详细信息",required = true, paramType = "query", dataType = "SysLog"),
            @ApiImplicitParam(name = "pageNum", value = "当前页码",required = true, paramType = "query", dataType = "Integer"),
            @ApiImplicitParam(name = "pageSize", value = "显示条数",required = true, paramType = "query", dataType = "Integer")
    })
    public @ResponseBody
    R listData(SysLog sysLog, Integer pageNum, Integer pageSize) {
        IPage page=baseService.selectPageMaps(new Page<>(pageNum,pageSize),sysLog);
        return R.ok(page);
    }

    /**
    * 新增
    * @param sysLog
    * @return
    */
    @PostMapping("save")
    public @ResponseBody
    R save(SysLog sysLog) {
        beanValidator(sysLog);
        return super.save(sysLog);
    }

    /**
    * 更新
    * @return
    */
    @PostMapping("update")
    public @ResponseBody
    R update(SysLog sysLog) {
        beanValidator(sysLog);
        return super.update(sysLog);
    }

    /**
    * 删除
    * @param id
    * @return
    */
    @PostMapping("remove/{id}")
    @ApiOperation(value = "删除日志",notes = "根据日志id删除日志信息",httpMethod = "POST")
    @ApiImplicitParam(name = "id", value = "日志id",required = true, dataType = "String")
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
    @ApiOperation(value = "获取日志",notes = "根据日志id获取日志信息",httpMethod = "POST")
    @ApiImplicitParam(name = "id", value = "日志id",required = true, dataType = "String")
    public @ResponseBody
    R get(@PathVariable("id")  String id) {
        return super.get(id);
    }
}
