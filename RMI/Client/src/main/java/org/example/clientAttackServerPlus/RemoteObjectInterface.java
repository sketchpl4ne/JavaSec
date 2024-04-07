package org.example.clientAttackServerPlus;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RemoteObjectInterface extends Remote {
    public String sayHello(String s) throws RemoteException;
    public void vulFunc(Object o) throws RemoteException;
    public void vulFunc(HelloObject o) throws RemoteException;
}
