package HashMap;

 class LinkedList<K,V> {
	private Node<K,V> head;
	private Node<K,V> tail;

	public Node<K,V> getHead() {
		return head;
	}
	public void setHead(Node<K,V> head) {
		this.head = head;
	}
	public Node<K,V> getTail() {
		return tail;
	}
	public void setTail(Node<K,V> tail) {
		this.tail = tail;
	}

	 void insert(Node<K,V> newNode){
		if(head == null){
			head = newNode;
			tail = newNode;
		}else{
			tail.setNext(newNode); 
			newNode.setPrevious(tail);
			tail = tail.getNext();
		}
	}
	
	 void delete(Node<K,V> element){
		if(element.getPrevious()!=null){
			if(element.getNext()!=null){ 
				element.getPrevious().setNext(element.getNext());
				element.getNext().setPrevious(element.getPrevious());
			}else{
				element.getPrevious().setNext(null);
				tail = element.getPrevious();
			}
		}else{
			if(element.getNext()!=null){
				head = element.getNext();
				element.getNext().setPrevious(null);
			}else{
				head = null;
				tail = null;
			}	
		}
		
	}

}
