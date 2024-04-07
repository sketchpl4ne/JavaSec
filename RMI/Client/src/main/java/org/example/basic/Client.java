package org.example.basic;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Client {
    public static void main(String[] args) throws Exception{
        // 获取注册中心对象
        Registry registry = LocateRegistry.getRegistry("localhost", 1099);
        // 获取远程对象的stub
        RemoteObjectInterface stub = (RemoteObjectInterface) registry.lookup("Hello");
        // 利用stub调用远程对象的方法
        Object hacker = new Object();

        System.out.println(stub.sayHello("Jasper"));
    }
}
