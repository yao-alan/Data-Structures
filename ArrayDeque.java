/** Written 6/4/2020 by Alan Yao */

/** add/remove are O(1), except when resizing -- O(1) amortized */
/** get/size are O(1) */
/** starting size of array should be 8 */
/** space complexity O(N); usage factor should be at least 0.25 for length 16+ */
/** circular topology */

public class ArrayDeque <typename> {
	
	private typename items[];
	private int first; /** points at index before first item */
	private int last; /** points at index behind last item */
	private int size;

	public ArrayDeque() {
		items = (typename[]) new Object[8];
		first = (items.length / 2) - 1; /** first/last can't overlap due to invariant */
		last = (items.length / 2);
		size = 0;
	}
	
	public ArrayDeque(ArrayDeque other) {
		items = (typename[])new Object[other.dequeLength()];
		first = other.first;
		last = first + 1;
		size = 0;
		
		for (int i = 0; i < other.size; i++) {
			addLast((typename)other.get(i));
		}
	}

	private void resize(int newSize) {
		typename copy[] = (typename[]) new Object[newSize];
		int j = (newSize / 2) - (size / 2); /** approximate distribution about middle */
		for (int i = first; i < first + size; i++) {
			/** modulus allows overflow to circle back around */
			copy[j] = items[(i + 1 + items.length) % items.length];
			j++;
		}
		first = ((j - size - 1) + newSize) % newSize;
		last = (j + newSize) % newSize;
		items = copy;
	}

	public void addFirst(typename item) {
		if (size == items.length)
			resize(items.length * 2);
		items[first] = item;
		first = (first - 1 + items.length) % (items.length);
		++size;
	}

	public void addLast(typename item) {
		if (size == items.length)
			resize(items.length * 2);
		items[last] = item;
		last = (last + 1 + items.length) % (items.length);
		++size;
	}

	public boolean isEmpty() {
		if (size == 0)
			return true;
		else
			return false;
	}

	public int size() {
		return size;
	}

	public int dequeLength() {
		return items.length;
	}

	public void printDeque() {
		for (int i = first; i < first + size; i++) {
			System.out.print(items[(i + 1 + items.length) % items.length] + " ");
		}
		System.out.println();
	}

	public typename removeFirst() {
		if (items.length >= 4 * size) {
			resize(items.length / 2);
		}
		first = (first + 1 + items.length) % items.length;
		typename var = items[first];
		items[first] = null;
		--size;
		return var;
	}

	public typename removeLast() {
		if (items.length >= 4 * size) {
			resize(items.length / 2);
		}
		last = (last - 1 + items.length) % items.length;
		typename var = items[last];
		items[last] = null;
		--size;
		return var;
	}

	public typename get(int index) {
		return items[(first + index + 1 + items.length) % (items.length)];
	}

	public static void main(String[] args) {
		ArrayDeque <Integer> deque = new ArrayDeque <>();
		for (int i = 0; i < 10; i++) {
			deque.addFirst(i);
		}
		for (int i = 10; i < 20; i++) {
			deque.addLast(i);
		}
		deque.printDeque();
		ArrayDeque <Integer> copy = new ArrayDeque <>(deque);
		copy.printDeque();
	}

}
