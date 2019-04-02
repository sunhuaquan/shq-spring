package com.sun.biz;

import com.sun.dao.UserDao;

/**
 * @author sunhuaquan
 * @title testdemo
 * @since 1.0.0
 */
public class UserBiz {

    private UserDao userDao;

    public void login(String name, String password) {
        System.out.println("UserBiz.................");
        userDao.login(name,password);
    }
}
