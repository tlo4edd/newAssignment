import java.util.*;
import java.io.*;

public class LinkedList implements Iterable, Serializable {

	private class ListNode implements Serializable {
		public Object data;
		public ListNode next;		//listnode inner class fields
		public ListNode prev;

		public ListNode(Object data, ListNode next, ListNode prev) {
			this.data = data;
			this.next = next;
			this.prev = prev;		//constructor
		}

	}

	private ListNode head = null;
	private ListNode tail = null;
	private int size;				//class fields for doubly linked list

	public LinkedList() {
		size = 0;
	}

	public Iterator iterator() {
		return new LinkedListIterator(this);
	}

	private class LinkedListIterator implements Iterator {	//iterator methods
		private ListNode iterNext;

		public LinkedListIterator(LinkedList theList) {
			iterNext = theList.head;
		}

		public boolean hasNext() {
			return (iterNext != null);
		}

		public Object next() {
			Object value;
			if (iterNext == null) {
				value = null;
			} else {
				value = iterNext.data;
				iterNext = iterNext.next;
			}
			return value;
		}

		public void remove() {
			throw new UnsupportedOperationException("Not supported");
		}
	}

	public void save(LinkedList objToSave, String filename) {
		FileOutputStream fileStrm;
		ObjectOutputStream objStrm;
		try {
			fileStrm = new FileOutputStream(filename);
			objStrm = new ObjectOutputStream(fileStrm);
			objStrm.writeObject(objToSave);					//serializable save method
			objStrm.close();
		} catch (Exception e) {
			throw new IllegalArgumentException("Unable to save object to file");
		}
	}

	public LinkedList load(String filename) throws IllegalArgumentException {
		FileInputStream fileStrm;
		ObjectInputStream objStrm;
		LinkedList inObj = new LinkedList();
		try {
			fileStrm = new FileInputStream(filename);
			objStrm = new ObjectInputStream(fileStrm);		//serializable load method
			inObj = (LinkedList) objStrm.readObject();
			objStrm.close();
			System.out.println("Loaded");
		} catch (ClassNotFoundException e) {
			System.out.println("Class ContainerClass not found" + e);
		} catch (Exception e) {
			throw new IllegalArgumentException("Unable to load object from file");
		}
		return inObj;
	}

	public void insertFirst(Object newValue) {
		size++;
		ListNode newNode = new ListNode(newValue, head, null);		//inserts node into first node position
		if (head != null) {
			head.prev = newNode;
		}
		head = newNode;
		if (tail == null) {
			tail = newNode;

		}

	}

	public void insertLast(Object newValue) {
		size++;
		ListNode newNode = new ListNode(newValue, null, tail);
		if (tail != null) {							//inserts node into last node position
			tail.next = newNode;
		}
		tail = newNode;
		if (head == null) {
			head = newNode;
		}

	}

	public boolean isEmpty() {
		boolean empty;
		if (head == null) {
			empty = true;
		} else {
			empty = false;			//checks to see if list is empty
		}
		return empty;
	}

	public Object peekFirst() {
		Object nodeValue;
		
		  if(head == null)
		  {
		  throw new IllegalArgumentException("Nothing to peek");
		  }
		 							//checks first value in linkedlist and returns it
		 else
		{
			nodeValue = head.data;
		}
		return nodeValue;
	}

	public int getSize() {
		return size;
	}

	public Object peekLast() {
		Object nodeValue;
		ListNode currNode;
		if (head == null) {
			throw new IllegalArgumentException("Nothing to peek");		//gets last node in linkedlist and returns it
		} else {
			currNode = head;
			while (currNode.next != null) {
				currNode = currNode.next;
			}
			nodeValue = currNode.data;
		}
		return nodeValue;
	}

	public Object removeFirst() {
		if (size == 0) {
			throw new IllegalArgumentException("Nothing to remove");		//removes first node in linkedlist and returns it
		} else {
			ListNode newNode = head;
			if (head != tail) {
				head = head.next;
				head.prev = null;
				size--;
			} else {
				head = tail = null;
				size--;
			}

			return newNode.data;
		}
	}

	public Object remove(Object name) {
		int count = 0;
		ListNode curNode = head;
		if (size == 0) {
			throw new IllegalArgumentException("Nothing to remove");		//removes specific node from linkedlist using imported object
		}

		while (curNode.data != name && count < size - 1) {
			curNode = curNode.next;
			count++;

		}

		if (curNode.data != name) {
			throw new IllegalArgumentException("Not Found");
		}

		if (curNode.data == head.data) {
			head = curNode.next;

		} else if (curNode.next == null) {
			curNode.prev.next = null;

		} else {
			curNode.prev.next = curNode.next;
			curNode.next.prev = curNode.prev;
		}
		size--;
		return curNode.data;
	}

	public Object removeLast() {
		if (size == 0) {
			throw new IllegalArgumentException("Nothing to remove"); // removes last node from linkedlist and returns it
		} else {

			ListNode nodeValue = tail;
			if (head != tail) {

				tail = tail.prev;
				tail.next = null;
				size--;
			} else {
				head = tail = null;
				size--;
			}
			return nodeValue.data;
		}
	}

}
