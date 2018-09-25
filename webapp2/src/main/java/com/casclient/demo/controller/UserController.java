package com.casclient.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.boot.SpringApplication;
import com.casclient.demo.service.RoleService;
import com.casclient.demo.service.UserService;


import java.util.Arrays;
import java.util.List;
import com.casclient.demo.entity.User;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/users")
    @RequiresPermissions(value = "user:view")
    @ResponseBody
    public List<User> users() {
        List<User> userList = userService.selectAlluser();
        return userList;
    }

    @RequiresPermissions(value = "role:update")
    @GetMapping(value = "/roles")
    @ResponseBody
    public String put(){
        return "允许修改角色, role:update";
    }

    @GetMapping(value = "/self")
    @RequiresPermissions(value = "user:view")
    @ResponseBody
    public PrincipalCollection getUserById() {
        return SecurityUtils.getSubject().getPrincipals();
    }


}
