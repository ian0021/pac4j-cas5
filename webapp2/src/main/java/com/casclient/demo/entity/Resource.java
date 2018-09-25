package com.casclient.demo.entity;

import java.io.Serializable;
import java.util.Date;

import net.sf.json.JSONObject;

/**
 * Created by lep on 18-6-10.
 */
public class Resource implements Serializable{

    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long id;

    /** 资源名称 */
    private String name;

    private ResourceType type = ResourceType.menu; //资源类型

    /** 资源介绍 */
    private String description;

    /** 资源路径 */
    private String url;

    /** 父级资源id */
    private Long pid;

    private String pids; //父编号列表

    /** 状态 */
    private Integer status;

    private String permission; //权限字符串

    /** 创建时间 */
    private Date createTime;

    public static enum ResourceType {
        menu("菜单"), button("按钮");

        private final String info;
        private ResourceType(String info) {
            this.info = info;
        }   

        public String getInfo() {
            return info;
        }   
    } 

    public Resource() {
    }

    public Resource(Long id, String name, String description, String url, Long pid, Integer status, String permission, Date createTime) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.url = url;
        this.pid = pid;
        this.status = status;
        this.permission = permission;
        this.createTime = createTime;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Long getPid() {
        return pid;
    }

    public void setPid(Long pid) {
        this.pid = pid;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {

      return JSONObject.fromObject(this).toString();
    }

}
