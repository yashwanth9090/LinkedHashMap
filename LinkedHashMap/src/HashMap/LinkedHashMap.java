package HashMap;

public class LinkedHashMap {
	
	public static void main(String[] args){
		HashMap<Integer,String> newList = new HashMap<>(4);
		
		newList.insert(1,"yashu"); 
		newList.insert(2,"uttam");
		newList.insert(3,"sam"); 
		MyIterator<Integer,String> iter = newList.iterator();
		while(iter.hasNext()){
			@SuppressWarnings("rawtypes")
			Node temp = (Node) iter.next();
			System.out.println(temp.getKey()+" "+temp.getValue());
		}
		
 
		
		
	}
	
}
