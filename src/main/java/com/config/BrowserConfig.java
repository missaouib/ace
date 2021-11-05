package com.config;


import com.util.ApplicationContextUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import util.Console;
import util.DataTypeUtil;
import util.IpUtil;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Properties;

/**
 * Application启动时自动打开默认Browser
 */
@Configuration
public class BrowserConfig {
    private static final Log log = LogFactory.getLog(BrowserConfig.class);

    static String url = "http://localhost:8088/";
    static String SwaggerUrl = "http://localhost:8088/swagger-ui.html";
    static String Knife4jUrl = "http://localhost:8088/doc.html";
    static String windowsBrowser = "C:\\Program Files (x86)\\Google\\Chrome\\Application\\chrome.exe ";

    /**
     * 打开windows默认Browser
     */

    private void openWindowsDefaultBrowser(boolean indexEnable) {
        if (!indexEnable) {
            return;
        }
        try {
            ProcessBuilder proc = new ProcessBuilder(windowsBrowser, url);
            proc.start();
            BrowserConfig config = new BrowserConfig();
            config.PrintUrl("WELCOME PAGE: ", url);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void openSwaggerOnMac(Map m, boolean swaggerEnable) throws IOException {
        if (!swaggerEnable) {
            return;
        }
        String macSwaggerUrl = SwaggerUrl.replace("8088", DataTypeUtil.integerToString((Integer) m.get("port")));
        String Command = "open " + macSwaggerUrl;
        log.info("Swagger2:\t\t" + SwaggerUrl);
        Process Child = Runtime.getRuntime().exec(Command);
    }

    private void openKnife4jOnMac(Map m, boolean isKnife4jEnable) throws IOException {
        if (!isKnife4jEnable) {
            return;
        }
        String macSwaggerUrl = Knife4jUrl.replace("8088", DataTypeUtil.integerToString((Integer) m.get("port")));
        String Command = "open " + macSwaggerUrl;
        log.info("Knife4j:\t\t" + Knife4jUrl);
        Process Child = Runtime.getRuntime().exec(Command);
    }


    private void openMacDefaultBrowser(boolean indexEnable) throws IOException {
        ApplicationContextUtil app = new ApplicationContextUtil();
        IpUtil ip = (IpUtil) app.getBeanByName("ipUtil");
        Map m = ip.getHostInfo();
        String macUrl = url.replace("8088", DataTypeUtil.integerToString((Integer) m.get("port")));
        log.info("ACE INDEX:\t\t" + macUrl);
        if (indexEnable) {
            String Command = "open " + macUrl;
            Process Child = Runtime.getRuntime().exec(Command);
        }
    }

//    public void getCss() throws IOException {
//        ApplicationContextUtil app = new ApplicationContextUtil();
//        IpUtil ip = (IpUtil) app.getBeanByName("ipUtil");
//        Map m = ip.getHostInfo();
//        String macUrl = url.replace("8088", DataTypeUtil.integerToString((Integer) m.get("port")));
//        log.info("Home Page:\t\t" + macUrl+ "assets/css/bootstrap.min.css");
//        if (true) {
//            String Command = "open " + macUrl + "assets/css/bootstrap.min.css";
//            Process Child = Runtime.getRuntime().exec(Command);
//        }
//    }
//
//    public void getIndex() throws IOException {
//        ApplicationContextUtil app = new ApplicationContextUtil();
//        IpUtil ip = (IpUtil) app.getBeanByName("ipUtil");
//        Map m = ip.getHostInfo();
//        String macUrl = url.replace("8088", DataTypeUtil.integerToString((Integer) m.get("port")));
//        log.info("Home Page:\t\t" + macUrl+ "ace/index.html");
//        if (true) {
//            String Command = "open " + macUrl + "ace/index.html";
//            Process Child = Runtime.getRuntime().exec(Command);
//        }
//    }

    /**
     * 打开windows默认Browser
     */
    private void openSwaggerOnWindows() {
        try {
            ProcessBuilder proc = new ProcessBuilder(windowsBrowser, SwaggerUrl);
            proc.start();
            BrowserConfig config = new BrowserConfig();
            config.PrintUrl("SWAGGER:\t\t", SwaggerUrl);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void PrintUrl(String banner, String url) {
        System.out.print(LocalDateTime.now() + "  ");
        Console.print("INFO ", Console.GREEN);
        Console.println(banner + url, Console.BOLD, Console.BLUE);
    }

    private static String getOsInfo() {
        //Java获取当前操作系统的信息
        //https://blog.csdn.net/qq_35981283/article/details/73332040
        Properties props = System.getProperties();
        String osName = props.getProperty("os.name").toUpperCase();
        System.out.println("OPERATION SYSTEM TYPE：" + osName);
        return osName;
    }


    public void openAceIndexAndSwagger(boolean openIndex, boolean openSwagger) throws IOException {
        BrowserConfig browserConfig = new BrowserConfig();
        if (openIndex) {
            String osName = BrowserConfig.getOsInfo();
            if (osName.contains("WINDOWS")) {
                browserConfig.openWindowsDefaultBrowser(true);
            } else if (osName.contains("MAC OS")) {
                browserConfig.openMacDefaultBrowser(true);
            }
            if (openSwagger) {
                ApplicationContextUtil app = new ApplicationContextUtil();
                IpUtil ip = (IpUtil) app.getBeanByName("ipUtil");
                Map m = ip.getHostInfo();
                browserConfig.openSwaggerOnMac(m, true);
            }
        }
    }


}