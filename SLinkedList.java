public class SLinkedList {
    private class ListNode {
        int val;
        ListNode next;
        private ListNode(int val, ListNode next) {
            this.val = val;
            this.next = next;
        }
    }
    ListNode sentinel; /** First object at sentinel.next */
    public SLinkedList() {
        sentinel = new ListNode(5, null);
    }
    public SLinkedList(int x) {
        sentinel = new ListNode(5, null);
        sentinel.next = new ListNode(x, null);
    }
    public void addLast(int x) {
        ListNode p = sentinel;
        while (p.next != null)
            p = p.next;
        p.next = new ListNode(x, null);
    }
    public void reverse() {
        if (sentinel.next == null)
            ;
        else {       
            ListNode p = reverseRecursive(sentinel.next);
            p.next = null;
        }
    }
    private ListNode reverseRecursive(ListNode node) {
        if (node.next == null) {
            sentinel.next = node;
            return node;
        }
        reverseRecursive(node.next).next = node;
        return node;
    }
    public int size() {
        return sizeRecursive(sentinel.next);
    }
    private int sizeRecursive(ListNode node) {
        if (node.next == null)
            return 1;
        return 1 + sizeRecursive(node.next);
    }
    public void print() {
        ListNode p = sentinel.next;
        while (p.next != null) {
            System.out.print(p.val + " ");
            p = p.next;
        }
        System.out.println(p.val);
    }
    public static void main(String[] args) {
        SLinkedList list = new SLinkedList();
        //for (int i = 1; i <= 5; i++) {
            //list.addLast(i);
        //}
        list.reverse();
        list.print();
    }
}