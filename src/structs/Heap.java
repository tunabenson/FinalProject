package structs;
/**
 * Heap.java
 * @author Ofek Katz
 * CIS 22C, Lab 18
 */
import java.util.ArrayList;
import java.util.Comparator;
import java.util.NoSuchElementException;

public class Heap<T> {
    private int heapSize;
    private ArrayList<T> heap;
    private Comparator<T> cmp;

    /**Constructors/

     /**
     * Constructor for the Heap class.
     * Sets heapSize to data size, stores parameters, inserts null at heap
     * element 0, and calls buildHeap().
     * @param data an unordered ArrayList, where element 0 is not used.
     * @param cmp that determines organization of heap
     * based on priority.
     */
    public Heap(ArrayList<T> data, Comparator<T> cmp) {
        this.heap = new ArrayList<>();
        this.cmp = cmp;
        this.heapSize = data.size();
        if(data == null) return;
        this.heap = data;
        this.heap.add(0, null);
        buildHeap();
    }

    public boolean isEmpty(){
        return heap.isEmpty();
    }


    /**Mutators*/

    /**
     * Converts an ArrayList into a valid max heap. Called by constructor.
     * Calls helper method heapify.
     */
    public void buildHeap() {
        int start = Math.floorDiv(heapSize , 2);
        for (int i = start; i >= 1; i--) {
            heapify(i);
        }
    }

    /**
     * Helper method to buildHeap, remove, and sort.
     * Bubbles an element down to its proper location within the heap.
     * @param index an index in the heap
     */
    private void heapify(int index) {
        int lft = getLeft(index);
        int rgt = getRight(index);
        int maxIndex = index;

        if(lft < heapSize + 1 && cmp.compare(heap.get(lft) , heap.get(index)) > 0){
            maxIndex = lft;
        }
        if(rgt < heapSize + 1 && cmp.compare(heap.get(rgt) , heap.get(maxIndex)) > 0){
            maxIndex = rgt;
        }

        if(index != maxIndex){
            T temp = heap.get(index);
            heap.set(index, heap.get(maxIndex));
            heap.set(maxIndex, temp);
            heapify(maxIndex);
        }
    }

    /**
     * Inserts the given data into heap.
     * Calls helper method heapIncreaseKey.
     * @param key the data to insert
     */
    public void insert(T key) {
        heapSize++;
        heap.add(heap.get(heapSize - 1)); //this is a low value
        heapIncreaseKey(heapSize, key);
    }

    /**
     * Helper method for insert.
     * Bubbles an element up to its proper location
     * @param index the current index of the key
     * @param key the data
     */
    private void heapIncreaseKey(int index, T key) {
        if(cmp.compare(key, heap.get(index)) > 0){
            heap.set(index, key);
            while (index > 1 && cmp.compare(heap.get(getParent(index)), key) < 0 ){
                int parentIndex = getParent(index);
                T temp = heap.get(index);
                heap.set(index, heap.get(parentIndex));
                heap.set(parentIndex, temp);

                index = parentIndex;

            }
        }
    }

    /**
     * Removes the element at the specified index.
     * Calls helper method heapify
     * @param index the index of the element to remove
     */
    public void remove(int index) {

        heap.set(index, heap.get(heapSize));
        heap.remove(heapSize);
        heapSize--;
        if (index < heapSize) heapify(index);

    }

    /**Accessors*/

    /**
     * Returns the heap size (current number of elements)
     * @return the size of the heap
     */
    public int getHeapSize() {
        return heapSize;
    }

    /**
     * Returns the location (index) of the
     * left child of the element stored at index.
     * @param index the current index
     * @return the index of the left child.
     * @precondition 0 < index <= heap_size
     * @throws IndexOutOfBoundsException when precondition is violated.
     */
    public int getLeft(int index) throws IndexOutOfBoundsException {
        if(index < 1 || index > heapSize) throw new IndexOutOfBoundsException("index provided is out of bounds");

        return 2 * index;
    }

    /**
     * Returns the location (index) of the right child of the element
     * stored at index.
     * @param index the current index
     * @return the index of the right child
     * @precondition 0 < i <= heap_size
     * @throws IndexOutOfBoundsException when precondition is violated.
     */
    public int getRight(int index) throws IndexOutOfBoundsException {
        if(index < 1 || index > heapSize) throw new IndexOutOfBoundsException("index provided is out of bounds");

        return (2 * index) + 1;
    }

    /**
     * Returns the location (index) of the
     * parent of the element stored at index.
     * @param index the current index
     * @return the index of the parent
     * @precondition 1 < i <= heap_size
     * @throws IndexOutOfBoundsException when precondition is violated.
     */
    public int getParent(int index) throws IndexOutOfBoundsException {
        if(index <= 1 || index > heapSize) throw new IndexOutOfBoundsException("index provided is out of bounds");

        return Math.floorDiv(index, 2);
    }

    /**
     * Returns the maximum element (highest priority)
     * @return the max value
     */
    public T getMax() {
        return heap.get(1);
    }

    /**
     * Returns the element at a specific index.
     * @param index an index in the heap.
     * @return the data at the index.
     * @precondition 0 < i <= heap_size
     * @throws IndexOutOfBoundsException when precondition is violated.
     */
    public T getElement(int index) throws IndexOutOfBoundsException {
        if(index < 1 || index > heapSize) throw new IndexOutOfBoundsException("index provided is out of bounds");

        return heap.get(index);
    }

    /**Additional Operations*/

    /**
     * Creates a String of all elements in the heap, separated by ", ".
     * @return a String of all elements in the heap, separated by ", ".
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (int i = 1; i <= heapSize; i++) {

            if(i == heap.size() - 1){
                builder.append(heap.get(heap.size() - 1));
            }
            else{
                builder.append(heap.get(i).toString()).append(", ");
            }
        }

        return builder.toString();
    }

    /**
     * Uses the heap sort algorithm to sort the heap into ascending order.
     * Calls helper method heapify.
     * @return an ArrayList of sorted elements
     * @postcondition heap remains a valid heap
     */
    public ArrayList<T> sort() {
        heap.remove(0);

        Heap<T> sorted = new Heap<>(new ArrayList<>(heap), cmp);

        int size = sorted.heapSize;


        for (int i = size; i > 1; i--) {
            T root = sorted.heap.get(1);
            sorted.heap.set(1, sorted.heap.get(i));
            sorted.heap.set(i, root);
            sorted.heapSize--;
            sorted.heapify(1);

        }

        sorted.heap.remove(0);
        heap.add(0, null);
        return sorted.heap;
    }
}