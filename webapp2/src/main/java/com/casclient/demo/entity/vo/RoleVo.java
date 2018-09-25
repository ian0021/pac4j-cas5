package com.casclient.demo.entity.vo;

import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;

import  com.casclient.demo.entity.Role;
import  com.casclient.demo.entity.Resource;

public class RoleVo extends Role implements Serializable {

    private List<Resource> resources = new ArrayList<Resource>();

    public List<Resource> getResources() {
        return resources;
    }

    public void setResources(List<Resource> resources) {
        this.resources = resources;
    }
}

