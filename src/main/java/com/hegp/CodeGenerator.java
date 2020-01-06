package com.hegp;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.commons.io.FileUtils;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// 演示例子，执行 main 方法控制台输入模块表名回车自动生成对应项目目录中
public class CodeGenerator {
    /** 被坑死过的地方
     * strategy.setSuperEntityColumns("字段名1", "字段名2") 只有设置了公共父类, 才可以设置父类的公共字段, 设置了这些字段后, 子类就不会生成,
     */
    /**
     * mybatis-plus-generator:3.2.0版本的官方代码生成器的坑
     * 1) 必须手动设置 IdType 的主键类型, 否则即使数据库表的主键是varchar(32), mybatis-plus-generator:3.2.0生成的主键字段是 Integer类型, 并且配置了 @TableId(type = IdType.AUTO)
     */
    /** mysql查询某个数据库的所有表名
        SELECT table_name FROM information_schema.tables WHERE table_schema='数据库名';

       // 将查出的多行记录通过分隔符合并成一行
       SELECT GROUP_CONCAT(table_name SEPARATOR ',') FROM (
           SELECT table_name FROM information_schema.tables WHERE table_schema='数据库名'
       ) AS new_table;
     */
    /**
     * postgresql查询某个数据库的所有表名
     * select string_agg(tablename,',') as tablename from pg_tables where schemaname='public'
     */
    public static HikariDataSource hikariDataSource = null;
    // 数据库参数
    public static String driverName = "com.mysql.jdbc.Driver";
//    public static String driverName = "org.postgresql.Driver";
    public static String dbUsername = "root";
    public static String dbPassword = "root";
    public static String dbSchema = "public";
//    public static String dbUrl = "jdbc:postgresql://localhost:5432/icity?useUnicode=true&useSSL=false&characterEncoding=utf8";
    public static String dbUrl = "jdbc:mysql://localhost:3306/xxl_job?useUnicode=true&useSSL=false&characterEncoding=utf8";

    // 模块名称
    public static String author = "hgp";
    public static String basePackage = "com.hegp";
    public static String moduleName = "system"; // "system", "businesss", "users", "roles", "permissions"
    public static String codeOutputPath = System.getProperty("user.dir")+"/src/main/java";
    public static String xmlOutputPath = System.getProperty("user.dir")+"/src/main/resources/mapper/"+ (StringUtils.hasText(moduleName)?moduleName:"");

    public static boolean useSwagger = true;
    public static boolean activeRecord = true;
    public static boolean useLombok = true;
    public static boolean baseResultMap = true;
    public static boolean entityClassShowTableName = true;

    // 时间类型参数
    public static DateType dateType = DateType.ONLY_DATE;
    // 主键类型
    public static IdType idType = IdType.UUID;
    // controller, service, mapper, model层包名设置
    public static String controllerPackageName = "controller";
    public static String servicePackageName = "service";
    public static String mapperPackageName = "mapper";
    public static String modelPackageName = "model";

//    private static String superControllerClass = "com.example.common.BaseController";
//    private static String superEntityClass = "com.baomidou.ant.common.BaseEntity";

    // 冷门参数
    public static boolean entityTableFieldAnnotationEnable = false;

    public static String scanner(String tip) {
        Scanner scanner = new Scanner(System.in);
        StringBuilder help = new StringBuilder();
        help.append("请输入" + tip + "：");
        System.out.println(help.toString());
        if (scanner.hasNext()) {
            String ipt = scanner.next();
            if (StringUtils.hasText(ipt)) {
                return ipt;
            }
        }
        throw new MybatisPlusException("请输入正确的" + tip + "！");
    }

