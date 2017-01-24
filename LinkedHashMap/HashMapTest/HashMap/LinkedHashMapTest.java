package HashMap;

import static org.junit.Assert.*;

import java.util.InputMismatchException;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InOrder;
import org.mockito.Matchers;
import org.mockito.Mockito;

import HashMap.ArrayIndexCalculator;
import HashMap.HashMap;
import HashMap.MyIterator;
import HashMap.Node;

public class LinkedHashMapTest {

	private HashMap<Integer,String> actualList;
	private LinkedHashMap<Integer,String> expectedList;
	
	@Before
	public void setUp() {
		actualList = Mockito.spy(new HashMap<>(4));
		expectedList = new LinkedHashMap<>();
	}
	
	@Test
	public void testInsertionOrder(){
		actualList.insert(1, "yashu");
		actualList.insert(2, "uttam");
		actualList.insert(3, "samatha");
		InOrder inOrder = Mockito.inOrder(actualList);
		inOrder.verify(actualList).insert(1,"yashu");
		inOrder.verify(actualList).insert(2,"uttam");
		inOrder.verify(actualList).insert(3,"samatha");
	}
	

	@Test
	public void testDuplicates(){
		
		when(actualList.hashFunction(1)).thenReturn(1);
		when(actualList.hashFunction(2)).thenReturn(2);

		actualList.insert(1, "yashu");
		actualList.insert(1, "yashu");
		actualList.insert(3, "samatha");
		
		Mockito.verify(actualList,Mockito.times(5)).hashFunction(Matchers.eq(1));
		Mockito.verify(actualList,Mockito.times(3)).hashFunction(Matchers.eq(3));
		
		expectedList.put(1, "yashu");
		expectedList.put(1, "yashu");
		expectedList.put(3, "samatha");
		verifyLists(expectedList,actualList);
	}

