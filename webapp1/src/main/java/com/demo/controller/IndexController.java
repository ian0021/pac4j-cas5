package com.demo.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.pac4j.core.profile.CommonProfile;
import org.springframework.web.servlet.mvc.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import io.buji.pac4j.subject.Pac4jPrincipal;
import org.springframework.web.servlet.ModelAndView;




@RestController
public class IndexController {
    @RequestMapping(value = "/initpage", method = RequestMethod.GET)
    public ModelAndView doView(ModelMap map) {
		Pac4jPrincipal p = SecurityUtils.getSubject().getPrincipals().oneByType(Pac4jPrincipal.class);
		
		if(p != null) {
			CommonProfile profile = p.getProfile();
			map.put("profile", profile);
		}

        ModelAndView mv = new ModelAndView("index"); 
        return mv;
    }

	@RequestMapping(value="/index",method=RequestMethod.GET) 
	public String index(ModelMap map) {
		
		//获取用户身份
		Pac4jPrincipal p = SecurityUtils.getSubject().getPrincipals().oneByType(Pac4jPrincipal.class);
		
		if(p != null) {
			CommonProfile profile = p.getProfile();
			map.put("profile", profile);
		}
		
		return "index";
	}

    @RequestMapping("/index1")
    public String index1(){
        return "Client 1 后端 index 接口";
    }

    @RequestMapping("/world")
    public String world(){
        return "Client 1 后端 api/world 接口";
    }

}
