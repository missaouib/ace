package com;

import cn.dev33.satoken.SaManager;
import com.config.AceConfig;
import com.config.BrowserConfig;
import com.restController.UserRolePermissionRestController;
import com.util.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.io.IOException;
import java.util.Map;


/*
@SpringBootConfiguration
        读取配置文件，配置文件的路径是当前根目录(src/main/resources/application.yml等)
@EnableAutoConfiguration
        开启自动配置，扫描当前的所有依赖的jar包，发现新的依赖出现将会将会根据依赖完各种自动配置
        (扫描start_web，自动配置内置tomcat默认路径、端口；依赖了rabbitmq，自动配置rabbitTemble)
@ComponentScan
        属于Spring框架(@Component,@Service,@Controller,@Repository,@Entity)，扫描范围默认情况下是启动类坐在的同名包及其子孙包
*/
@SpringBootApplication
@ComponentScan({"com", "com.util"})
@MapperScan("com.mapper")
@EnableTransactionManagement
@EnableCaching
@EnableFeignClients
//for baseEntity using
@EnableJpaAuditing
public class AceApplication {
    private static final Logger log = LogManager.getLogger(AceApplication.class.getName());
    public static ApplicationContext applicationContext;


    public static void main(String[] args) throws IOException {
        applicationContext = SpringApplication.run(AceApplication.class, args);

        //print all loaded BeanName and properties value
        Console.println(SystemUtil.LINE, Console.BOLD, Console.MAGENTA);
        BeanUtil.printBeanName(applicationContext);
        PropertiesUtil.printLoadedProperties();
        Console.println(SystemUtil.LINE, Console.BOLD, Console.MAGENTA);

        //iterate bean value by name
        //print server side information
        BeanUtil beanUtil = applicationContext.getBean("beanUtil", BeanUtil.class);
        IpUtil ip = (IpUtil) beanUtil.getBeanByName("ipUtil");
        Map m = ip.getHostInfo();
        MapUtil.iterateMapKeyset(m);

        AceConfig aceConfig = beanUtil.getBeanByName("aceConfig", AceConfig.class);

        //决定项目启动时, 是否主动打开swagger/docHtml
        BrowserConfig browserConfig = new BrowserConfig();
        browserConfig.openAceIndexAndSwagger(aceConfig.isIndexEnable(), aceConfig.isSwaggerEnable(), aceConfig.isDocHtmlEnabled());

        // browserConfig.getCss();
        // browserConfig.getIndex();

        log.info("Running success：Sa-Token config：{}", SaManager.getConfig());

        UserRolePermissionRestController userRolePermissionRestController = (UserRolePermissionRestController) beanUtil.getBeanByName("userRolePermissionRestController");
        userRolePermissionRestController.defaultUser();
    }

}
