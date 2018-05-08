package datastructures.concrete;

import datastructures.interfaces.IPriorityQueue;
import misc.exceptions.EmptyContainerException;

/**
 * See IPriorityQueue for details on what each method must do.
 */
public class ArrayHeap<T extends Comparable<T>> implements IPriorityQueue<T> {
    // See spec: you must implement a implement a 4-heap.
    private static final int NUM_CHILDREN = 4;
    private static final int INIT_SIZE = NUM_CHILDREN * (NUM_CHILDREN + 1) + 1;
    
    // You MUST use this field to store the contents of your heap.
    // You may NOT rename this field: we will be inspecting it within
    // our private tests.
    private T[] heap;
    private int size;
    // Feel free to add more fields and constants.

    public ArrayHeap() {
        this.size = 0;
        this.heap = makeArrayOfT(INIT_SIZE);
    }

    /**
     * This method will return a new, empty array of the given size
     * that can contain elements of type T.
     *
     * Note that each element in the array will initially be null.
     */
    @SuppressWarnings("unchecked")
    private T[] makeArrayOfT(int size) {
        // This helper method is basically the same one we gave you
        // in ArrayDictionary and ChainedHashDictionary.
        //
        // As before, you do not need to understand how this method
        // works, and should not modify it in any way.
        return (T[]) (new Comparable[size]);
    }

    @Override
    public T removeMin() {
        if (this.size == 0) {
            throw new EmptyContainerException("This list is empty");
        }
        T result = this.peekMin();
        this.heap[0] = this.heap[this.size - 1];
        this.heap[this.size - 1] = null;
        
        
        boolean found = false;
        int index = 0;
        while (!found) {
            int count = 1;
            if (index < this.size / NUM_CHILDREN) { 
                for (int i = 1; i <= NUM_CHILDREN; i++) {
                    T temp = this.heap[NUM_CHILDREN * index + i];
                    if (temp != null && temp.compareTo(this.heap[NUM_CHILDREN * index + count]) < 0) {
                        count = i;
                    }
                }
                if (this.heap[index].compareTo(this.heap[NUM_CHILDREN * index + count]) > 0) {
                    T temp = this.heap[NUM_CHILDREN * index + count];
                    this.heap[NUM_CHILDREN * index + count] = this.heap[index];
                    this.heap[index] = temp;
                    index = NUM_CHILDREN * index + count;
                } else {
                    found = true;
                }
            } else {
                found = true;
            }
        }
        this.size--;
        return result;
    }

    @Override
    public T peekMin() {
        if (this.size == 0) {
            throw new EmptyContainerException("this list is empty");
        }
        return this.heap[0];
    }

    @Override
    public void insert(T item) {
        if (item == null) {
            throw new IllegalArgumentException("item cannot be null");
        }
        if (this.size >= this.heap.length) { // resize
            T[] temp = this.makeArrayOfT(this.heap.length * 2);
            for (int i = 0; i < this.size; i++) {
                temp[i] = this.heap[i];
            }
            this.heap = temp;            
        }
        int index = this.size;
        this.heap[index] = item;
        
        // percolating up 
        while(index > 0 && this.heap[(index - 1) / NUM_CHILDREN].compareTo(this.heap[index]) > 0) { // parent exists in the case when index is greater than 0, 
            T temp = this.heap[index];
            this.heap[index] = this.heap[(index - 1) / NUM_CHILDREN];
            this.heap[(index - 1) / NUM_CHILDREN] = temp;
            index = (index - 1) / NUM_CHILDREN;
        }
        this.size++;
    }

    @Override
    public int size() {
        return this.size;
    }
    
    public String toString() {
        String result = "[";
        for (int i = 0; i < this.size - 1; i++) {
            result += this.heap[i] + ", "; 
        }
        result += this.heap[this.size-1] + "]";
        return result; 
    }
}
