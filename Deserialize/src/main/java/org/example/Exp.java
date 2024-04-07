package org.example;

import org.example.HackInfo;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class Exp {
    public static void main(String[] args) throws Exception{
        HackInfo hackInfo = new HackInfo();
        serialize(hackInfo);
        unserialize();
    }
    public static void serialize(Object o) throws Exception{
        FileOutputStream fos = new FileOutputStream("object.ser");
        ObjectOutputStream os = new ObjectOutputStream(fos);
        os.writeObject(o);

        System.out.println("序列化完成...");
    }

    public static void unserialize() throws Exception{
        FileInputStream fis = new FileInputStream("object.ser");
        ObjectInputStream ois = new ObjectInputStream(fis);
        //反序列化执行readObject()方法
        Object o =  ois.readObject();
        ois.close();
        fis.close();

        System.out.println("反序列化完成...");
    }
}
