package com.hegp.system.service.core.impl;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hegp.system.model.TreeEntity;
import com.hegp.system.service.core.TreeEntityService;

public class TreeEntityServiceImpl<M extends BaseMapper<T>, T extends TreeEntity> extends ServiceImpl<M, T> implements TreeEntityService<T> {
    @Override
    public void test(T t) {
        super.save(t);
    }
}
