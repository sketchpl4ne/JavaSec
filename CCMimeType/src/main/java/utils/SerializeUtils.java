package utils;

import java.io.*;
import java.util.Base64;

public class SerializeUtils {
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
    public static String serializeBase64(Object object) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
        objectOutputStream.writeObject(object);
        objectOutputStream.close();
        byte[] serializedBytes = byteArrayOutputStream.toByteArray();
        String res = Base64.getEncoder().encodeToString(serializedBytes);
        return res;
    }
    public static Object unserializeBase64(String base64String) throws IOException, ClassNotFoundException {
        byte[] decodedBytes = Base64.getDecoder().decode(base64String);
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(decodedBytes);
        ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
        Object deserializedObject = objectInputStream.readObject();
        objectInputStream.close();
        return deserializedObject;
    }




}