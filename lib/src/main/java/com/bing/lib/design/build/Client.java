package com.bing.lib.design.build;

/**
 * @author fengxb
 * 版本：1.0
 * 创建日期：2019/8/4
 * 描述：
 */
public class Client {
    public static void main(String[] args) {
        Build build = new WorkBuild();
        Designer designer = new Designer();
        Room room = designer.build(build);
        Room room1 = new Room("1","2");
        Room room2 = new Room("1","2");

        String aa = new String("aa");
        String bb = new String("aa");

        System.out.println(room1.equals(room2));

    }
}
