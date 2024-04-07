package org.example.basic;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RemoteObjectInterface extends Remote {
    public String sayHello() throws RemoteException;
    public String sayHello(String name) throws RemoteException;
    public String sayGoodbye() throws RemoteException;
}
