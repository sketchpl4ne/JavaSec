package org.example.Static;

import org.example.UserService;
import java.util.Date;

public class StaticProxy implements UserService {
    private UserService target;
    public StaticProxy(UserService target) {
        this.target = target;
    }
    @Override
    public void select() {
        before();
        target.select();
        after();
    }

    @Override
    public void update() {
        before();
        target.select();
        after();
    }

    private void before() {     // 在执行方法之前执行
        System.out.println(String.format("log start time [%s] ", new Date()));
    }
    private void after() {      // 在执行方法之后执行
        System.out.println(String.format("log end time [%s] ", new Date()));
    }
}
