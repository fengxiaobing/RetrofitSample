package com.bing.lib;

/**
 * @author fengxb
 * 版本：1.0
 * 创建日期：2019/7/27
 * 描述：递归求和
 * 递归函数就是一个函数，本身就是完成一个功能
 */
public class Sum {
    public static int sum(int[] arr){
       return sum(arr,0);
    }
    //计算arr[l....n) 这个区间的所有的数字的和
    private static int sum(int[] arr,int l){
        if(l == arr.length){
            return 0;   //求解最基本问题
        }else {
            return arr[l]+sum(arr,l+1);   //把原问题转化成更小的问题
        }
    }

    public static void main(String[] args) {
        int[] nums = {1,2,3,4,5,6,7,8};
        System.out.println(sum(nums));
    }
}
