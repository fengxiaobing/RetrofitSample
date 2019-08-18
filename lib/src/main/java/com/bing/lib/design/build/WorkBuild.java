package com.bing.lib.design.build;

/**
 * @author fengxb
 * 版本：1.0
 * 创建日期：2019/8/4
 * 描述：具体建造者持有对房子的引用
 */
public class WorkBuild implements Build {
    Room room = new Room("1","2");
    @Override
    public void makeWindow() {
        room.setWindow("法式窗户");
    }

    @Override
    public void makeFollor() {
        room.setFollor("华丽地板");
    }

    @Override
    public Room build() {
        return room;
    }


}
