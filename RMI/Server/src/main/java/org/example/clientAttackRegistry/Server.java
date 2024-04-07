package org.example.clientAttackRegistry;

import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Server {
    public static void main(String[] args) throws Exception {
        System.out.println("Remote Server start...");

        // 1.create registry
        Registry registry = LocateRegistry.createRegistry(1099);

        // 2.generate remote object
        RemoteObjectInterface remoteObject = new RemoteObjectImpl();

        // 3.bind the remote object to the registry
        Naming.rebind("rmi://localhost:1099/remoteObject", remoteObject);
    }
}
