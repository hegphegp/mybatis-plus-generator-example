package ${baseTreeServicePackage}.impl;

import ${baseTreeEntityPackage}.Tree;
import ${baseTreeServicePackage}.TreeService;
import ${superMapperClassPackage};
import ${superServiceImplClassPackage};

public class TreeServiceImpl<M extends ${superMapperClass}<T>, T extends Tree> extends ${superServiceImplClass}<M, T> implements TreeService<T> {
    @Override
    public void test(T t) {
        super.save(t);
    }
}
