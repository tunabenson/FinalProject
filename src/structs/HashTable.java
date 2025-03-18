package structs; /**
 * structs.HashTable.java
 * @author Ofek Katz
 * CIS 22C, Lab 13.2
 */
import structs.LinkedList;

import java.util.ArrayList;

public class HashTable<T> {

    private int numElements;
    private ArrayList<LinkedList<T>> table;

    /**
     * Constructor for the structs.HashTable class. Initializes the Table to be sized
     * according to value passed in as a parameter. Inserts size empty Lists into
     * the table. Sets numElements to 0
     *
     * @param size the table size
     * @precondition size > 0
     * @throws IllegalArgumentException when size <= 0
     */
    public HashTable(int size) throws IllegalArgumentException {
        if(size < 0 ) throw new IllegalArgumentException("structs.HashTable cannot be negatively sized");
        this.numElements = 0;
        this.table = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            table.add(new LinkedList<>());
        }
    }

    /**
     * Constructor for structs.HashTable class.
     * Inserts the contents of the given array
     * into the Table at the appropriate indices
     * @param array an array of elements to insert
     * @param size the size of the Table
     * @precondition size > 0
     * @throws IllegalArgumentException when size <= 0
     */
    public HashTable(T[] array, int size) throws IllegalArgumentException {
        this(size);
        if(array == null) return;
        for (T element: array) {
            add(element);
        }
    }

    /** Accessors */

    /**
     * Returns the hash value in the table for a given Object.
     * @param obj the Object
     * @return the index in the table
     */
    private int hash(T obj) {
        int code = obj.hashCode();
        return code % table.size();
    }

    /**
     * Counts the number of elements at this index.
     * @param index the index in the table
     * @precondition 0 <= index < table.size()
     * @return the count of elements at this index
     * @throws IndexOutOfBoundsException when the precondition is violated
     */
    public int countBucket(int index) throws IndexOutOfBoundsException {
        if(index < 0 || index >= table.size()) throw new IndexOutOfBoundsException("Index provided out of bounds");
        return table.get(index).getLength();
    }

    /**
     * Determines total number of elements in the table
     * @return total number of elements
     */
    public int getNumElements() {
        return numElements;
    }

    /**
     * Accesses a specified key in the Table
     * @param elmt the key to search for
     * @return the value to which the specified key is mapped,
     * or null if this table contains no mapping for the key.
     * @precondition elmt != null
     * @throws NullPointerException when the precondition is violated.
     */
    public T get(T elmt) throws NullPointerException {
        if(elmt == null) throw new NullPointerException("get(elmt): could not get element because it was null ");
        int bucketIndex = hash(elmt);
        LinkedList<T> bucket = table.get(bucketIndex);
        int objIndex = bucket.findIndex(elmt);
        if(objIndex == -1) return null;
        bucket.advanceIteratorToIndex(objIndex);
        return bucket.getIterator();
    }

    /**
     * Accesses a specified element in the table.
     * @param elmt the element to locate
     * @return the bucket number where the element
     * is located or -1 if it is not found.
     * @precondition elmt != null
     * @throws NullPointerException when the precondition is violated.
     */
    public int find(T elmt) throws NullPointerException{
        if(elmt == null) throw new NullPointerException("could not check element because it was null ");
        int bucket = hash(elmt);
        if(table.get(bucket).findIndex(elmt) == -1) return -1;
        return bucket;
    }

    /**
     * Determines whether a specified element is in the table.
     * @param elmt the element to locate
     * @return whether the element is in the table
     * @precondition  elmt != null
     * @throws NullPointerException when the precondition is violated
     */
    public boolean contains(T elmt) throws NullPointerException {
        //find() will throw nullpointer
        return find(elmt) != -1;
    }

    /** Mutators */

    /**
     * Inserts a new element in the table at the end of the chain
     * of the correct bucket.
     * @param elmt the element to insert
     * @precondition elmt != null
     * @throws NullPointerException when the precondition is violated.
     */
    public void add(T elmt) throws NullPointerException {
        if(elmt == null) throw new NullPointerException("add(elmt): could not add element because it was null ");
        int index = hash(elmt);
        if(index >= table.size() || table.get(index) == null){
            table.add(index, new LinkedList<>());
        }
        LinkedList<T> bucket = table.get(index);
        bucket.addLast(elmt);
        numElements++;
    }

    /**
     * Removes the given element from the table.
     * @param elmt the element to remove
     * @precondition elmt != null
     * @return whether elmt exists and was removed from the table
     * @throws NullPointerException when the precondition is violated
     */
    public boolean delete(T elmt) throws NullPointerException {
        if(elmt == null) throw new NullPointerException("remove(elmt): could not remove element because it was null ");

        LinkedList<T> bucket = table.get(hash(elmt));
        int objIndex = bucket.findIndex(elmt);
        if(objIndex == -1) return false;
        bucket.advanceIteratorToIndex(objIndex);
        bucket.removeIterator();
        return true;


    }

    /**
     * Resets the hash table back to the empty state, as if the one argument
     * constructor has just been called.
     */
    public void clear() {
        for (int i = 0; i < table.size(); i++) {
            table.set(i, new LinkedList<T>());
        }
        this.numElements = 0;
    }

    /** Additional Methods */

    /**
     * Computes the load factor.
     * @return the load factor
     */
    public double getLoadFactor() {
        return ((double)this.numElements) / this.table.size();
    }

    /**
     * Creates a String of all elements at a given bucket
     * @param bucket the index in the table
     * @return a String of elements, separated by spaces with a new line character
     *  at the end
     * @precondition bucket < table.size()
     * @throws IndexOutOfBoundsException when bucket is
     * out of bounds
     */
    public String bucketToString(int bucket) throws IndexOutOfBoundsException {
        if(bucket >= table.size()) throw new IndexOutOfBoundsException("bucket does not exist");

        return table.get(bucket).toString();
    }

    /**
     * Creates a String of the bucket number followed by a colon followed by
     * the first element at each bucket followed by a new line. For empty buckets,
     * add the bucket number followed by a colon followed by empty.
     *
     * @return a String of all first elements at each bucket.
     */
    public String rowToString() {
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < table.size(); i++) {
            str.append("Bucket ").append(i).append(": ");
            LinkedList<T> bucket = table.get(i);
            if(bucket.isEmpty()){
                str.append("empty\n");
                continue;
            }
            str.append(table.get(i).getFirst());
            str.append("\n");
        }
        return str.toString();
    }

    /**
     * Starting at the 0th bucket, and continuing in order until the last
     * bucket, concatenates all elements at all buckets into one String, with
     * a new line between buckets and one more new line at the end of the
     * entire String.
     * @return a String of all elements in this structs.HashTable.
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < table.size(); i++) {
            String bucket = bucketToString(i);
            if(!bucket.isBlank()){
                builder.append(bucket);
            }
        }
        builder.append("\n");
        return builder.toString();
    }
}
