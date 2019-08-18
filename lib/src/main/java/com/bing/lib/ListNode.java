package com.bing.lib;

/**
 * @author fengxb
 * 版本：1.0
 * 创建日期：2019/7/26
 * 描述：
 */

public class ListNode {
    int val;
    ListNode next;

   public ListNode(int x) {
        val = x;
    }


    public ListNode(int[] arr){
      this.val = arr[0];
      ListNode cur = this;
      for(int i = 1;i< arr.length;i++){
          cur.next = new ListNode(arr[i]);
          cur = cur.next;
      }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        ListNode cur = this;
        while (cur != null){
            sb.append(cur.val+"->");
            cur = cur.next;
        }
        sb.append("NULL");
        return sb.toString();
    }
}
