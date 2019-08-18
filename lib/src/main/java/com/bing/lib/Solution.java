package com.bing.lib;

/**
 * @author fengxb
 * 版本：1.0
 * 创建日期：2019/7/27
 * 描述：
 */
public class Solution {

    public Node head;

    public static void main(String[] args) {
        Solution solution = new Solution();
        Node node = solution.add(2, 666);
        System.out.println(node);
    }

    public Solution() {
        int[] nums = {1, 2, 3, 4, 5};
        Node listNode = new Node(nums);
        head = listNode;
        System.out.println(listNode);
    }

    public ListNode remove(ListNode head, int val) {
        if (head == null) {
            return null;
        }

        ListNode res = remove(head.next, val);
        if (head.val == val) {
            return res;
        } else {
            head.next = res;
            return head;
        }
    }

  /*  private   Node add(int index,int e){
     Node pre = head;
        for (int i = 0; i < index-1; i++) {
            pre = pre.next;
        }
        Node node = new Node(e);
        node.next = pre.next;
        pre.next = node;
        return head;
    }*/

    private   Node add(int index,int e){

        return head;
    }

    private class Node {
        int val;
        Node next;

        public Node(int x) {
            val = x;
        }


        public Node(int[] arr) {
            this.val = arr[0];
            Node cur = this;
            for (int i = 1; i < arr.length; i++) {
                cur.next = new Node(arr[i]);
                cur = cur.next;
            }
        }


        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            Node cur = this;
            while (cur != null) {
                sb.append(cur.val + "->");
                cur = cur.next;
            }
            sb.append("NULL");
            return sb.toString();
        }
    }

}
