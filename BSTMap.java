public class BSTMap <K extends Comparable <K>, V> {

    private class Node {
        private K key;
        private V val;
        private Node left, right;
        private int size;

        private Node(K key, V val) {
            this.key = key;
            this.val = val;
        }
    }

    Node sentinel; /** sentinel.left points to root node */
    private int size;

    public BSTMap() {
        sentinel = new Node(null, null);
        size = 0;
    }
    /** Removes all the BSTMap's mappings */
    public void clear() {

    }
    /** Returns the value for which the specified key is map, or null if
     * there is no mapping for the key
     */
    public V get(K key) {
        return getRecursive(key, sentinel.left);
    }
    private V getRecursive(K key, Node current) {
        /** No protection against current == null */
        if (key == null)
            throw new IllegalArgumentException();
        else {
            int cmp = key.compareTo(current.key);
            if (cmp < 0) {
                return getRecursive(key, current.left);
            }
            else if (cmp > 0) {
                return getRecursive(key, current.right);
            }
            else
                return current.val;
        }
    }
    /** Returns number of key-value mappings */
    //public int size() {

    //}
    /** Associates specified value with specified key */
    public void put(K key, V val) {
        sentinel.left = putRecursive(key, val, sentinel.left);
    }
    private Node putRecursive(K key, V val, Node current) {
        /** Previous node was leaf */
        if (current == null)
            return new Node(key, val);
        else {
            int cmp = key.compareTo(current.key);
            /** Rebuilds BST from bottom up */
            if (cmp < 0)
                current.left = putRecursive(key, val, current.left);
            else if (cmp > 0)
                current.right = putRecursive(key, val, current.right);
        }
        /** If key was found */
        return current;
    }
    /** Returns a Set view of keys contained in map */
    /** Set<K> keySet(); */
    /** Removes mapping for specified key from the map */
    public void remove(K key) {
        if (key == null)
            throw new IllegalArgumentException();
        else 
            sentinel.left = remove(key, sentinel.left);
    }
    /** Hibbard Deletion implementation not particularly clean */
    private Node remove(K key, Node current) {
        int cmp = key.compareTo(current.key);
        if (cmp < 0)
            current.left = remove(key, current.left);
        else if (cmp > 0)
            current.right = remove(key, current.right);
        else {
            /** Node has no children */
            if (current.left == null && current.right == null)
                return null;
            /** Node has one child */
            else if (current.left == null)
                return current.left;
            else if (current.right == null)
                return current.right;
            /** Node has two children -- Hibbard Deletion */
            else {
                Node predecessor = current.left;
                Node preParent = current;
                while (predecessor.right != null) {
                    preParent = predecessor;
                    predecessor = predecessor.right;
                }
                /** Eliminates predecessor node */
                if (preParent == current)
                    preParent.left = null;
                else    
                    preParent.right = null;
                /** Copies predecessor information to current */
                current.key = predecessor.key; 
                current.val = predecessor.val;
                current.size = predecessor.size;
                return current;
            }
        }
        return current;
    }
    
    /** Removes entry for specified key only if currently mapped to
     * specified value
     */
    //public V remove(K key, V val) {

    //}
    /** Tester method */
    public static void main(String[] args) {
        BSTMap <String, Integer> map = new BSTMap<>();
        map.put("pie", 2);
        map.put("this", 3);
        map.put("apple", 1);
        map.put("banana", 10);
        map.put("zebra", 6);
        map.put("suit", 5);
        map.remove("pie");
        System.out.println(map.get("apple"));
    }
}