package com.zimmem.gae.wiki.viewtools;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;


public class StaticResourceTool implements HandlerInterceptor{
    private ThreadLocal<List<String>> importCss = new ThreadLocal<List<String>>();
    private ThreadLocal<List<String>> importJavascript = new ThreadLocal<List<String>>();
    
    public StaticResourceTool(){
        
    }
    
    public void init(){
        
    }
    
    public void importJavascript(String uri){
        importJavascript.get().add(uri);
    }
    
    public void importCss(String uri){
        importCss.get().add(uri);
    }
    
    public List<String> getJavascriptFiles(){
        return importJavascript.get();
    }
    
    public List<String> getCssFiles(){
        return importCss.get();
    }
    
    public void clear(){
        importCss.remove();
        importJavascript.remove();
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        importCss.set(new ArrayList<String>());
        importJavascript.set(new ArrayList<String>());
        return true;
    }


    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex){
        importCss.remove();
        importJavascript.remove();
        
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {
        if(modelAndView!=null){
            modelAndView.addObject("srTool", this);
        }
        
    }
}
