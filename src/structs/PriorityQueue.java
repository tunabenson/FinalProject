package structs;

import java.util.ArrayList;
import java.util.Comparator;

public class PriorityQueue<T> {

    private Heap<T> heap;
    private ArrayList <T> priority;
    public PriorityQueue(Comparator<T> cmp, ArrayList<T> list) {
        this.heap = new Heap<>(list, cmp);
        priority = this.heap.sort();

    }

    public boolean isEmpty() {
        return priority.isEmpty();
    }

    public void enqueue(T item) {
        heap.insert(item);
        priority = heap.sort();
    }

    public T dequeue() {
        return priority.remove(0);
    }

    public T peek() {
        return priority.get(0);
    }

    public String numberedOrderList() {
        StringBuilder numberedList  = new StringBuilder();
        for (int i = 0; i < priority.size(); i++) {
            numberedList.append("#").append(i + 1).append(") ").append(priority.get(i));
        }
        return numberedList.toString();
    }
}
