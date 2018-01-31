package HashMap;

/**
  Collection used to store list of elements and retains insertion order.
 **/
public interface OrderedSet<K,V> {
		
	/**
	 Inserts elements into the collection and @throws 
	 IllegalArgumentException if the input is null.
	 **/
	public void insert(K key,V value);
	
	/**
	 
	Deletes elements from the list and retains insertion order.
	@throws InputMismatchException if the element doesn't exist.
	 
	 **/
	public void delete(K key);
	
	/**
		Checks uniqueness of the list during insertion.
	 **/
	public boolean equals(K key,V value);
	

	/**
	 * Prints all of the set elements.
	 */
	public void print();
	
	
}
