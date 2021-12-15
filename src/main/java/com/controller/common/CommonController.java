package com.controller.common;

import cn.dev33.satoken.session.SaSession;
import cn.dev33.satoken.stp.StpUtil;
import com.constant.Constant;
import com.constant.Css;
import com.constant.WebServiceInfo;
import com.models.entity.dao.Users;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;
import util.JsonUtil;
import util.NullUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CommonController {
    private Log log = LogFactory.getLog(this.getClass());
    protected final String keyAjaxResult = "ajaxResult";
    /**
     * Request对象(存在于用户的每个请求)
     */
    private HttpServletResponse response;
    private HttpServletRequest request;

    protected HttpServletRequest getRequest() {
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        request = servletRequestAttributes.getRequest();
        return request;
    }

    protected HttpServletResponse getResponse() {
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        response = servletRequestAttributes.getResponse();
        return response;
    }


    protected ModelAndView logOut() {
        ModelAndView modelAndView = page("ace/login.html");
        String msg = "Logged out";
        modelAndView.addObject("msg", msg);
        modelAndView.addObject(Css.css, Css.red);

        StpUtil.logout();
        return modelAndView;
    }

    protected ModelAndView logOut(String msg) {
        ModelAndView modelAndView = page("ace/login.html");
        String msgCss = Css.red;
        modelAndView.addObject("msg", msg);
        modelAndView.addObject(Css.css, msgCss);
        StpUtil.logout();
        return modelAndView;
    }

    protected void kickOut(Long userId) {
        StpUtil.kickout(userId);
    }

    /**
     * 返回页面
     *
     * @param page page
     * @return mv
     */
    protected ModelAndView page(String page) {
        if (!isLogin()) {
            ModelAndView modelAndView = new ModelAndView("ace/login.html");
            modelAndView.addObject("user", new Users());
            return modelAndView;
        }
        ModelAndView modelAndView = new ModelAndView(page);
        Users user = getCurrentUser();
        modelAndView.addObject("user", user);
        return modelAndView;
    }

    //原文
//    protected ModelAndView page(String page) {
//        return new ModelAndView(page);
//    }


    protected ModelAndView redirect(String url) {
        ModelAndView modelAndView = null;
        try {
            log.info("重定向地址：" + url);
            modelAndView = new ModelAndView("redirect:/" + url);
            if (isLogin()) {
                Users user = getCurrentUser();
                modelAndView.addObject("user", user);
            } else {
                modelAndView.addObject("user", new Users());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return modelAndView;
    }

    /**
     * 返回json数据
     *
     * @param
     * @return
     */
    protected ModelAndView json(Integer status, String msg, Object obj) {
        WebServiceInfo webServiceInfo = new WebServiceInfo(status, msg, obj);
        ModelAndView json = json(webServiceInfo);
        return json;
    }

    /**
     * 返回json数据
     */
    protected ModelAndView json(WebServiceInfo rs) {
        ModelAndView mv = new ModelAndView("pb-pages/ajax-result.jsp");
        System.out.println(JsonUtil.getInstance().toJson(rs));
        mv.addObject(keyAjaxResult, JsonUtil.getInstance().toJson(rs));
        return mv;
    }

    public void setSession(Users users) {
        StpUtil.getSession().set("user", users);
    }

    public Users getCurrentUser() {
        Users user = (Users) StpUtil.getSession().get("user");
        return user;
    }

    public SaSession getSession() {
        return StpUtil.getSession();
    }

    public SaSession getSessionByLoginId(Long id) {
        return StpUtil.getSessionByLoginId(id);
    }

    public void login(long userId) {
        StpUtil.login(userId);
    }

    public void login(long userId, boolean rememberMe) {
        StpUtil.login(userId, rememberMe);
    }

    public void clearSession() {
        // 注销此Session会话 (从持久库删除此Session)
        SaSession session = getSession();
        session.logout();
    }

    public boolean isLogin() {
        return StpUtil.isLogin();
    }

}
