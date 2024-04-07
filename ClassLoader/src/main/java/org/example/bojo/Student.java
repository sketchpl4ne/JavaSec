    package org.example.bojo;

    public class Student {
        private String name;
        public static int id;

        static {
            System.out.println("静态代码块");
        }

        public static void staticMethod() {
            System.out.println("静态方法");
        }

        {
            System.out.println("构造代码块");
        }

        public Student() {
            System.out.println("无参构造函数");
        }

        public Student(String name) {
            this.name = name;
            System.out.println("有参构造函数");
        }
    }
