package HashMap;
/**
 * Collection uses Doubly LinkedList.
 * Key - To store the Key.
 * Value - To store the Value.
 * previous - Pointer to the previous node.
 * next - Pointer to the next node.
 * right - Pointer to the node in collision list.
 * @author Yashwanth
 *
 * @param <T>
 */
public class Node<K,V> {
	private K key;
	private V value;
	private Node<K,V> previous;
	private	Node<K,V> next;
	private	Node<K,V> right;
	

	public V getValue() {
		return value;
	}

	public K getKey() {
		return key;
	}

	public Node<K,V> getPrevious() {
		return previous;
	}


	public void setPrevious(Node<K,V> previous) {
		this.previous = previous;
	}


	public Node<K,V> getNext() {
		return next;
	}


	public void setNext(Node<K,V> next) {
		this.next = next;
	}


	public Node<K,V> getRight() {
		return right;
	}


	public void setRight(Node<K,V> right) {
		this.right = right;
	}


	public void setKey(K key) {
		this.key = key;
	}
	
	public void setValue(V value) {
		this.value = value;
	}


	Node(K key,V value){
		this.key  = key;
		this.value = value ;
	}
	

}
