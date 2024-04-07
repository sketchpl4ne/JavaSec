package org.example;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class Reflect2RCE {
    public static void main(String[] args) throws Exception{
        // 1. 反射执行：java.lang.Runtime.getRuntime().exec("calc");/
        // 1.1 利用getMethod
        RuntimeGetMethod();
        // 1.2 利用getDeclaredMethod
        RuntimeGetDeclaredMethod();

        // 2. 反射执行：Process cmd = new ProcessBuilder(command).start();
        ProcessBuilderGetConstructor();

        // 3. ProcessImpl，这个类本身就是通过反射调用实现命令执行
        ProcessImplExec();
    }
    public static void RuntimeGetMethod() throws Exception{
        Class<?> clazz = Class.forName("java.lang.Runtime");
        Method getRuntime = clazz.getMethod("getRuntime");
        Method exec = clazz.getMethod("exec", String.class);
        Object runtime = getRuntime.invoke(clazz);
        exec.invoke(runtime,"calc"); // invoke反射调用：函数.invoke(函数所属对象, 参数) == 函数所属对象.函数(参数)
    }
    public static void RuntimeGetDeclaredMethod() throws Exception{
        //获取类对象
        Class<?> clazz = Class.forName("java.lang.Runtime");
        //获取私有构造函数对象c
        Constructor c = clazz.getDeclaredConstructor();
        //设置私有构造函数对象可访问
        c.setAccessible(true);
        //创建出runtime对象
        Object runtime = (Runtime) c.newInstance();
        //获取exec函数对象
        Method execMethod = clazz.getMethod("exec", String.class);
        //invoke调用函数，runtime.exec("calc")
        execMethod.invoke(runtime,"calc");
    }

    public static void ProcessBuilderGetConstructor() throws Exception{
        // 获取到ProcessBuilder类对象
        Class<?> clazz = Class.forName("java.lang.ProcessBuilder");
        //获取ProcessBuilder的构造函数
        Constructor c = clazz.getConstructor(List.class);
        // 创建ProcessBuilder实例，并传入初始参数
        Object processBuilder = c.newInstance(Arrays.asList("calc"));
        //获取start()函数对象
        Method start = clazz.getMethod("start");
        start.invoke(processBuilder);
    }

    public static void ProcessImplExec() throws Exception{
        //获取ProcessImpl的类对象
        Class clazz = Class.forName("java.lang.ProcessImpl");
        //获取start函数对象
        Method startMethod = clazz.getDeclaredMethod("start", String[].class, Map.class, String.class, ProcessBuilder.Redirect[].class, boolean.class);
        //start函数是static的，需要设置访问权限
        startMethod.setAccessible(true);
        //设置start的参数列表
        String[] cmds = new String[]{"calc"};
        //invoke调用，start(cmd)
        Process process = (Process) startMethod.invoke(null,cmds,null,".",null,true);

    }
}
