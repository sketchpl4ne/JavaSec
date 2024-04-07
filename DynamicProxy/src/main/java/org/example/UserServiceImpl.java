package org.example;

public class UserServiceImpl implements UserService{
    @Override
    public void select() {
        System.out.println("查询操作...");
    }

    @Override
    public void update() {
        System.out.println("更新操作...");
    }
}
