package com.bing.lib.design.build;

/**
 * @author fengxb
 * 版本：1.0
 * 创建日期：2019/8/4
 * 描述：设计者（指导者）、
 * 肯定知道房屋怎么设计
 * 指挥工人建造
 */
public class Designer {
    public Room build(Build build){
        build.makeFollor();
        build.makeWindow();
        return build.build();
    }
}
