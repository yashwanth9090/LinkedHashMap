package HashMap;
import java.util.InputMismatchException;
public class HashMap<K,V> implements OrderedSet<K,V>, Container<K,V>{

	private	Node<K,V>[] array;
	private ArrayIndexCalculator indexCalculator;
	private LinkedList<K,V> linkedList;
	private int count = 0;


	/**
	 * Parameterized constructor
	 * @param initialSize - Initial size of the set
	 */
	@SuppressWarnings("unchecked")
	public HashMap(int initialSize){
		array = new Node[initialSize];
		indexCalculator = new ArrayIndexCalculator();
		linkedList = new LinkedList<K,V>();
	}
	 
	// Package visible only - for testing purposes
	@SuppressWarnings("unchecked")
	HashMap(int initialSize, ArrayIndexCalculator indexCalculator) {
		array = new Node[initialSize];
		this.indexCalculator = indexCalculator;
		linkedList = new LinkedList<K,V>();
	}
	// Returns the size of the Array.
	public int getLengthOfArray() {
		return array.length;
	}

	@SuppressWarnings("unchecked")
	private void createNewArray(int length) {
		array = new Node[length];
	}
	
	//Iterator
	private class LinkedHashMapIterator implements MyIterator<K,V>{
		private Node<K,V> temp ;
		
		public LinkedHashMapIterator(){
		temp = null;
		}
		@Override
		public boolean hasNext() {
			return temp!=linkedList.getTail();
		}

		@Override
		public Object next() {
			if(temp == null){
				temp = linkedList.getHead() ;
				return temp;
			}
			temp = temp.getNext();
			return temp;
		}
		
	}
	
	@Override
	public MyIterator<K,V> iterator() {
		return new LinkedHashMapIterator();
	}
	
	public void insert(K key,V value) {
		if(value == null||key==null){
			throw new IllegalArgumentException();
		}
		//Checks uniqueness of the set.
		if(equals(key,value)){
			
			return;
			}
		//Doubles the size of Array if it is full.
		if(count == array.length){
			array = doubleArray(array.length);
		}
		count++;
		Node<K,V> newNode = new Node<K,V>(key,value);
		//Using the Hash Function the index of the node is determined.
		int indexOfString = hashFunction(key);
		//If the index is not null, collision is avoided using LinkedList. 
		if(collision(key)){
			linkedList.setTail(avoidCollision(newNode,linkedList.getTail(),indexOfString));
			count--;
			return;
		}
		insertIntoArray(indexOfString, newNode);
		linkedList.insert(newNode);
	}


	Node<K,V>[] doubleArray(int length) {
		@SuppressWarnings("unchecked")
		Node<K,V>[] temp = new Node[length];
		for(int i = 0;i<length;i++){
			temp[i] = array[i];
		}
		createNewArray(2*length);
		@SuppressWarnings("unchecked")
		Node<K,V>[] hashSetArray = new Node[2*length];
		for(int i =0;i<length;i++){
			hashSetArray[i] = temp[i];
		}
		return hashSetArray;
	}

	Node<K,V> avoidCollision(Node<K,V> newNode, Node<K,V> tail, int indexOfString) {
		Node<K,V> temp = array[indexOfString];
		while(temp.getRight()!=null){
			temp = temp.getRight();
		}
		temp.setRight(newNode);
		linkedList.getTail().setNext(newNode);
		newNode.setPrevious(tail);
		linkedList.setTail(linkedList.getTail().getNext());
		return linkedList.getTail();
	}

	private boolean collision(K key) {
		return array[hashFunction(key)]!=null;
	}

	 public boolean equals(K key,V value) {
		if(array[hashFunction(key)]==null){
			return false;
		}else{
			Node<K,V> temp = array[hashFunction(key)];
			while(temp!= null){
				if(temp.getKey().equals(key)){
					temp.setValue(value);
					return true;
				}
				temp =temp.getRight();
			}
			return false;
		}
	}

	 private boolean exists(K key) {
			return array[hashFunction(key)]==null;
	}

	private void insertIntoArray(int indexOfString, Node<K,V> newNode) {
		array[indexOfString] = newNode;
	}

	int hashFunction(K key) {
		return indexCalculator.getIndex(key,array.length);
	}
	
	public void print() {
		Node<K,V> temp = linkedList.getHead();
		if(linkedList.getHead() == null){
			System.out.println("Empty list!");
			return;
		}else{ 
		while(temp!=null){
			System.out.println(temp.getKey()+" "+temp.getValue());
			temp = temp.getNext();
		}
		}
	}
   //Returns true if set is empty
	boolean isEmpty(){
		return linkedList.getHead()==null;
	}
	/**
	 * Removes specified element from the set if it is present.
	 */
	public void delete(K key) {
		/**
		 * If the element doesn't exists 
		 * @throw InputMisMatchException().
		 */
		if(exists(key)){
			throw new InputMismatchException();
		}else{
			int index = hashFunction(key);
			Node<K,V> element = array[index];
			if(isCollisionLinkedList(element)){
				Node<K,V> newNode = deleteLinkedList(element,key);
				array[index] = newNode;
			}else{
				array[index] = null;
				linkedList.delete(element);
			} 
		}
				 
	}

	private Node<K,V> deleteLinkedList(Node<K,V> element,K key) {
		if(element.getKey().equals(key)){
			linkedList.delete(element);
			return element.getNext();
		}else{
			Node<K,V> previous = element;
			Node<K,V> current = element;
			while(current!=null){
				if(current.getKey().equals(key)){
					 linkedList.delete(current);
					previous.setRight(current.getRight());
				}
				previous = current;
				current = current.getNext();
			}
			return element;
		}
		
	}

	//Checks if the element is in collision list.
	private boolean isCollisionLinkedList(Node<K,V> element) {
		return element.getRight()!=null;
		
	}

	
	
}