/** Left-Leaning Red Black BST */
/** 1. When inserting, use a red link
 *  2. If there is a right-leaning 3-node, rotate node left
 *  3. If there are consecutive left links, rotate appropriate node right
 *  4. If there are nodes with two red children, color flip the node
 *  5. Fix any additional violations that may come up
 */

public class LLRB<K extends Comparable<K>, V>
{
    private Node root;
    private int size; /** number of nodes */

    private class Node
    {
        K key;
        V val;
        Node left;
        Node right;
        boolean color; /** false = black, true = red */

        private Node() {
            ;
        }
        private Node(K key, V val) {
            this.key = key;
            this.val = val;
            color = false;
        }
        private Node(K key, V val, boolean color) {
            this.key = key;
            this.val = val;
            this.color = color;
        }
    }

    public LLRB() {
        ;
    }
    public LLRB(K key, V val) {
        root = new Node(key, val); 
    }
    public void add(K key, V val) {
        root = add(key, val, root);
        root.color = false;
    }
    private Node add(K key, V val, Node p) {
        if (p == null)
            return new Node(key, val, true);
        int cmp = key.compareTo(p.key);
        if (cmp < 0)
            p.left = add(key, val, p.left);
        else if (cmp > 0)
            p.right = add(key, val, p.right);
        /** imagine adding 4-5-6 in order; if there were no !isRed(left), the program would 
         *  needlessly rotateLeft() and then rotateRight() again, creating the original config
         */
        /** since p is the current node's parent .left or .right, replacing p directly 
         * changes parent pointers */
        if (!isRed(p.left) && isRed(p.right))
            p = rotateLeft(p); 
        if (isRed(p.left) && isRed(p.left.left))
            p = rotateRight(p);
        if (isRed(p.left) && isRed(p.right))
            colorFlip(p);

        return p; /** returns the rebuilt subtree, so that the parent node can connect to it */
    }
    private Node rotateLeft(Node p) {
        boolean tmpColor = p.color;
        p.color = p.right.color;
        p.right.color = tmpColor;

        Node l = p.right.left;
        Node x = p.right;
        x.left = p;
        x.left.right = l;

        return x;
    }
    private Node rotateRight(Node p) {
        boolean tmpColor = p.color;
        p.color = p.left.color;
        p.left.color = tmpColor;

        Node r = p.left.right;
        Node x = p.left;
        x.right = p;
        x.right.left = r;

        return x;
    }
    private void colorFlip(Node p) {
        p.left.color = !p.left.color;
        p.right.color = !p.right.color;
        p.color = !p.color;
    }
    private boolean isRed(Node p) {
        if (p == null)
            return false;
        return p.color;
    }

    public static void main(String[] args) {
        LLRB<String, Integer> tree = new LLRB<>();
        tree.add("2", 2);
        tree.add("6", 6);
        tree.add("3", 3);
        tree.add("4", 4);
        tree.add("1", 1);
        tree.add("5", 5);
        System.out.println(2);
    }
}