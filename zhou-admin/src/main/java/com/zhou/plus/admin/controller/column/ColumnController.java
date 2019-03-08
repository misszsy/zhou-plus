package com.zhou.plus.admin.controller.column;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zhou.plus.busi.common.controller.BaseController;
import com.zhou.plus.busi.entity.Column;
import com.zhou.plus.busi.service.ColumnService;
import com.zhou.plus.framework.annotation.Log;
import com.zhou.plus.framework.resp.R;
import org.apache.commons.collections.CollectionUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.data.redis.connection.jedis.JedisUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zhoushengyuan
 * @since 2018-12-24
 */
@ApiIgnore
@Controller
@RequestMapping("/column")
public class ColumnController extends BaseController<ColumnService, Column> {


    public String getViewPath() {
        return "column/";
    }


   /**
    *
    * 页面跳转
    */
    @GetMapping("list")
    @RequiresPermissions("sys:column:view")
    public String listView() {
        return getViewPath() + "list";
    }


    /**
     * 获取菜单栏目
     * @return
     */
    @GetMapping("getColumnTree")
    public @ResponseBody
    R getAuthMenuTree() {
        return R.ok(baseService.list(new QueryWrapper<Column>().lambda().orderByDesc(Column::getSort)));
    }


    /**
    * 分页查询列表
    * @param column
    * @param pageNum
    * @param pageSize
    * @return
    */
    @GetMapping(value = {"listData"})
    public @ResponseBody
    R listData(Column column, Integer pageNum, Integer pageSize) {
        QueryWrapper wrapper=new QueryWrapper<Column>();
        return super.listData(wrapper, pageNum, pageSize);
    }

    /**
    * 新增
    * @param column
    * @return
    */
    @Log(value = "新增")
    @PostMapping("save")
    @RequiresPermissions("sys:column:save")
    public @ResponseBody
    R save(Column column) {
        beanValidator(column);
        return super.save(column);
    }

    /**
    * 更新
    * @return
    */
    @Log(value = "更新")
    @PostMapping("update")
    @RequiresPermissions("sys:column:update")
    public @ResponseBody
    R update(Column column) {
        beanValidator(column);
        return super.update(column);
    }

    /**
    * 删除
    * @param id
    * @return
    */
    @Log(value = "删除")
    @PostMapping("remove/{id}")
    @RequiresPermissions("sys:column:remove")
    public @ResponseBody
    R remove(@PathVariable String id) {
     return super.remove(id);
    }


    /**
     * 根据id获取
     * @param id
     * @return
     */
    @GetMapping("get/{id}")
    @RequiresPermissions("sys:column:update")
    public @ResponseBody
    R get(@PathVariable String id) {
        return super.get(id);
    }


    /**
     * 根据ids获取
     * @return
     */
    @GetMapping("getColumnList")
    public @ResponseBody R getColumnList() {
        return R.ok(baseService.listMaps(new QueryWrapper<>()));
    }
}
