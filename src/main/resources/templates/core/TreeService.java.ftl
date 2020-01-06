package ${baseTreeServicePackage};

import ${baseTreeEntityPackage}.Tree;
import ${superServiceClassPackage};

public interface TreeService<T extends Tree>  extends ${superServiceClass}<T> {
    void test(T t);
}
