package ${package.Controller};

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
<#if swagger2>
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
</#if>
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.util.Assert;
<#if restControllerStyle>
import org.springframework.web.bind.annotation.RestController;
<#else>
import org.springframework.stereotype.Controller;
</#if>
<#if superControllerClassPackage??>
import ${superControllerClassPackage};
</#if>

import ${package.Entity}.${entity};
import ${package.Service}.${table.serviceName};

import java.util.List;

/**
 * <p>
 * ${table.comment!} 前端控制器
 * </p>
 *
 * @author ${author}
 * @since ${date}
 */
<#if restControllerStyle>
@RestController
<#else>
@Controller
</#if>
@RequestMapping("<#if package.ModuleName??>/${package.ModuleName}</#if>/<#if controllerMappingHyphenStyle??>${controllerMappingHyphen}<#else>${table.entityPath}</#if>")
<#if kotlin>
class ${table.controllerName}<#if superControllerClass??> : ${superControllerClass}()</#if>
<#else>
<#if superControllerClass??>
public class ${table.controllerName} extends ${superControllerClass} {
<#else>
public class ${table.controllerName} {
</#if>

    @Autowired
    private ${table.serviceName} ${table.entityName?uncap_first}Service;

    /** 新增 */
    <#if swagger2>
    @ApiOperation(value = "新增数据")
    </#if>
    @PostMapping(value = "/add")
    public void add(@RequestBody ${entity} entity) {
        ${table.entityName?uncap_first}Service.save(entity);
    }

    /** 编辑 */
    <#if swagger2>
    @ApiOperation(value = "编辑数据")
    @ApiImplicitParam(paramType="path", name = "id", value = "id", required = true)
    </#if>
    @PutMapping(value = "/{id}")
    public void editById(@RequestBody ${entity} entity) {
        ${table.entityName?uncap_first}Service.updateById(entity);
    }

    /** 查询 */
    <#if swagger2>
    @ApiOperation(value = "根据ID查询")
    @ApiImplicitParam(paramType="path", name = "id", value = "id", required = true)
    </#if>
    @GetMapping(value = "/{id}")
    public ${entity} getById(@PathVariable("id") String id) {
       ${entity} result = ${table.entityName?uncap_first}Service.getById(id);
       return result;
    }

    /** 删除 */
    <#if swagger2>
    @ApiOperation(value = "删除数据")
    @ApiImplicitParam(paramType="path", name = "id", value = "id", required = true)
    </#if>
    @DeleteMapping(value = "/{id}")
    public void delete(@PathVariable("id") String id) {
        boolean result = ${table.entityName?uncap_first}Service.removeById(id);
    }

    /** 批量删除 */
    <#if swagger2>
    @ApiOperation(value = "批量删除")
    </#if>
    @DeleteMapping(value = "/batch-delete")
    public void delete(@RequestBody List<String> ids) {
        Assert.isTrue(ObjectUtils.isNotEmpty(ids), "请勾选数据");
        boolean result = ${table.entityName?uncap_first}Service.removeByIds(ids);
    }

    /** 分页查询 */
    <#if swagger2>
    @ApiOperation(value = "分页查询")
    @ApiImplicitParams({
        @ApiImplicitParam(paramType="query", name = "pageNum", value = "页码（默认为1）", required = false),
        @ApiImplicitParam(paramType="query", name = "pagesize", value = "每页记录数（默认为10）", required = false)
    })
    </#if>
    @GetMapping("/page")
    public IPage<${entity}> queryPage(@RequestParam(name = "pageNum", defaultValue = "1") Long pageNum,
                                      @RequestParam(name = "pagesize", defaultValue = "10") Long pagesize) {
        Page page = new Page<>(pageNum, pagesize);
        QueryWrapper queryWrapper = new QueryWrapper<>();
        /**
        queryWrapper.eq(true, "column1", "value1");
        queryWrapper.orderByAsc("createTime", "age");
        queryWrapper.orderByDesc("score");
         */
        return ${table.entityName?uncap_first}Service.page(page, queryWrapper);
    }
}
</#if>
