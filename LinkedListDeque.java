/** Written 6/3/2020 by Alan Yao */

/** add/remove cannot use loops/recursion; must be done in O(1) */
/** get() must use iteration, not recursion */
/** size() must be done in O(1) */
/** space complexity O(N) -- no references to items not in deque */
/** circular topology preferred -- last element points back to sentinel */
/** every node should have 4 connections -- doubly-linked on both sides */

public class LinkedListDeque <typename> {

	private class Node {
		public Node prev;
		public typename item;
		public Node next;

		public Node(Node prev, typename item, Node next) {
			this.prev = prev;
			this.item = item;
			this.next = next;
		}
	}
	
	private Node sentinel;
	private int size; /** cache size for O(1) */

	public LinkedListDeque() {
		sentinel = new Node(null, null, null);
		sentinel.prev = sentinel;
		sentinel.next = sentinel;
		size = 0;
	}

	public LinkedListDeque(typename x) {
		sentinel = new Node(null, null, null);
		sentinel.next = new Node(null, x, sentinel); /** sentinel to node forward */
		sentinel.next.prev = sentinel; /** node to sentinel backward */
		sentinel.prev = sentinel.next;
		size = 1;
	}

	/** creates new deque with same items as other; completely different objects */
	public LinkedListDeque(LinkedListDeque other) {
		sentinel = new Node(null, null, null);
		sentinel.prev = sentinel;
		sentinel.next = sentinel;
		size = 0;	
		for (int i = 0; i < other.size(); i++) {
			/** must cast with typename; since other could be of any type, compiler will
			 * throw an error otherwise */
			addBack((typename)other.get(i)); 
		}
	}

	public void addFront(typename x) {
		/** connect sentinel to new node (forward), new node to sentinel (backward), 
		 * and new node.next to either sentinel (if initially empty list), or second */
		sentinel.next = new Node(sentinel, x, sentinel.next);
		/** connect second node to new node, or sentinel.prev to new node (if initially
		 * empty list) */
		sentinel.next.next.prev = sentinel.next;
		++size;
	}

	public void addBack(typename x) {
		/** connect new node.prev to second-to-last node, new node.next to sentinel, and 
		 * second-to-last node to new node (forward) */
		sentinel.prev.next = new Node(sentinel.prev, x, sentinel);
		/** connect sentinel to new node (forward) */
		sentinel.prev = sentinel.prev.next;
		++size;
	}

	public typename removeFront() {
		if (sentinel.next == null) /** empty list */
			return null;
		else {
			typename val = sentinel.next.item;
			
			sentinel.next.next.prev = sentinel; /** connect second node and sentinel */
			sentinel.next = sentinel.next.next; /** connect sentinel and second node */

			--size;
			return val;
		}
	}

	public typename removeBack() {
		if (sentinel.next == null) /** empty list */
			return null;
		else {
			/** remove all references to last node: 
			 * second-to-last to last, sentinel to last */
			typename val = sentinel.prev.item;

			sentinel.prev.prev.next = sentinel; /** second-to-last to last */
			sentinel.prev = sentinel.prev.prev; /** sentinel to last */
			
			--size;
			return val;
		}
	}
	
	/** return true if deque is empty, otherwise return false */
	public boolean isEmpty() {
		if (size == 0)
			return true;
		else
			return false;
	}

	public int size() {
		return size;
	}

	public typename get(int index) {
		Node p = sentinel.next;
		for (int i = 0; i < index; i++) {
			p = p.next;
		}
		return p.item;
	}

	public typename getRecursive(int index) {
		return (recursiveHelp(sentinel.next, index)).item;
	}

	private Node recursiveHelp(Node current, int index) {
		if (index == 0) 
			return current;
		return recursiveHelp(current.next, --index);
	}

	/** print items in deque from first to last, separated by space */
	public void printDeque() {
		Node p = sentinel;
		for (int i = 0; i < size; i++) {
			p = p.next;
			System.out.print(p.item + " ");
		}
		System.out.println();
	}

	public static void main(String[] args) {
		LinkedListDeque <Integer> deque = new LinkedListDeque <>();
		deque.addFront(1);
		deque.addBack(3);
		deque.addFront(2);
		deque.addBack(4);
		deque.addFront(5);
		LinkedListDeque <Integer> copy = new LinkedListDeque <>(deque);
		copy.printDeque();
	}
}
