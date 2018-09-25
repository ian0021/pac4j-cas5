package com.casclient.demo.entity;

import java.io.Serializable;

import net.sf.json.JSONObject;
/**
 * Created by lep on 18-6-10.
 */
public class Role implements Serializable{
    private static final long serialVersionUID = 1L;

    /** 主键id */
    private Long id;

    /** 角色名 */
    private String role;

    /** 简介 */
    private String description;

    /** 状态 */
    private Boolean available = Boolean.FALSE; //是否可用,如果不可用将不会添加给用户

    public Role() {
    }

    public Role(Long id, String role, String description, Boolean available) {
        this.id = id;
        this.role = role;
        this.description = description;
        this.available = available;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }

    @Override
    public String toString() {
        return JSONObject.fromObject(this).toString();
    }
}