    public static void main(String[] args) throws IOException, SQLException {
        setHikariDataSource();

        // 代码生成器
        AutoGenerator mpg = new AutoGenerator();

        // 全局配置
        GlobalConfig gc = new GlobalConfig();
        gc.setActiveRecord(activeRecord);
        gc.setBaseResultMap(baseResultMap);
        gc.setIdType(idType);
        gc.setOutputDir(codeOutputPath);
        gc.setDateType(dateType);
        gc.setAuthor(author);
        gc.setOpen(false);
        gc.setSwagger2(useSwagger); // 实体属性 Swagger2 注解
        mpg.setGlobalConfig(gc);

        // 数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUrl(dbUrl);
        dsc.setSchemaName(dbSchema);
        dsc.setDriverName(driverName);
        dsc.setUsername(dbUsername);
        dsc.setPassword(dbPassword);
        mpg.setDataSource(dsc);

        PackageConfig pc = setPackageConfig();
        mpg.setPackageInfo(pc);

        // 自定义配置
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
                // to do nothing
            }
        };

        // 如果模板引擎是 freemarker
        String templatePath = "/templates/mapper.xml.ftl";
        // 自定义输出配置
        List<FileOutConfig> focList = new ArrayList<>();
        // 自定义配置会被优先输出
        focList.add(new FileOutConfig(templatePath) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                if (entityClassShowTableName) {
                    tableInfo.setConvert(true);
                }
                // 自定义输出文件名 ， 如果你 Entity 设置了前后缀、此处注意 xml 的名称会跟着发生变化！！
                return xmlOutputPath+"/" + tableInfo.getEntityName() + "Mapper.xml";
            }
        });

        cfg.setFileOutConfigList(focList);
        mpg.setCfg(cfg);

        // 配置自定义输出模板
        TemplateConfig templateConfig = new TemplateConfig();
        // 指定自定义模板路径，注意不要带上.ftl/.vm, 会根据使用的模板引擎自动识别
        // templateConfig.setEntity("templates/entity2.java");
        // templateConfig.setService("templates/service.java");
        // templateConfig.setServiceImpl("templates/serviceImpl.java");
        // templateConfig.setController("templates/controller.java");
        templateConfig.setXml(null);  // 必须配置这个, 否则mybatis-plus官方代码生成器会在 mapper接口所在的目录生成xml文件夹并存放mapper.xml文件
        mpg.setTemplate(templateConfig);

        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        strategy.setNaming(NamingStrategy.underline_to_camel);
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
        strategy.setEntityLombokModel(useLombok);
        strategy.setRestControllerStyle(true);
        strategy.setEntityTableFieldAnnotationEnable(entityTableFieldAnnotationEnable);

        // 公共实体类的父类
//         strategy.setSuperEntityClass(superEntityClass);
        // 公共父类
        // strategy.setSuperControllerClass(superControllerClass);
        // 写于父类中的公共字段
        // strategy.setSuperEntityColumns("id");
//        strategy.setInclude(scanner("表名，多个英文逗号分割").split(","));

        /** 删除文件夹重建 */
        String codeOutputPathDir = (codeOutputPath+"/"+basePackage+"."+moduleName).replaceAll("\\.", "/");
        FileUtils.deleteDirectory(new File(codeOutputPathDir));
        strategy.setInclude(getAllTableNames().split(","));
        /** 删除文件夹重建 */

        strategy.setControllerMappingHyphenStyle(true);
        strategy.setTablePrefix(pc.getModuleName() + "_");
        mpg.setStrategy(strategy);
        mpg.setTemplateEngine(new FreemarkerTemplateEngine());
        mpg.execute();

    }

    // 包名配置
    public static PackageConfig setPackageConfig() {
        PackageConfig pc = new PackageConfig();
        pc.setModuleName(moduleName);
        pc.setParent(basePackage);
        pc.setController(controllerPackageName);
        pc.setService(servicePackageName);
        pc.setServiceImpl(servicePackageName+".impl");
        pc.setMapper(mapperPackageName);
        pc.setEntity(modelPackageName);
        return pc;
    }

    public static void setHikariDataSource() {
        if (hikariDataSource==null) {
            HikariConfig hikariConfig = new HikariConfig();
            hikariConfig.setJdbcUrl(dbUrl);
            hikariConfig.setUsername(dbUsername);
            hikariConfig.setPassword(dbPassword);
            hikariConfig.setSchema(dbSchema);
            hikariConfig.setDriverClassName(driverName);
            hikariConfig.addDataSourceProperty("cachePrepStmts", "true");
            hikariConfig.addDataSourceProperty("prepStmtCacheSize", "250");
            hikariConfig.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
            hikariDataSource = new HikariDataSource(hikariConfig);
        }
    }

    public static String getAllTableNames() throws SQLException {
        Connection conn = hikariDataSource.getConnection();
        String tableNames = null;
        if ("com.mysql.jdbc.Driver".equals(driverName)) {
            //从元数据中获取到所有的表名
            ResultSet rs = conn.getMetaData().getTables(null, null, null, new String[] { "TABLE" });
            List<String> tables = new ArrayList<>();
            while(rs.next()) {
                tables.add(rs.getString(3));
            }
            tableNames = String.join(",", tables);
        } else if ("org.postgresql.Driver".equals(driverName)) {
            String sql = "select string_agg(tablename,',') as tablenames from pg_tables where schemaname='"+dbSchema+"'";
            ResultSet rs = conn.createStatement().executeQuery(sql);
            while (rs.next()) {
                tableNames = rs.getString("tablenames");
            }
        } else {
            throw new RuntimeException("数据库驱动 "+driverName+" 不正确");
        }
        conn.close();
        return tableNames;
    }
}