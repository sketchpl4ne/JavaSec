package Client;

import javax.naming.InitialContext;

public class LoadTomcatLocalClassClient {
    public static void main(String[] args) throws Exception{
        InitialContext initialContext = new InitialContext();
        initialContext.lookup("rmi://localhost:1099/TomcatBypass");
        System.out.println("RMI Client connected...");
    }
}

