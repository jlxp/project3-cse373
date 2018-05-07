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
        this.size--;
              
        boolean found = false;
        int index = 0;        
        while (!found) {
            // Original Version (somehow works well on one of the problem)
            int count = 1;
            if (this.size > index * 4) {
                for (int i = 1; i <= 4; i++) {
                    T temp = this.heap[4 * index + i];
                    if (temp != null && temp.compareTo(this.heap[4 * index + count]) < 0) {
                        count = i;
                    }
                }
                if (index < this.size / 4) {// && this.heap[index].compareTo(this.heap[4 * index + count]) > 0) {
                    T temp = this.heap[4 * index + count];
                    this.heap[4 * index + count] = this.heap[index];
                    this.heap[index] = temp;
                    index = 4 * index + count;
                } else {
                    found = true;
                }
            } else {
                T temp = this.heap[0];
                int minDex = 0;
                for (int i = 1; i < this.size; i++) {
                    if (temp != null && this.heap[i].compareTo(this.heap[minDex]) < 0) {
                        minDex = i;
                    }
                }
                if (minDex < this.size) {
                    T old = this.heap[0];
                    this.heap[0] = this.heap[minDex];
                    this.heap[minDex] = old;
                } else {
                    found = true;
                }
            }
        }
        // below is our latest version that consider edge cases -- work worse than original somehow
//        while (!found) {            
//            int count = 0;
//            if (index < (this.size - 2) / NUM_CHILDREN) { // general branch nodes
//                for (int i = 1; i <= NUM_CHILDREN; i++) { //find the minimum child
//                    T temp = this.heap[NUM_CHILDREN * index + i];
//                    if (temp == null) {
//                        break;
//                    } else if (temp != null && temp.compareTo(this.heap[NUM_CHILDREN * index + count]) < 0) {
//                        count = i;
//                    }
//                }
//                if (this.heap[index].compareTo(this.heap[NUM_CHILDREN * index + count]) > 0) { 
//                    // compare with the minimum child node
//                    T temp = this.heap[NUM_CHILDREN * index + count];
//                    this.heap[NUM_CHILDREN * index + count] = this.heap[index];
//                    this.heap[index] = temp;
//                    index = NUM_CHILDREN * index + count;
//                } else { // smaller than minimum children means that current node is smaller than its kids
//                    found = true;
//                }
//            } else {
//                // buggy: this else statement represents the leaf node, so nothing should not happen here, but why not 
//                for (int i = 0; i < NUM_CHILDREN; i++) { // find the min child
//                    T temp = this.heap[index + i];
//                    if (temp == null) {
//                        break;
//                    } else if (temp != null && temp.compareTo(this.heap[index + count]) < 0) {
//                        count = i;
//                    }
//                }             
//                if (this.heap[index + count] != null && this.heap[index].compareTo(this.heap[ index + count]) > 0) {
//                 // compare with the minimum child node
//                    T temp = this.heap[index + count];
//                    this.heap[index + count] = this.heap[index];
//                    this.heap[index] = temp;
//                } 
//                found = true;
//
//            }
//        }
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
        this.heap[this.size] = item;
        boolean found = false;
        int index = this.size;
        while (!found) {         
            if (this.size >= index / NUM_CHILDREN && this.heap[index].compareTo(this.heap[index / NUM_CHILDREN]) < 0) {
                T temp = this.heap[index / NUM_CHILDREN];
                this.heap[index / NUM_CHILDREN] = this.heap[index];
                this.heap[index] = temp;
                index = index / NUM_CHILDREN;
            } else {
                found = true;
            }
        }
        this.size++;
    }

    @Override
    public int size() {
        return this.size;
    }
}
