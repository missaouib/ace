package com.util;

import com.AceApplication;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class BeanUtil {
    private static final Logger log = LogManager.getLogger(BeanUtil.class.getName());

    public static ApplicationContext applicationContext = null;

    //@Autowired
    // private RequestMappingHandlerMapping requestMappingHandlerMapping;

    public BeanUtil() {
        if (applicationContext == null) {
            log.info("ApplicationContext loaded from ACE !");
            applicationContext = AceApplication.applicationContext;
        }
    }


    /**
     * print all bean name
     *
     * @param applicationContext
     */
    public static void printBeanName(ApplicationContext applicationContext) {
        String[] beanNames = applicationContext.getBeanDefinitionNames();
        log.info("total bean: {}", applicationContext.getBeanDefinitionCount());
        // String[] beanNames = applicationContext.getBeanNamesForAnnotation(RequestMapping.class);//所有添加该注解的bean
        int i = 0;
        for (String s : beanNames) {
            log.info("{},beanName: {}", ++i, s);
        }
    }


    /**
     * getBean By beanName
     *
     * @param name
     * @return
     */
    public Object getBeanByName(String name) {
        Object object = applicationContext.getBean(name);
        return object;
    }

    public static void main(String[] args) {
        getXmlConfigFile();
    }

    /**
     * 加载ApplicationContext Sample
     *
     * @param
     */
    public static void getXmlConfigFile() {
        //第一种: FileSystemXmlApplicationContext
        //加载单个配置文件
        ApplicationContext ctx1 = new FileSystemXmlApplicationContext("bean.xml");
        //加载单个配置文件
        String[] locations = {"bean1.xml", "bean2.xml", "bean3.xml"};
        ApplicationContext ctx2 = new FileSystemXmlApplicationContext(locations);
        //根据具体路径加载文件
        ApplicationContext ctx3 = new FileSystemXmlApplicationContext("D:/project/bean.xml");

    }


    /**
     * ClassPathXmlApplicationContext --从classpath路径加载配置文件，创建Bean对象
     */
    public void getClassPathXmlApplicationContext() {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
        Object claz = (Object) ctx.getBean("beanName");
    }

    public void getApplicationContext(HttpServletRequest request) {
        HttpSession session = request.getSession();
        ServletContext context = session.getServletContext(); //arg0.getSession().getServletContext();
        ApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(context);
        Object clazz = (Object) ctx.getBean("beanName");
    }


}
