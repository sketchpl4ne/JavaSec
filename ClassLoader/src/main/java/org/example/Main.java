package org.example;

import org.example.bojo.Student;
import sun.misc.Unsafe;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Main {
    public static void main(String[] args) throws Exception {
        // 【类加载会导致命令执行的原理】
        // 1. 类的初始化操作，触发静态代码块的执行
//        Student.id = 1;
        // 2. 执行静态函数，静态函数内部会进行类的初始化，进而触发静态代码块的执行
//        Student.staticMethod();
        // 3. Class.forName加载类，函数内部封装了类的初始化过程（可以传参关闭），同样会触发静态代码块的执行
//        Class clazz = Class.forName("org.example.bojo.Student");
        // 4. 类的实例化过程，首先进行类的初始化触发静态代码块的执行，其次执行构造代码块，最后执行构造函数
//        Student s = new Student();

        // 【调试ClassLoader加载类的流程】
//        Student s = new Student("jasper");
//        ClassLoader classLoader = ClassLoader.getSystemClassLoader();
//        Class<?> c = classLoader.loadClass("org.example.bojo.Student");

        // 【任意类加载导致代码执行的利用方式】
//        urlClassLoaderLoadClass();
//        classLoaderDefineClass();
        unsafeDefineClass();
    }
    public static void urlClassLoaderLoadClass() throws Exception{
        // 任意类加载的路径，如果出网就可以是远程URL
        URL httpURL = new URL("http://localhost:7777/");
        //创建一个类加载器
        URLClassLoader urlClassLoader = new URLClassLoader(new URL[]{httpURL});
        //加载
        Class clazz = urlClassLoader.loadClass("org.example.Calc");
        //实例化
        clazz.newInstance();
    }
    public static void classLoaderDefineClass() throws Exception{
        //获取ClassLoader的类对象
        Class loaderClass = ClassLoader.class;
        //获取defineClass()函数对象
        Method defineClassMethod = loaderClass.getDeclaredMethod("defineClass", String.class, byte[].class, int.class, int.class);
        //设置访问权限，确保可以反射
        defineClassMethod.setAccessible(true);
        //读出Calc.class的所有字节流
        byte[] bytes = Files.readAllBytes(Paths.get("D:\\CTF\\Web\\Java\\sec\\ClassLoader\\target\\classes\\org\\example\\Calc.class"));
        //通过ClassLoader对象获取systemClassLoader对象
        ClassLoader sysClassLoader = ClassLoader.getSystemClassLoader();
        //调用systemClassLoad的defineClass()方法，这个方法能加载指定类对象
        Class clazz = (Class) defineClassMethod.invoke(sysClassLoader,"org.example.Calc",bytes,0,bytes.length);
        //实例化指定的类对象
        clazz.newInstance();
    }
    public static void unsafeDefineClass() throws Exception{
        //获取Unsafe类
        Class unsafeClass = Unsafe.class;
        //获取theUnsafe属性
        Field theUnsafe = unsafeClass.getDeclaredField("theUnsafe");
        //设置theUnsafe的访问权限
        theUnsafe.setAccessible(true);
        //强转
        Unsafe unsafe = (Unsafe) theUnsafe.get(null);
        //读出Calc.class的所有字节流
        byte[] bytes = Files.readAllBytes(Paths.get("D:\\CTF\\Web\\Java\\sec\\ClassLoader\\target\\classes\\org\\example\\Calc.class"));
        //通过ClassLoader对象获取systemClassLoader对象
        ClassLoader sysClassLoader = ClassLoader.getSystemClassLoader();
        //加载Calc类
        Class clazz = unsafe.defineClass("org.example.Calc",bytes,0,bytes.length,sysClassLoader,null);
        //实例化
        clazz.newInstance();
    }
}

