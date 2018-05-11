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
    private int heapSize;
    // Feel free to add more fields and constants.

    public ArrayHeap() {
        this.heapSize = 0;
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
    
    /**
     * Throws and EmptycontainerException if the heap is empty
     */
    private void emptyError() {
        if (this.isEmpty()) {
            throw new EmptyContainerException("This list is empty");
        }
    }
    
    /**
     * Removes and returns the minimum item in the heap. Throws and EmptyContainerException
     * if the heap is empty
     */
    @Override
    public T removeMin() {
        this.emptyError();
        T result = this.peekMin();
        this.heap[0] = this.heap[this.heapSize - 1];
        this.heap[this.heapSize - 1] = null;
        int index = 0;
        
        while (index < this.heapSize / NUM_CHILDREN || 
                (index * NUM_CHILDREN + 1 <= this.heapSize && this.heap[index * NUM_CHILDREN + 1] != null)) {
            int count = 1;
            for (int i = 1; i <= NUM_CHILDREN; i++) {
                T temp = this.heap[NUM_CHILDREN * index + i];
                if (temp != null && temp.compareTo(this.heap[NUM_CHILDREN * index + count]) < 0) {
                    count = i;
                }
            }
            
            int minIndex = NUM_CHILDREN * index + count;
            if (this.heap[index].compareTo(this.heap[minIndex]) > 0) {
                T temp = this.heap[minIndex];
                this.heap[minIndex] = this.heap[index];
                this.heap[index] = temp;
                index = minIndex;
            } else {
                break;
            }
        }
        this.heapSize--;
        return result;
    }
    
    /**
     * Returns the minimum value in the heap.
     * Throws EmptyContainerException if the heap is empty.
     */
    @Override
    public T peekMin() {
        this.emptyError();
        return this.heap[0];
    }

    /**
     * Inserts the item passed in as a parameter into the heap following standard heap
     * requirements. Throws an IllegalArguementException if the item passed in is null.
     */
    @Override
    public void insert(T item) {
        if (item == null) {
            throw new IllegalArgumentException("item cannot be null");
        }
        if (this.heapSize >= this.heap.length - 1) { // resize
            T[] temp = this.makeArrayOfT(this.heap.length * 2);
            for (int i = 0; i < this.heapSize; i++) {
                temp[i] = this.heap[i];
            }
            this.heap = temp;            
        }

        int index = this.heapSize;
        this.heap[index] = item;
        int parent = (index - 1) / NUM_CHILDREN;
        while (index > 0 && this.heap[parent].compareTo(this.heap[index]) > 0) {
            T temp = this.heap[index];
            this.heap[index] = this.heap[parent];
            this.heap[parent] = temp;
            index = parent;
            parent = (index - 1) / NUM_CHILDREN;
        }
        this.heapSize++;
    }

    /**
     * Returns the Current size of the heap.
     */
    @Override
    public int size() {
        return this.heapSize;
    }
}