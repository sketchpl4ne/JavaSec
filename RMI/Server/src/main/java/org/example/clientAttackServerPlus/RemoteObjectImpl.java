package org.example.clientAttackServerPlus;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class RemoteObjectImpl extends UnicastRemoteObject implements RemoteObjectInterface {
    protected RemoteObjectImpl() throws RemoteException {
    }

    @Override
    public String sayHello(String s) throws RemoteException {
        String upS = s.toUpperCase();
        System.out.println(upS);
        return upS;
    }

    @Override
    public void vulFunc(HelloObject o) throws RemoteException {

    }
}
