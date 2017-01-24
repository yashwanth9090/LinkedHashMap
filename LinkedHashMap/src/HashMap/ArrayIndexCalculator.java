package HashMap;

public class ArrayIndexCalculator {
	public int getIndex(Object obj, int arrayLength) {
		int hash = obj.hashCode();
		return Math.abs(hash%arrayLength);  
	}

}
