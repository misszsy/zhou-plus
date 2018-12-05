package com.zhou.plus.framework.security;


import java.io.Serializable;

/**
 * 授权用户信息
 * @author bone
 * @version 2017-07-27
 */
public class Principal implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id; // 编号

    private String username; //名称

    private String name; //名称

    public Principal(String id, String username,String name) {
        this.id = id;
        this.username=username;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
