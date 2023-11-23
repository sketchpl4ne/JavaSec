package Client;

import Client.Pojo.RemoteObjectInterface;
import javax.naming.InitialContext;

public class RMIClient {
    public static void main(String[] args) throws Exception{
        InitialContext initialContext = new InitialContext();
        RemoteObjectInterface remoteObject = (RemoteObjectInterface) initialContext.lookup("rmi://localhost:1099/remoteObj");
        System.out.println(remoteObject.sayHello("I'm Jasper you motherfucker."));
    }
}
