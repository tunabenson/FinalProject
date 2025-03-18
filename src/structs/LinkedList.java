package structs; /**
 * Defines a doubly-linked list class for CIS 22C
 * @author Ofek Katz
 */
import java.util.NoSuchElementException;

public class LinkedList<T> {

    private class Node{
        private T data;
        private Node prev;
        private Node next;

        public Node(T data){
            this.data = data;
            this.prev = null;
            this.next = null;
        }
    }

    private Node first;
    private Node last;
    private Node iterator;
    private int length;

    /**
     * Instantiates a new structs.LinkedList with default values
     * @postcondition an empty structs.LinkedList with length 0
     */
    public LinkedList() {
        first = null;
        last = null;
        length = 0;
    }



    /**
     * Converts the given array into a structs.LinkedList
     * @param array the array of values to insert into this structs.LinkedList
     * @postcondition a structs.LinkedList will be initialized containing the data in the array
     */
    public LinkedList(T[] array) {
        first = null;
        last = null;
        length = 0;
        if(array == null) {
            return;
        }

        for(T obj: array){
            addLast(obj);
        }
    }
    /**
     * Instantiates a new structs.LinkedList by copying another List
     * @param original the structs.LinkedList to copy
     * @postcondition a new List object, which is an identical,
     * but separate, copy of the structs.LinkedList original
     */
    public LinkedList(LinkedList<T> original) {
        if(original == null){
            return;
        }
        if(original.length == 0){
        }
        else{
            Node temp = original.first;
            while(temp != null){
                addLast(temp.data);
                temp = temp.next;
            }
        }
    }

