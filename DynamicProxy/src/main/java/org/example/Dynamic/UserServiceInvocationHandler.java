package org.example.Dynamic;

import org.example.UserService;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Date;

public class UserServiceInvocationHandler implements InvocationHandler {
    private UserService target;

    public UserServiceInvocationHandler(UserService target) {
        this.target = target;
    }

    //proxy: 要代理的对象
    //method: 要强化的方法
    //args:   要强化方法的参数
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if(method.getName().equals("select") || method.getName().equals("update")){
            System.out.println(String.format("log start time [%s] ", new Date()));
            Object o =  method.invoke(target,args);
            System.out.println(String.format("log end time [%s] ", new Date()));
            return o;
        }else {
            return method.invoke(target,args);
        }
    }
}
