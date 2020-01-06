package ${baseTreeEntityPackage};

/**
 * 主要用于树形结构的子子孙孙节点的搜索
 * 树形结构搜索要构造一个树形节点路径,否则搜索不了子子孙孙节点
 * symbol是系统默认给的一个标识, 是雪花算法转成base58的标识, fullPath就是sysbol拼接起来搜索子孙节点用的
 * 每次生成记录时, 默认对symbol赋值, 编辑时不再修改取值
 * 一开始想用记录的ID拼接全路径, 但是uuid是32位, 占用空间, 拼接20层的路径ID时, 路径长度是660位, 搜索时性能低,
 * 因此用雪花算法生成的ID转成的base58作为标识拼接路径, 拼接20层的路径, 路径长度是260位
 */
public class Tree <#if activeRecord>extends Model<T></#if> {
    /**
     * 标识, 每个树形记录都有一个标识, 主要用于拼接树形结构的路径,方便搜索
     */
    private String symbol;
    /**
     * 标识, 每个树形记录都有一个标识, 主要用于拼接树形结构的路径,方便搜索
     */
    private String fullPath;
    /**
     * 父级ID, 这里有个限制, 主表的ID必须是String类型
     */
    private String parentId;
    /**
     * 第几层的节点
     */
    private Integer level;
    /**
     * 排序号
     */
    private Integer orderSort;

    public Tree() {
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getFullPath() {
        return fullPath;
    }

    public void setFullPath(String fullPath) {
        this.fullPath = fullPath;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Integer getOrderSort() {
        return orderSort;
    }

    public void setOrderSort(Integer orderSort) {
        this.orderSort = orderSort;
    }
}
