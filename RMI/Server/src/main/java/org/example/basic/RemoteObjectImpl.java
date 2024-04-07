package org.example.basic;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class RemoteObjectImpl extends UnicastRemoteObject implements RemoteObjectInterface {
    protected RemoteObjectImpl() throws RemoteException {
    }

    @Override
    public String sayHello() throws RemoteException {
        return null;
    }

    @Override
    public String sayHello(String name) throws RemoteException {
        return name + " is so cool!";
    }

    @Override
    public String sayGoodbye() throws RemoteException {
        return null;
    }
}
