/** Differences between ExtrinsicMinPQ and typical MinPQ
 * 1. Priority value assigned via add()/changePriority(); not intrinsic to item
 * 2. changePriority() can change priorities after items have been added
 * 3. Only one copy of an item should exist at a time
 * 4. Two items with the same priority have arbitrary tie breaks
 */

public interface ExtrinsicMinPQ<T>
{
    /** Returns true if the PQ contains the given itme */
    public boolean contains(T item);
    /** Adds an item of type T with the given priority; if item exists,
     * throw IllegalArgumentException */
    public void add(T item, double priority);
    /** Returns item with smallest priority; if no items exist, 
     * throw NoSuchElementException */
    public T getSmallest();
    /** Removes and returns item with smallest priority; if no such items
     * exist, throw NoSuchElementException */
    public T removeSmallest();
    /** Returns number of items */
    public int size();
    /** Sets priority of given item to the given value; if no such item
     * exists, throw a NoSuchElementException */
    public void changePriority(T item, double priority);
}