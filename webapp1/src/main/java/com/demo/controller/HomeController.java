package com.demo.controller;

import org.apache.shiro.SecurityUtils;
import org.pac4j.core.profile.CommonProfile;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import io.buji.pac4j.subject.Pac4jPrincipal;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home(){
        return "index";
    }
    

        @GetMapping("/haha")
        public ModelAndView doView(ModelMap map) {
    		Pac4jPrincipal p = SecurityUtils.getSubject().getPrincipals().oneByType(Pac4jPrincipal.class);
    		
    		if(p != null) {
    			CommonProfile profile = p.getProfile();
    			map.put("profile", profile);
    		}

            ModelAndView mv = new ModelAndView("index"); 
            return mv;
        }
    
}