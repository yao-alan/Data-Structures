/** Created by Alan Yao on 6/29/20. 
 * Implements a BST alongside a heap in order to provide O(log N)
 * performance on contains(), an impossible task given only a heap. 
 * Also allows the user to change the priority of an item even after
 * it has been added to the heap. */

import java.io.*;
import java.util.*;

public class ArrayHeapMinPQ<T> implements ExtrinsicMinPQ<T>
{
    ArrayList<Node> heap; /** Organized by priority */
    TreeMap<T, Node> itemBST; /** Brings contains to log(N); references ArrayList nodes */

    private class Node 
    {
        T item;
        double priority;

        private Node(T item, double priority) {
            this.item = item;
            this.priority = priority;
        }
    }

    public ArrayHeapMinPQ() {
        heap = new ArrayList<>();
        heap.add(new Node(null, -1.0)); /** sentinel */
        itemBST = new TreeMap<>();
    }

    /** everything hidden is a helper method */
    private Node sink(int index) { /** returns reference to sunken node */
        int i = index;
        int j;
        while (i * 2 <= size()) { /** At least one child exists */
            if (i * 2 + 1 > size()) { /** Only one child */
                if (heap.get(i).priority > heap.get((j = i * 2)).priority)
                    swap(i, j);
            }
            else { /** Two children */
                j = minPriority(i * 2, i * 2 + 1);
                if (heap.get(i).priority > heap.get(j).priority)
                    swap(i, j);
            }
            i = j;
        }
        return heap.get(i);
    }
    private Node swim(int index) { /** returns reference to the node that swam */
        int i = index;
        int j = i / 2;
        while (heap.get(i).priority < heap.get(j).priority) {
            swap(i, j);
            i = j;
            j = i / 2;
        }
        return heap.get(i);
    }
    private void swap(int index1, int index2) {
        Node tmp = heap.get(index1);
        heap.set(index1, heap.get(index2));
        heap.set(index2, tmp);
    }
    private int minPriority(int index1, int index2) { /** Doesn't protect against OutOfBounds */
        if (heap.get(index1).priority < heap.get(index2).priority)
            return index1;
        else
            return index2;
    }

    public boolean contains(T item) { /** O(log N) */
        return itemBST.containsKey(item);
    }
    /** Adds an item of type T with the given priority; if item exists,
     * throw IllegalArgumentException */
    public void add(T item, double priority) { /** O(log N) amortized */
        if (contains(item))
            throw new IllegalArgumentException();
        heap.add(new Node(item, priority)); /** Add to bottom of heap */
        Node x = swim(size()); /** swims newly added node to the proper position */
        itemBST.put(item, x); /** itemBST now points to that new node */
    }
    /** Returns item with smallest priority; if no items exist, 
     * throw NoSuchElementException */
    public T getSmallest() { /** O(log N) */
        if (size() == 0)
            throw new NoSuchElementException();
        return heap.get(1).item;
    }
    /** Removes and returns item with smallest priority; if no such items
     * exist, throw NoSuchElementException */
    public T removeSmallest() { /** O(log N) amortized */
        if (size() == 0)
            throw new NoSuchElementException();
        T smallest = heap.get(1).item;
        swap(1, size());
        heap.remove(size());
        if (size() != 0)
            sink(1);
        return smallest;
    }
    /** Returns number of items */
    public int size() { /** O(log N) */
        return heap.size() - 1;
    }
    /** Sets priority of given item to the given value; if no such item
     * exists, throw a NoSuchElementException */
    public void changePriority(T item, double priority) { /** O(log N) */
        Node x;
        if ((x = itemBST.get(item)) == null)
            throw new NoSuchElementException();
        double initPriority = x.priority;
        x.priority = priority;
        if (initPriority > priority)
            swim(heap.indexOf(x));
        else
            sink(heap.indexOf(x));
    }

    /** Tester methods */
    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new FileReader("HeapData.in"));
        StringTokenizer st = new StringTokenizer(in.readLine());

        ArrayHeapMinPQ<Integer> pq = new ArrayHeapMinPQ<>();
        int size = Integer.parseInt(st.nextToken());
        for (int i = 0; i < size; i++) {
            st = new StringTokenizer(in.readLine());
            int item = Integer.parseInt(st.nextToken());
            double priority = Double.parseDouble(st.nextToken());
            pq.add(item, priority);
        }
        pq.changePriority(162, 1014.8265870792774);
        for (int i = 0; i < size; i++) {
            System.out.print(pq.getSmallest() + ", ");
            System.out.print(pq.removeSmallest() + ", ");
            System.out.println(pq.size());
        }

        in.close();
    }
}
