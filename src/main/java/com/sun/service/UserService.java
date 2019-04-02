package com.sun.service;

import com.sun.biz.UserBiz;

/**
 * @author sunhuaquan
 * @title testdemo
 * @since 1.0.0
 */
public class UserService {

    private UserBiz userBiz;

    private String log;

    private Integer id;


    public void login(String name, String password) {
        System.out.println("UserService................." + log + " " + id);
        userBiz.login(name, password);

    }
}
