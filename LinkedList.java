/**
 * Represents a list of Nodes. 
 */
public class LinkedList {
	
	private Node first; // pointer to the first element of this list
	private Node last;  // pointer to the last element of this list
	private int size;   // number of elements in this list
	
	/**
	 * Constructs a new list.
	 */ 
	public LinkedList () {
		first = null;
		last = first;
		size = 0;
	}
	
	/**
	 * Gets the first node of the list
	 * @return The first node of the list.
	 */		
	public Node getFirst() {
		return this.first;
	}

	/**
	 * Gets the last node of the list
	 * @return The last node of the list.
	 */		
	public Node getLast() {
		return this.last;
	}
	
	/**
	 * Gets the current size of the list
	 * @return The size of the list.
	 */		
	public int getSize() {
		return this.size;
	}
	
	/**
	 * Gets the node located at the given index in this list. 
	 * 
	 * @param index
	 *        the index of the node to retrieve, between 0 and size
	 * @throws IllegalArgumentException
	 *         if index is negative or greater than the list's size
	 * @return the node at the given index
	 */		
	public Node getNode(int index) {
		if (index < 0 || index > size) {
			throw new IllegalArgumentException(
					"index must be between 0 and size");
		}
		if (size == 0) {
			return null;
		}
		if (index == 0) {
			return first;
		}
		else{
			Node pointer = this.first;
			for(int i = 0;i < index;i++){///לא יחרוג כי indez <= size
				pointer = pointer.next;
			}
			return pointer;
		}
	}
	
	/**
	 * Creates a new Node object that points to the given memory block, 
	 * and inserts the node at the given index in this list.
	 * <p>
	 * If the given index is 0, the new node becomes the first node in this list.
	 * <p>
	 * If the given index equals the list's size, the new node becomes the last 
	 * node in this list.
     * <p>
	 * The method implementation is optimized, as follows: if the given 
	 * index is either 0 or the list's size, the addition time is O(1). 
	 * 
	 * @param block
	 *        the memory block to be inserted into the list
	 * @param index
	 *        the index before which the memory block should be inserted
	 * @throws IllegalArgumentException
	 *         if index is negative or greater than the list's size
	 */
	public void add(int index, MemoryBlock block) {
		if (index < 0 || index > size) {
			throw new IllegalArgumentException(
					"index must be between 0 and size");
		}
		Node newNode = new Node(block);
		if (index == 0) {
			addFirst(block);
		}
		else if (index == size) {
			addLast(block);
		}
		else if (index > 0 && index < size) {
			Node pointer = getNode(index - 1);
			newNode.next = pointer.next;
			pointer.next = newNode;
			size++;
		}
	}

	/**
	 * Creates a new node that points to the given memory block, and adds it
	 * to the end of this list (the node will become the list's last element).
	 * 
	 * @param block
	 *        the given memory block
	 */
	public void addLast(MemoryBlock block) {
		Node newNode = new Node(block); // Create a new node
        if (size == 0) {
            first = newNode;
            last = newNode;
        } else {
            last.next = newNode;
            last = newNode;
        }
        size++; 
	}
	
	/**
	 * Creates a new node that points to the given memory block, and adds it 
	 * to the beginning of this list (the node will become the list's first element).
	 * 
	 * @param block
	 *        the given memory block
	 */
	public void addFirst(MemoryBlock block) {
		Node newNode = new Node(block); // Create a new node
        if (size == 0) {
            first = newNode;
            last = newNode;
        } else {
            newNode.next = first;
            first = newNode;
        }
        size++; 
	}

	/**
	 * Gets the memory block located at the given index in this list.
	 * 
	 * @param index
	 *        the index of the retrieved memory block
	 * @return the memory block at the given index
	 * @throws IllegalArgumentException
	 *         if index is negative or greater than or equal to size
	 */
	public MemoryBlock getBlock(int index) {
		if (size == 0) {
			throw new IllegalArgumentException(
					"index must be between 0 and size");
		}
		return getNode(index).block;
	}	

	/**
	 * Gets the index of the node pointing to the given memory block.
	 * 
	 * @param block
	 *        the given memory block
	 * @return the index of the block, or -1 if the block is not in this list
	 */
	public int indexOf(MemoryBlock block) {
		Node pointer = this.first;
		for(int i = 0;i < this.size;i++){
			if (pointer.block == block) {
				return i;
			}
			pointer = pointer.next;
		}
		return -1;
	}

	/**
	 * Removes the given node from this list.	
	 * 
	 * @param node
	 *        the node that will be removed from this list
	 */
	public void remove(Node node) {

        if (node == first) {
            // Remove the first node
            first = first.next;
            if (first == null) {
                last = null; // List becomes empty
            }
            size--;
            return;
        }

        // Traverse the list to find the node
        Node current = first;
        while (current != null && current.next != node) {
            current = current.next;
        }

        if (current == null || current.next == null) {
            throw new IllegalArgumentException("Node not found in the list");
        }
        // Remove the node
        current.next = node.next;
        if (node == last) {
            // If the node is the last node, update the last pointer
            last = current;
        }

        size--;
    }

	/**
	 * Removes from this list the node which is located at the given index.
	 * 
	 * @param index the location of the node that has to be removed.
	 * @throws IllegalArgumentException
	 *         if index is negative or greater than or equal to size
	 */
		public void remove(int index) {

			if (size == 0) {
				throw new IllegalArgumentException(
						"index must be between 0 and size");
			}
			else if (index < 0 || index >= size) {
				throw new IllegalArgumentException("Index out of bounds: " + index);
			}

			else if (index == 0) {
				remove(first);
				return;
			}
			else if (index == size - 1) {
				Node prev = getNode(index - 1);
				prev.next = null;  // Disconnect the last node
				last = prev;       // Update the last pointer
				size--;
				return;
			}
			else{
				Node prev = getNode(index - 1);
				if (prev.next == null) {
					last = prev;
				}
				else{
				remove(prev.next);
				}
			}
		}
		

	/**
	 * Removes from this list the node pointing to the given memory block.
	 * 
	 * @param block the memory block that should be removed from the list
	 * @throws IllegalArgumentException
	 *         if the given memory block is not in this list
	 */
	public void remove(MemoryBlock block) {
		if (size == 0) {
			throw new IllegalArgumentException(
					"index must be between 0 and size");
		}
		if (first.block.equals(block)) {
			remove(first);
		}
		else if(last.block.equals(block)){
			remove(last);
		}
		else{
			Node pointer = getNode(indexOf(block) - 1);
			remove(pointer.next);
		}

	}	

	/**
	 * Returns an iterator over this list, starting with the first element.
	 */
	public ListIterator iterator(){
		return new ListIterator(first);
	}
	
	/**
	 * A textual representation of this list, for debugging.
	 */
	/*
	public String toString() {
		String str = "";
		if (this.getFirst() != null) {
			Node pointer = this.getFirst();
			while (pointer.next != null) {
				str += pointer.block.toString()+"\n";
				pointer = pointer.next;
			}
		}
		return str;
	}*/
	public String toString() {
		String str = "";
		if (this.getFirst() != null) {
			Node pointer = this.getFirst();
			while (pointer != null) { // Ensure the loop handles the last node correctly
				str += pointer.block.toString() + " "; // Add the current block
				pointer = pointer.next; // Move to the next node
			}
		}
		return str; // Trim to remove the trailing newline
	}
}