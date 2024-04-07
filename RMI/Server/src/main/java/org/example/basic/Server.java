package org.example.basic;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Server {
    public static void main(String[] args) throws Exception{
        // 创建注册中心 registry
        Registry registry = LocateRegistry.createRegistry(1099);
        // 创建远程对象 remoteObject
        RemoteObjectInterface remoteObject = new RemoteObjectImpl();
        registry.bind("Hello", remoteObject);
        System.out.println("Server Start");
    }
}
