package org.ligson.fw.web.controller;

import org.ligson.fw.web.vo.WebResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Map;
import java.util.Set;

/**
 * Created by ligson on 2016/4/26.
 */
public class BaseController {
    protected HttpServletRequest request;
    protected HttpServletResponse response;
    protected HttpSession session;
    protected ServletContext applicationContext;
    protected WebResult webResult = new WebResult();
    protected static Logger logger;

    public BaseController() {
        logger = LoggerFactory.getLogger(this.getClass());
    }

    @ModelAttribute
    private void setRequestAndResponse(HttpServletRequest request, HttpServletResponse response) {
        this.request = request;
        this.response = response;
        this.session = request.getSession();
        this.applicationContext = request.getServletContext();
    }

    public String redirect(String url) {
        return "redirect:" + url;
    }


    public String forward(String url) {
        Map<String, String[]> paramsMap = request.getParameterMap();
        Set<String> keySet = paramsMap.keySet();
        for (String key : keySet) {
            String[] value = paramsMap.get(key);
            if (value.length == 1) {
                request.setAttribute(key, value[0]);
            } else {
                request.setAttribute(key, value);
            }
        }
        return "forward:" + url;
    }
}
