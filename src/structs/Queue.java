package structs;
/**
 * a singly linked list representing a Queue data structure
 * @author Ofek Katz
 * CIS 22C, Lab 7
 * @param <T> the generic data stored in the Queue
 */


import java.util.NoSuchElementException;

public class Queue<T> implements Q<T> {
    private class Node {
        private T data;
        private Node next;

        public Node(T data) {
            this.data = data;
            this.next = null;
        }
    }

    private int size;
    private Node front;
    private Node end;

    /****CONSTRUCTORS****/

    /**
     * Default constructor for the Queue class
     * @postcondition a new Queue object with all fields
     * assigned default values
     */
    public Queue() {
        front = end = null;
        size = 0;
    }

    /**
     * Converts an array into a Queue
     * @param array the array to copy into
     * the Queue
     */
    public Queue(T[] array) {
        this();
        if(array == null) return;
        for(T obj : array){
            enqueue(obj);
        }
    }

    /**
     * Copy constructor for the Queue class
     * Makes a deep copy of the parameter
     * @param original the Queue to copy
     * @postcondition <You fill in here>
     */
    public Queue(Queue<T> original) {
        this();
        if(original == null) return;
        Node temp = original.front;
        while(temp != null){
            enqueue(temp.data);
            temp = temp.next;
        }
    }

    /****ACCESSORS****/

    /**
     * Returns the value stored at the front
     * of the Queue
     * @return the value at the front of the queue
     * @precondition !isEmpty()
     * @throws NoSuchElementException when the
     * precondition is violated
     */
    public T getFront() throws NoSuchElementException {
        if(isEmpty()){
            throw new NoSuchElementException("getFront: cannot get value since list is empty");
        }
        return front.data;

    }

    /**
     * Returns the size of the Queue
     * @return the size from 0 to n
     */
    public int getSize() {
        return this.size;
    }

    /**
     * Determines whether a Queue is empty
     * @return whether the Queue contains no elements
     */
    public boolean isEmpty() {
        return this.size == 0;
    }

    /****MUTATORS****/

    /**
     * Inserts a new value at the end of the Queue
     *
     * @param data the new data to insert
     * @postcondition new Node will be added at the end of the queue
     */
    public void enqueue(T data) {
        if(size == 0){
            front = end = new Node(data);
        }
        else {
            Node newNode = new Node(data);
            end.next = newNode;
            end = newNode;
        }
        size++;
    }

    /**
     * Removes the front element in the Queue
     * @precondition !isEmpty()
     * @throws NoSuchElementException when
     * the precondition is violated
     * @postcondition first node will be removed from queue and front will point to next node in line
     */
    public void dequeue() throws NoSuchElementException {
        if(isEmpty()){
            throw new NoSuchElementException("dequeue: unable to pop element because queue is empty ");
        }
        front = front.next;
        size--;

    }

    /****ADDITONAL OPERATIONS****/

    /**
     * Returns the values stored in the Queue
     * as a String, separated by a blank space
     * with a new line character at the end
     * @return a String of Queue values
     */
    @Override public String toString() {
        StringBuilder builder = new StringBuilder();
        Node temp = front;
        while (temp != null) {
            builder.append(temp.data.toString()).append(" ");
            temp = temp.next;
        }
        return builder.append("\n").toString();
    }

    /**
     * Determines whether two Queues contain
     * the same values in the same order
     * @param obj the Object to compare to this
     * @return whether obj and this are equal
     */
    @SuppressWarnings("unchecked") // good practice to remove warning here
    @Override public boolean equals(Object obj)  {
        if(obj == this) return true;
        else if(!(obj instanceof Queue<?>)) return false;

        Queue<T> other = (Queue<T>) obj;
        if(other.size != this.size) return false;

        Node temp1 = other.front;
        Node temp2 = this.front;

        while(temp1 != null){
            if(!temp1.data.equals(temp2.data)){
                return false;
            }
            temp1 = temp1.next;
            temp2 = temp2.next;
        }
        return true;
    }

}
