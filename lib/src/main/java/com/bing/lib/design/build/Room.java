package com.bing.lib.design.build;

/**
 * @author fengxb
 * 版本：1.0
 * 创建日期：2019/8/4
 * 描述：产品
 */
public class Room {
    private String window;
    private String follor;

    public Room(String window, String follor) {
        this.window = window;
        this.follor = follor;
    }

    public String getWindow() {
        return window;
    }

    public void setWindow(String window) {
        this.window = window;
    }

    public String getFollor() {
        return follor;
    }

    public void setFollor(String follor) {
        this.follor = follor;
    }
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("地板：   ").append(follor).append("   窗户：").append(window);
        return sb.toString();
    }
}
