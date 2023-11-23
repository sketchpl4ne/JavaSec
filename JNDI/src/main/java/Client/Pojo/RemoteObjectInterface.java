package Client.Pojo;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RemoteObjectInterface extends Remote {
    public String sayHello(String s) throws RemoteException;
}