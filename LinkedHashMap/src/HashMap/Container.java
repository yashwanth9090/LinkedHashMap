package HashMap;

public interface Container<K,V> {
	/**
	 * Creates and returns the Iterator.
	 * @return
	 */
	public MyIterator<K,V> iterator();
}
