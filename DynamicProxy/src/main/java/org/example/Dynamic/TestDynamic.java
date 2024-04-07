package org.example.Dynamic;

import org.example.UserService;
import org.example.UserServiceImpl;
import java.lang.reflect.Proxy;


public class TestDynamic {
    public static void main(String[] args) {
        UserServiceImpl userService = new UserServiceImpl();
        UserServiceInvocationHandler uih = new UserServiceInvocationHandler(userService);
        UserService userServiceProxy = (UserService) Proxy.newProxyInstance(userService.getClass().getClassLoader(), userService.getClass().getInterfaces(), uih);
        userServiceProxy.select();
        userServiceProxy.update();
    }
}
