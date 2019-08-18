package com.bing.lib;

import java.util.ArrayList;
import java.util.List;

import static jdk.nashorn.internal.objects.Global.print;

public class MyClass {
    public static void main(String[] args) {
        ListNode listNode1 = new ListNode(1);
        ListNode listNode2 = new ListNode(1);
        ListNode listNode3 = new ListNode(2);
        ListNode listNode4 = new ListNode(3);
        ListNode listNode5 = new ListNode(3);
        ListNode listNode6 = new ListNode(3);

        listNode1.next = listNode2;
        listNode2.next = listNode3;
        listNode3.next = listNode4;
        listNode4.next = listNode5;
        listNode5.next = listNode6;

        MyClass myClass = new MyClass();
        myClass.print(listNode1);
        System.out.println("==============================");
        ListNode listNode = myClass.deleteDuplicates(listNode1);

        myClass.print(listNode);

    }

    private void print(ListNode listNode1) {
        StringBuilder sb = new StringBuilder();
        while (listNode1 != null) {
            sb.append(listNode1.val).append("-------->");
            listNode1 = listNode1.next;
        }
        sb.append("NULL");
        System.out.println(sb.toString());
    }

    /**
     * 翻转链表  需要借助一个临时变量来寸下一个节点  先翻转  在统一向后移动
     *
     * @param head
     * @return
     */
    public ListNode reverseList(ListNode head) {

        ListNode pre = null; //前一个指针节点
        ListNode cur = head;//当前指针节点
        while (cur != null) {
            ListNode temp = cur.next;//临时节点
            cur.next = pre;  //翻转
            pre = cur;
            cur = temp;
        }
        return pre;
    }

    public ListNode middleNode(ListNode head) {
        if (head.next == null) {
            return head;
        }
        int count = getCount(head);
        int retCount = count / 2 + 1;

        for (int i = 0; i < retCount - 1; i++) {
            head = head.next;
        }
        return head;
    }

    private int getCount(ListNode head) {
        int count = 0;
        while (head != null) {
            count++;
            head = head.next;
        }
        return count;
    }


    /**
     * 删除重复的元素
     *
     * @param head
     * @return
     */
    public ListNode deleteDuplicates(ListNode head) {
        List<Integer> integerList = new ArrayList<>();
        ListNode node = head;
        while (node != null) {
            if (integerList.contains(node.val)) {
                remove(head,node);
            } else {
                integerList.add(node.val);
            }
            node = node.next;
        }
        return head;
    } 

    private void remove(ListNode head, ListNode node) {
        while (head!=null){
            if(head.next == node){
                head.next = head.next.next;
            }
            head = head.next;
        }
    }


}
