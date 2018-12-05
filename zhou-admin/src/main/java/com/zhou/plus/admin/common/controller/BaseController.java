package com.zhou.plus.admin.common.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zhou.plus.admin.common.entity.BaseModel;
import com.zhou.plus.framework.resp.R;
import com.zhou.plus.framework.validator.BeanValidators;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;

import javax.validation.Validator;

public abstract class BaseController<S extends IService<T>,T extends BaseModel> {

    @Autowired
    protected S baseService;

    /**
     * 日志对象
     */
    protected Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 验证Bean实例对象
     */
    @Autowired
    protected Validator validator;

    public R listData(QueryWrapper wrapper, Integer pageNum, Integer pageSize) {
        IPage page=baseService.page(new Page<>(pageNum,pageSize),wrapper);
        return R.ok(page);
    }


    /**
     * 根据id获取对象返回
     * @param id
     * @return
     */
    public R get(@PathVariable String id){
        return R.ok(baseService.getById(id));
    }

    /**
     * 新增
     * @param obj
     * @return
     */
    public R save(T obj){
        beanValidator(obj);
        baseService.save(obj);
        return  R.ok(get(obj.getId()));
    }

    /**
     * 更新
     * @param obj
     * @return
     */
    public R update(T obj){
       beanValidator(obj);
        baseService.updateById(obj);
       return R.ok(get(obj.getId()));
    }

    /**
     * 删除
     * @param id
     * @return
     */
    public R remove(@PathVariable String id){
        baseService.removeById(id);
        return R.ok();
    }


    /**
     * 服务端参数有效性验证
     *
     * @param object
     *            验证的实体对象
     * @param groups
     *            验证组
     * @return 验证成功：返回true；严重失败：将错误信息添加到 message 中
     */
    protected void beanValidator(Object object,Class<?>... groups) {
        BeanValidators.validateWithException(validator, object, groups);
    }
}
