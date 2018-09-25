package com.casclient.demo.entity;

import java.io.Serializable;
import java.util.Date;


import net.sf.json.JSONObject;

/**
 * Created by lep on 18-6-10.
 */
public class User implements Serializable{
    private static final long serialVersionUID = 1L;

    /** 主键id */
    private Long id;

    /** 用户名 */
    private String username;

    /** 密码 */
    private String password;

    /** 密码加密盐 */
    private String salt;

    /** 创建时间 */
    private Date createTime;

    public User() {
    }

    public User(Long id, String username, String password, String salt, Date createTime) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.salt = salt;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
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
