package com.casclient.demo.entity;

import java.io.Serializable;

import net.sf.json.JSONObject;

/**
 * Created by lep on 18-6-10.
 */
public class RoleResource implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 主键id */
    private Long id;

    /** 角色id */
    private Long roleId;

    /** 资源id */
    private Long resourceId;

    public RoleResource() {
    }

    public RoleResource(Long id, Long roleId, Long resourceId) {
        this.id = id;
        this.roleId = roleId;
        this.resourceId = resourceId;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getRoleId() {
        return this.roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public Long getResourceId() {
        return this.resourceId;
    }

    public void setResourceId(Long resourceId) {
        this.resourceId = resourceId;
    }

    @Override
    public String toString() {
        return JSONObject.fromObject(this).toString();
    }
}
