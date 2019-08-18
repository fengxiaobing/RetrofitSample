package com.bing.lib.design.build;

/**
 * @author fengxb
 * 版本：1.0
 * 创建日期：2019/8/4
 * 描述：抽象建造者
 */
public interface Build {
    public void makeWindow();
    public void makeFollor();

    //房屋建造完之后的返回
    public Room build();

}
