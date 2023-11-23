package Client;

import Client.Pojo.RemoteObjectInterface;

import javax.naming.InitialContext;

public class LDAPClient {
    public static void main(String[] args) throws Exception{
        InitialContext initialContext = new InitialContext();
        RemoteObjectInterface remoteObject = (RemoteObjectInterface)initialContext.lookup("ldap://127.0.0.1:1234/Calc");
        System.out.println(remoteObject.sayHello("I'm Jasper you motherfucker."));

    }
}
