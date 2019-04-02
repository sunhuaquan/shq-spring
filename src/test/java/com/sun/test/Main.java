package com.sun.test;

import com.sun.context.FileSystemXmlContext;
import com.sun.factory.BeanFactory;
import com.sun.service.UserService;

/**
 * @Description:
 * @author: sunhuaquan
 * @Date: 2018-12-10 14:06
 */
public class Main {

    public static void main(String[] args) {
        String fileName = "src/beans.xml";
        BeanFactory beanFactory = new FileSystemXmlContext(fileName);
        UserService userService = (UserService) beanFactory.getBean("userService");
        userService.login("sunquan", "123");
    }
}
