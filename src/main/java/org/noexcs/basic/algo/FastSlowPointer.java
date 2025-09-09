package org.noexcs.basic.algo;

public class FastSlowPointer {
    public static void main(String[] args) {
        ListNode head;

        for (int i = 0; i < 15; i++) {
            head = createList(i);
            printList(head);
            locatePosition(head);
            System.out.println();
        }
    }

    private static ListNode createList(int length) {
        ListNode dummy = new ListNode(-1);
        ListNode cur = dummy;
        for (int i = 0; i < length; i++) {
            cur.next = new ListNode(i);
            cur = cur.next;
        }
        return dummy.next;
    }

    private static void printList(ListNode head) {
        ListNode cur;
        cur = head;
        while (cur != null) {
            System.out.print(cur.val + "->");
            cur = cur.next;
        }
        System.out.println(" null");
    }


    static class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
            next = null;
        }
    }

    public static void locatePosition(ListNode head) {
        if (head == null || head.next == null) {
            return;
        }
        ListNode slow = head;
        ListNode fast = head;
        while (fast.next != null && fast.next.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        if (fast.next != null) {
            fast = fast.next;
        }

        // slow 指针
        // 元素个数为偶数时指向前半段最后一个，元素个数为奇数时指向中间一个
        System.out.println("slow: " + slow.val);
        // fast 指向链表最后一个元素，
        System.out.println("fast: " + fast.val);
    }

    public static ListNode detectCycle(ListNode head) {
        if (head == null || head.next == null) {
            return null;
        }
        ListNode slow = head;
        ListNode fast = head;
        boolean hasCycle = false;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
            if (slow == fast) {
                hasCycle = true;
                break;
            }
        }
        if (!hasCycle) {
            return null;
        }
        slow = head;
        while (slow != fast) {
            slow = slow.next;
            fast = fast.next;
        }
        return slow;
    }
}
