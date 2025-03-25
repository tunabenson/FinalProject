package structs;

import shop.Order;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.NoSuchElementException;

public class PriorityQueue<T> {
    private Heap<T> heap;

    /** Constructor */
    public PriorityQueue(ArrayList<T> priority,Comparator<T> cmp) {
        heap = new Heap<>(priority, cmp);
    }

    /** Inserts an element into the priority queue */
    public void enqueue(T item) {
        heap.insert(item);
    }

    /** Removes and returns the highest-priority element */
    public T dequeue() {
        if (heap.getHeapSize() == 0) {
            throw new NoSuchElementException("Priority queue is empty");
        }
        T max = heap.getMax();
        heap.remove(1);
        return max;
    }

    /** Returns the highest-priority element without removing it */
    public T peek() {
        if (heap.getHeapSize() == 0) {
            throw new NoSuchElementException("Priority queue is empty");
        }
        return heap.getMax();
    }

    /** Checks if the priority queue is empty */
    public boolean isEmpty() {
        return heap.getHeapSize() == 0;
    }

    /** Returns the size of the priority queue */
    public int size() {
        return heap.getHeapSize();
    }

    public String numberedOrderList() {
        ArrayList<T> priority = heap.sort();
        StringBuilder numberedList  = new StringBuilder();
        for (int i = 0; i < priority.size(); i++) {
            numberedList.append("#").append(i + 1).append(") ").append(priority.get(i));
        }
        return numberedList.toString();
    }
}
