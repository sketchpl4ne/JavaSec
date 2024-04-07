package org.example;

import org.example.pojo.Producer;

public class ProducerDemo {
    public static void main(String[] args) {
        Producer pro = new Producer();
        pro.sendhello2ActiveMq("hello motherfucker...");
    }
}
