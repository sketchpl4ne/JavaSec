package Utils;

import java.io.*;
import java.util.Base64;

public class SerialUtils {

    public static void serialize(Object o) throws Exception{
        FileOutputStream fos = new FileOutputStream("object.ser");
        ObjectOutputStream os = new ObjectOutputStream(fos);
        os.writeObject(o);

        System.out.println("序列化完成...");
    }
    public static String serializeB64(Object o) throws Exception{
        // 输出Base64后的对象
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream out = new ObjectOutputStream(baos);
        out.writeObject(o);
        out.close();
        String res = Base64.getEncoder().encodeToString(baos.toByteArray());
        System.out.println(res);
        System.out.println("序列化完成...");
        return res;
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
