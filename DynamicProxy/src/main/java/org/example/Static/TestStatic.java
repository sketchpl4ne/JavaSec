package org.example.Static;

import org.example.Static.StaticProxy;
import org.example.UserServiceImpl;

public class TestStatic {
    public static void main(String[] args) {
        UserServiceImpl userService = new UserServiceImpl();
        StaticProxy staticProxy = new StaticProxy(userService);
        staticProxy.select();
    }
}
