package com.hegp.system.service.core;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hegp.system.model.TreeEntity;

public interface TreeEntityService<T extends TreeEntity>  extends IService<T> {
    void test(T t);
}