    /**
     * Converts the structs.LinkedList to a String,
     * with each value separated by a blank
     * line At the end of the String, place a
     * new line character
     * @return the structs.LinkedList as a String
     */
    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        Node temp = first;
        while(temp != null) {
            result.append(temp.data + "\n");
            temp = temp.next;
        }
        return result.toString() + "\n";
    }


    /**
     * Creates a new first element
     * @param data the data to insert at the front of the structs.LinkedList
     * @postcondition the first node in the list will contain the data inserted
     */
    public void addFirst(T data){
        if(length == 0){
            first = last = new Node(data);
        }
        else {
            Node newFirst = new Node(data);
            newFirst.next = this.first;
            first.prev = newFirst;
            first = newFirst;
        }
        length++;
    }


    /**
     * Creates a new last element
     * @param data the data to insert at the end of the structs.LinkedList
     * @postcondition the last node in the list will contain the data inserted
     */
    public void addLast(T data){
        if(length == 0){
            first = last = new Node(data);
        }
        else{
            Node newLast = new Node(data);
            newLast.prev = last;
            last.next = newLast;
            last = newLast;
        }
        length++;
    }

    /**
     * removes the first element in the structs.LinkedList
     * @precondition length>0
     * @postcondition the first node in the list is removed
     * @throws NoSuchElementException if the list is empty
     */
    public void removeFirst() throws NoSuchElementException{
        if(length == 0){
            throw new NoSuchElementException("Element cannot be removed because the list is empty");
        }
        else if(length == 1){
            this.first = null;
            this.last = null;
        }
        else{
            first = first.next;
            first.prev = null;
        }
        length--;
    }


    /**
     * removes the last element in the structs.LinkedList
     * @precondition length>0
     * @postcondition the last node in the list is removed
     * @throws NoSuchElementException if the list is empty
     */
    public void removeLast() throws NoSuchElementException{
        if(length == 0){
            throw new NoSuchElementException("Element cannot be removed because the list is empty");
        }
        else if(length == 1){
            this.first = null;
            this.last = null;
        }
        else{
            last = last.prev;
            last.next = null;
        }
        length--;
    }


    /**
     * Returns whether or not the structs.LinkedList is currently empty
     * @return whether the structs.LinkedList is empty or not
     */
    public boolean isEmpty(){
        return length == 0;
    }

    /**
     * Returns the current length of the Linkedlist
     * @return the length of the list (0-indexed)
     */
    public int getLength(){
        return length;
    }

    /**
     * retrieves the last object in the structs.LinkedList
     * @precondition length>0
     * @return the data at the last Node of the structs.LinkedList
     * @throws NoSuchElementException if the list is empty (length==0)
     */
    public T getLast(){
        if(length == 0){
            throw new NoSuchElementException("Could not get the last element, structs.LinkedList is empty");
        }
        return this.last.data;
    }


    /**
     * retrieves the first object in the structs.LinkedList
     * @precondition length>0
     * @return the data at the first Node of the structs.LinkedList
     * @throws NoSuchElementException if the list is empty (length==0)
     */
    public T getFirst() throws NoSuchElementException{
        if(length == 0){
            throw new NoSuchElementException("Could not get the first element, structs.LinkedList is empty");
        }
        return this.first.data;
    }


    /**
     * Returns the data stored in the iterator node
     * @precondition !offEnd()
     * @return the data stored in the iterator node
     * @throw NullPointerException if the iterator points to a null value (off end)
     */
    public T getIterator() throws NullPointerException {
        if(offEnd()) {
            throw new NullPointerException("Iterator cannot be null");
        }
        return iterator.data;
    }


    /**
     * Returns whether the iterator is offEnd, i.e. null
     * @return whether the iterator is null
     */
    public boolean offEnd() {
        return iterator == null;
    }

    /**** MUTATORS ****/


    /**
     * Inserts a new element after the iterator
     * @param data the data to insert
     * @precondition !offEnd()
     * @throws NullPointerException if the iterator is offEnd (is null)
     */
    public void addIterator(T data) throws NullPointerException{
        if(offEnd()){
            throw new NullPointerException("Iterator is a null value: cannot be null");
        }
        else if(iterator == last){
            addLast(data);
        }
        else{
            Node newNode = new Node(data);
            newNode.prev = iterator;
            newNode.next = iterator.next;

            iterator.next.prev = newNode;
            iterator.next = newNode;
        }
        length++;
    }


    /**
     * removes the element referenced by the iterator
     * @precondition !offEnd()
     * @postcondition Node of iterator will be removed from the list. Iterator will be null after this.
     * @throws NullPointerException if the iterator is offEnd or null
     */
    public void removeIterator() throws NullPointerException {
        if(offEnd()){
            throw new NullPointerException("Iterator is a null value: cannot be null");
        }
        else if(iterator == first){
            removeFirst();
        }
        else if(iterator == last){
            removeLast();
        }
        else{
            iterator.prev.next = iterator.next;
            iterator.next.prev = iterator.prev;
        }
        iterator = null;
        length--;

    }

    /**
     * places the iterator at the first node
     * @postcondition iterator points to the first node in the list
     */
    public void positionIterator(){
        this.iterator= this.first;
    }

    /**
     * Moves the iterator one node towards the last
     * @precondition iterator != null
     * @postcondition the iterator will have advanced one Node in the structs.LinkedList
     * @throws NullPointerException if iterator points to a null value
     */
    public void advanceIterator() throws NullPointerException {
        if(offEnd()){
            throw new NullPointerException("Iterator is a null value: cannot be null");
        }
        this.iterator = this.iterator.next;

    }

    /**
     * Moves the iterator one node towards the first
     * @precondition !offEnd()
     * @postcondition the iterator will have traversed one Node down in the structs.LinkedList
     * @throws NullPointerException if iterator points to a null value (off end)
     */
    public void reverseIterator() throws NullPointerException {
        if(offEnd()){
            throw new NullPointerException("Iterator is a null value: cannot be null");
        }
        this.iterator = this.iterator.prev;
    }

    /**** ADDITIONAL OPERATIONS ****/

    /**
     * Re-sets structs.LinkedList to empty as if the
     * default constructor had just been called
     * @postcondition structs.LinkedList will be of length 0 and contain no values
     */
    public void clear() {
        //does this truly clear the list?
        //isnt the data still there since the nodes point to each other and garbage collection wouldn't get it?
        first = null;
        last = null;
        length = 0;
    }



    /**
     * Determines whether the given Object is
     * another structs.LinkedList, containing
     * the same data in the same order
     * @param obj another Object
     * @return whether there is equality
     */
    @SuppressWarnings("unchecked")
    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        } else if (!(obj instanceof LinkedList)) {
            return false;
        }

        LinkedList<T> list = (LinkedList<T>) obj;
        if (list.length != length) {
            return false;
        } else {
            Node temp1 = this.first;
            Node temp2 = list.first;
            while (temp1 != null) { //we don't need to check for both since we know they are the same length
                if(temp1.data == null || temp2.data == null){
                    if(temp1.data != temp2.data){
                        return false;
                    }
                }
                else if (!temp1.data.equals(temp2.data)) {
                    return false;
                }
                temp1 = temp1.next;
                temp2 = temp2.next;
            }
            return true;
        }
    }

    /**CHALLENGE METHODS*/

    /**
     * Moves all nodes in the list towards the end
     * of the list the number of times specified
     * Any node that falls off the end of the list as it
     * moves forward will be placed the front of the list
     * For example: [1, 2, 3, 4, 5], numMoves = 2 -> [4, 5, 1, 2 ,3]
     * For example: [1, 2, 3, 4, 5], numMoves = 4 -> [2, 3, 4, 5, 1]
     * For example: [1, 2, 3, 4, 5], numMoves = 7 -> [4, 5, 1, 2 ,3]
     * @param numMoves the number of times to move each node.
     * @precondition numMoves >= 0
     * @postcondition iterator position unchanged (i.e. still referencing
     * the same node in the list, regardless of new location of Node)
     * @throws IllegalArgumentException when numMoves < 0
     */
    public void spinList(int numMoves) throws IllegalArgumentException{
        if(numMoves < 0){
            throw new IllegalArgumentException("Cannot spin the list negative times");
        }
        if(length == 0){
            return;
        }
        for (int i = 0; i < numMoves; i++) {
            addFirst(last.data);
            removeLast();
        }
    }


    /**
     * Splices together two LinkedLists to create a third List
     * which contains alternating values from this list
     * and the given parameter
     * For example: [1,2,3] and [4,5,6] -> [1,4,2,5,3,6]
     * For example: [1, 2, 3, 4] and [5, 6] -> [1, 5, 2, 6, 3, 4]
     * For example: [1, 2] and [3, 4, 5, 6] -> [1, 3, 2, 4, 5, 6]
     * @param list the second structs.LinkedList
     *
     * @return a new structs.LinkedList, which is the result of
     * interlocking this and list
     * @postcondition this and list are unchanged
     */
    public LinkedList<T> altLists(LinkedList<T> list) {
        if(list == null) return new LinkedList<>(this);

        LinkedList<T> newList= new LinkedList<>();
        list.positionIterator();
        this.positionIterator();
        while(!this.offEnd() || !list.offEnd()){
            if(!this.offEnd()){
                newList.addLast(this.getIterator());
                this.advanceIterator();
            }
            if(!list.offEnd()){
                newList.addLast(list.getIterator());
                list.advanceIterator();
            }
        }
        return newList;
    }
    /**
     * Returns each element in the structs.LinkedList along with its
     * numerical position from 1 to n, followed by a newline.
     */
    public String numberedListString() {
        StringBuilder numberedList = new StringBuilder();
        positionIterator();
        Node temp = iterator;
        int counter = 0;
        while(temp != null){
            counter++;
            numberedList.append(counter + ". ").append(temp.data.toString()).append("\n");
            temp = temp.next;
        }
        return numberedList.toString()+"\n";
    }

    /**
     * Searches the structs.LinkedList for a given element's index.
     * @param data the data whose index to locate.
     * @return the index of the data or -1 if the data is not contained
     * in the structs.LinkedList.
     */
    public int findIndex(T data) {
        int index = 0;

        Node temp = first;
        while (temp != null){
            if(data.equals(temp.data)) return index;
            index ++;
            temp  = temp.next;
        }
        return -1;
    }

    /**
     * Advances the iterator to location within the structs.LinkedList
     * specified by the given index.
     * @param index the index at which to place the iterator.
     * @precondition index >= 0,  index < length
     * @throws IndexOutOfBoundsException when index is out of bounds
     */
    public void advanceIteratorToIndex(int index)  throws IndexOutOfBoundsException {
        if (index < 0 || index >= length){
            throw new IndexOutOfBoundsException("advanceIteratorToIndex(index): index out of bounds");
        }
        positionIterator();
        for (int i = 0; i < index; i++) {
            iterator = iterator.next;
        }
    }
}

