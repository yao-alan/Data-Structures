/** Written by Alan Yao on 6/20/20 */

public class HashMap <K extends Comparable <K>, V>
{
    private Node[] buckets;
    private double minLoad;
    private double maxLoad;
    private int size;
    private enum resizeDirection {DECREASE, INCREASE};

    private class Node
    {
        private K key;
        private V val;
        private Node next;

        private Node() {
            this.key = null;
            this.val = null;
            this.next = null;
        }
        private Node(K key, V val) {
            this.key = key;
            this.val = val;
            this.next = null;
        }
    }

    public HashMap() {
        buckets = new HashMap.Node[16];
        for (int i = 0; i < buckets.length; i++)
            buckets[i] = new Node((K)"hi", (V)"hi"); /** Sentinel nodes */
        minLoad = 0.25;
        maxLoad = 0.75;
        size = 0;
    }
    public HashMap(int initialSize) {
        buckets = new HashMap.Node[initialSize];
        for (int i = 0; i < buckets.length; i++)
            buckets[i] = new Node((K)"hi", (V)"hi"); /** Sentinel nodes */
        minLoad = 0.25;
        maxLoad = 0.75;
        size = 0;
    }
    public HashMap(int initialSize, double minLoad, double maxLoad) {
        buckets = new HashMap.Node[initialSize];
        for (int i = 0; i < buckets.length; i++)
            buckets[i] = new Node((K)"hi", (V)"hi"); /** Sentinel nodes */
        this.minLoad = minLoad;
        this.maxLoad = maxLoad;
        size = 0;
    }
    /** Patchy solution; ideally wouldn't need to pass in mod */
    private int hash(K key, int mod) {
        return Math.floorMod(key.hashCode(), mod);
    }
    /** Removes all of the HashMap's mappings */
    public void clear() {
        for (int i = 0; i < buckets.length; i++)
            buckets[i] = new Node((K)"hi", (V)"hi"); /** New set of sentinel nodes */
        size = 0;
    }
    /** Returns the value for the specified key, or null if the key has
     * no mapping
     */
    public V get(K key) {
        Node p = buckets[hash(key, buckets.length)];
        while (p != null) {
            if (p.key.equals(key))
                return p.val;
            p = p.next;
        }
        return null;
    }
    /** Returns the number of key-value mappings */
    public int size() {
        return size;
    }
    /** Associates specified value with specified key */
    public void put(K key, V val) {
        put(key, val, this.buckets);
        ++size;
    }
    private void put(K key, V val, Node[] buckets) {
        if (size != 0 && buckets.length / size < maxLoad)
            resize(resizeDirection.INCREASE);
        Node p = buckets[hash(key, buckets.length)];
        while (p.next != null) {
            if (p.key.equals(key))
                return;
            p = p.next;
        }
        if (p.key.equals(key))
            return;
        p.next = new Node(key, val);
    }
    /** Not particularly efficient, since resizing also requires filling in the new
     * HashMap.Node[] array with sentinel nodes; still O(N), but ~2N rather than ~N
     */
    private void resize(resizeDirection dir) {
        Node[] bucketsResized;
        if (dir == resizeDirection.INCREASE) {
            bucketsResized = new HashMap.Node[buckets.length * 2];
            for (int i = 0; i < bucketsResized.length; i++)
                bucketsResized[i] = new Node((K)"hi", (V)"hi");
        }
        else {
            bucketsResized = new HashMap.Node[buckets.length / 2];
            for (int i = 0; i < bucketsResized.length; i++)
                bucketsResized[i] = new Node((K)"hi", (V)"hi");
        }
        /** Rehashing */
        for (int i = 0; i < buckets.length; i++) {
            Node p = buckets[i];
            while (p != null) {
                put(p.key, p.val, bucketsResized);
                p = p.next;
            }
        }
        buckets = bucketsResized;
    }
    /** Removes mapping for the specified key from the map */
    public void remove(K key) {
        if (buckets.length / size > minLoad)
            resize(resizeDirection.DECREASE);
        Node p = buckets[hash(key, buckets.length)]; /** Parent */
        Node c = p.next;             /** Current */
        while (c != null) {
            if (c.key == key) {
                p.next = c.next;
                break;
            }
            p = c;
            c = c.next;
        }
        --size;
    }
    /** Tester method */
    public static void main(String[] args) {
        HashMap <String, Integer> map = new HashMap <>();
        map.put("Bayus", 6);
        map.put("Thing", 0);
        map.remove("Bayus");
        System.out.println(map.get("Thing"));
    }
}