	@Test
	public void testEmptyList(){
		assertTrue(actualList.isEmpty());
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testNull(){
		actualList.insert(null,null);
	}
	
	
	@Test
	public void testDeletingAllElements(){
		actualList.insert(1, "yashu");
		actualList.insert(2, "uttam");
		actualList.insert(3, "samatha");
		actualList.delete(1);
		actualList.delete(2);
		actualList.delete(3);
		assertTrue(actualList.isEmpty());
	}
	
	@Test(expected = InputMismatchException.class)
	public void testDeletionOfNonexistentElement(){
		actualList.insert(1,"yashu");
		actualList.insert(2,"sam");
		actualList.delete(3); 
		
	}
	@Test
	public void testInsert(){
		ArgumentCaptor<Integer> argInt = ArgumentCaptor.forClass(Integer.class);
		ArgumentCaptor<String> arg = ArgumentCaptor.forClass(String.class);
		actualList.insert(1,"yashu");
		Mockito.verify(actualList).insert(argInt.capture(),arg.capture());
		assertEquals("yashu",arg.getValue());
		assertEquals(Integer.valueOf(1),argInt.getValue());
	}
	@Test
	public void testDeletionFromCollsionListTail(){
		
		when(actualList.hashFunction(1)).thenReturn(1);
		when(actualList.hashFunction(2)).thenReturn(1);
		when(actualList.hashFunction(3)).thenReturn(2);
		
		actualList.insert(1, "yashu");
		actualList.insert(2, "uttam");
		actualList.insert(3, "samatha");
				
		Mockito.verify(actualList, Mockito.times(3)).hashFunction(Matchers.eq(1));
		Mockito.verify(actualList, Mockito.times(4)).hashFunction(Matchers.eq(2));
		Mockito.verify(actualList, Mockito.times(3)).hashFunction(Matchers.eq(3));
		
		
		
		
		actualList.delete(2);
		expectedList.put(1, "yashu");
		expectedList.put(2, "uttam");
		expectedList.put(3, "samatha");
		
		expectedList.remove(2);
		verifyLists(expectedList,actualList);	
	}
	
	
	@Test
	public void testDeletionFromCollsionListTail2(){
		ArrayIndexCalculator indexCalculator = Mockito.mock(ArrayIndexCalculator.class);
		actualList = new HashMap<>(4, indexCalculator);
		
		when(indexCalculator.getIndex(1, 4)).thenReturn(1);
		when(indexCalculator.getIndex(2, 4)).thenReturn(1);
		when(indexCalculator.getIndex(3, 4)).thenReturn(2);
		
		actualList.insert(1, "yashu");
		actualList.insert(2, "uttam");
		actualList.insert(3, "samatha");
		
		
		Mockito.verify(indexCalculator,Mockito.times(3)).getIndex(1,4);
		Mockito.verify(indexCalculator,Mockito.times(4)).getIndex(2,4);
		Mockito.verify(indexCalculator,Mockito.times(3)).getIndex(3,4);
		
		
		
		actualList.delete(2);
		expectedList.put(1, "yashu");
		expectedList.put(2, "uttam");
		expectedList.put(3, "samatha");
		expectedList.remove(2);
		verifyLists(expectedList,actualList);	
	}
	
	@Test
	public void testDeletionFromCollsionListMiddle(){
		when(actualList.hashFunction(1)).thenReturn(1);
		when(actualList.hashFunction(2)).thenReturn(1);
		when(actualList.hashFunction(3)).thenReturn(1);
		
		actualList.insert(1, "yashu");
		actualList.insert(2, "uttam");
		actualList.insert(3, "samatha");
		
		Mockito.verify(actualList,Mockito.times(3)).hashFunction(Matchers.eq(1));
		Mockito.verify(actualList,Mockito.times(4)).hashFunction(Matchers.eq(2));
		Mockito.verify(actualList,Mockito.times(4)).hashFunction(Matchers.eq(3));
		
		actualList.delete(2);
		expectedList.put(1, "yashu");
		expectedList.put(2, "uttam");
		expectedList.put(3, "samatha");
		expectedList.remove(2);
		verifyLists(expectedList,actualList);
	}
	
	@Test
	public void testDeletionFromCollsionListHead(){
		when(actualList.hashFunction(1)).thenReturn(1);
		when(actualList.hashFunction(2)).thenReturn(1);
		when(actualList.hashFunction(3)).thenReturn(1);
		
		actualList.insert(1, "yashu");
		actualList.insert(2, "uttam");
		actualList.insert(3, "samatha");
		
		Mockito.verify(actualList,Mockito.times(3)).hashFunction(Matchers.eq(1));
		Mockito.verify(actualList,Mockito.times(4)).hashFunction(Matchers.eq(2));
		Mockito.verify(actualList,Mockito.times(4)).hashFunction(Matchers.eq(3));
		
		actualList.delete(1);
		expectedList.put(1, "yashu");
		expectedList.put(2, "uttam");
		expectedList.put(3, "samatha");
		expectedList.remove(1);
		verifyLists(expectedList,actualList);
	}
	
	@Test
	public void testDeletionFromOrderingListTail(){
		when(actualList.hashFunction(1)).thenReturn(1);
		when(actualList.hashFunction(2)).thenReturn(2);
		when(actualList.hashFunction(3)).thenReturn(3);
		
		actualList.insert(1, "yashu");
		actualList.insert(2, "uttam");
		actualList.insert(3, "samatha");
		
		Mockito.verify(actualList,Mockito.times(3)).hashFunction(Matchers.eq(1));
		Mockito.verify(actualList,Mockito.times(3)).hashFunction(Matchers.eq(2));
		Mockito.verify(actualList,Mockito.times(3)).hashFunction(Matchers.eq(3));
		
		actualList.delete(3);
		expectedList.put(1, "yashu");
		expectedList.put(2, "uttam");
		expectedList.put(3, "samatha");
		expectedList.remove(3);
		verifyLists(expectedList,actualList);
	}
	
	@Test
	public void testDeletionFromOrderingListMiddle(){
		when(actualList.hashFunction(1)).thenReturn(1);
		when(actualList.hashFunction(2)).thenReturn(2);
		when(actualList.hashFunction(3)).thenReturn(3);
		
		actualList.insert(1, "yashu");
		actualList.insert(2, "uttam");
		actualList.insert(3, "samatha");
		
		Mockito.verify(actualList,Mockito.times(3)).hashFunction(Matchers.eq(1));
		Mockito.verify(actualList,Mockito.times(3)).hashFunction(Matchers.eq(2));
		Mockito.verify(actualList,Mockito.times(3)).hashFunction(Matchers.eq(3));
		
		actualList.delete(2);
		expectedList.put(1, "yashu");
		expectedList.put(2, "uttam");
		expectedList.put(3, "samatha");
		expectedList.remove(2);
		verifyLists(expectedList,actualList);
	}
	
	@Test
	public void testDeletionFromOrderingListHead(){
		when(actualList.hashFunction(1)).thenReturn(1);
		when(actualList.hashFunction(2)).thenReturn(2);
		when(actualList.hashFunction(3)).thenReturn(3);
		
		actualList.insert(1, "yashu");
		actualList.insert(2, "uttam");
		actualList.insert(3, "samatha");
		
		Mockito.verify(actualList,Mockito.times(3)).hashFunction(Matchers.eq(1));
		Mockito.verify(actualList,Mockito.times(3)).hashFunction(Matchers.eq(2));
		Mockito.verify(actualList,Mockito.times(3)).hashFunction(Matchers.eq(3));
		
		actualList.delete(1);
		expectedList.put(1, "yashu");
		expectedList.put(2, "uttam");
		expectedList.put(3, "samatha");
		expectedList.remove(1);
		verifyLists(expectedList,actualList);
	}
	
	@Test
	public void testDeletingOrderingLinkedListElements(){
		when(actualList.hashFunction(1)).thenReturn(1);
		when(actualList.hashFunction(2)).thenReturn(2);
		when(actualList.hashFunction(3)).thenReturn(3);
		actualList.insert(1, "yashu");
		actualList.insert(2, "uttam");
		actualList.insert(3, "samatha");
		
		Mockito.verify(actualList,Mockito.times(3)).hashFunction(Matchers.eq(1));
		Mockito.verify(actualList,Mockito.times(3)).hashFunction(Matchers.eq(2));
		Mockito.verify(actualList,Mockito.times(3)).hashFunction(Matchers.eq(3));
		
		actualList.delete(1);
		actualList.delete(2);
		actualList.delete(3);
		expectedList.put(1, "yashu");
		expectedList.put(2, "uttam");
		expectedList.put(3, "samatha");
		expectedList.remove(1);		
		expectedList.remove(2);
		expectedList.remove(3);
		verifyLists(expectedList,actualList);
	}
	
	@Test
	public void testParameterType(){
		actualList.insert(1,"yashu");
		Mockito.verify(actualList).insert(Mockito.anyInt(),Mockito.anyString());
	}
	
	@Test
	public void testDoubleArraySize(){
		when(actualList.hashFunction(1)).thenReturn(0);
		when(actualList.hashFunction(2)).thenReturn(1);
		when(actualList.hashFunction(3)).thenReturn(2);
		when(actualList.hashFunction(4)).thenReturn(3);
		actualList.insert(1, "yashu");
		actualList.insert(2, "uttam");
		actualList.insert(3, "samatha");
		actualList.insert(4, "cherry");
		actualList.insert(5, "divya");
		Mockito.verify(actualList).doubleArray(4);
		assertEquals(8,actualList.getLengthOfArray());
		
	}

	@Test
	public void testDuplicateKey(){
		when(actualList.hashFunction(1)).thenReturn(1);
		when(actualList.hashFunction(2)).thenReturn(2);

		actualList.insert(1, "yashu");
		actualList.insert(1, "uttam");
		actualList.insert(3, "samatha");
		
		Mockito.verify(actualList,Mockito.times(5)).hashFunction(Matchers.eq(1));
		Mockito.verify(actualList,Mockito.times(3)).hashFunction(Matchers.eq(3));
		
		expectedList.put(1, "yashu");
		expectedList.put(1, "uttam");
		expectedList.put(3, "samatha");
		verifyLists(expectedList,actualList);
	}
	
	@Test
	public void testDuplicateValue(){
		actualList.insert(1, "yashu");
		actualList.insert(2, "yashu");
		actualList.insert(3, "samatha");
		expectedList.put(1, "yashu");
		expectedList.put(2, "yashu");
		expectedList.put(3, "samatha");
		verifyLists(expectedList,actualList);
		
	}
	
	
	private void verifyLists(LinkedHashMap<Integer,String> expected, HashMap<Integer,String> actual) {
		MyIterator<Integer,String> actualIter = actual.iterator();
		Set<Entry<Integer, String>> entries = expected.entrySet();
		Iterator<Entry<Integer, String>> ExpecedIter = entries.iterator();
		while(ExpecedIter.hasNext()){
			@SuppressWarnings("rawtypes")
			Node temp = (Node) actualIter.next();
			
			Map.Entry<Integer, String>  me = (Map.Entry<Integer, String>)ExpecedIter.next();
			    assertEquals(me.getKey(),temp.getKey());
			    assertEquals(me.getValue(),temp.getValue());
			
		}
		
		
	}	

	
}
