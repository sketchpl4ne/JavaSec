package org.example.serverAttackClient;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Client {
    public static void main(String[] args) throws Exception {
        // 1. get the stub of registry
        Registry registry = LocateRegistry.getRegistry("localhost", 1099);
        // 2. get the stub of remote object
        RemoteObjectInterface stub = (RemoteObjectInterface) registry.lookup("remoteObject");
        // 3. invoke the method of remote object
        stub.vulFunc();
    }
}
