package datastructures.concrete;

import datastructures.interfaces.IPriorityQueue;
import misc.exceptions.EmptyContainerException;

/**
 * See IPriorityQueue for details on what each method must do.
 */
public class ArrayHeap<T extends Comparable<T>> implements IPriorityQueue<T> {
    // See spec: you must implement a implement a 4-heap.
    private static final int NUM_CHILDREN = 4;
    private static final int INIT_SIZE = 10;
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
            // find smol child
            int count = 1;
            if (this.size > index * 4) {
                for (int i = 1; i <= 4; i++) {
                    T temp = this.heap[4 * index + i];
                    if (temp != null && temp.compareTo(this.heap[4 * index + count]) < 0) {
                        count = i;
                    }
                    System.out.println("i: " + i + " count: " + count);
                }
                if (index < this.size / 4 && this.heap[index].compareTo(this.heap[4 * index + count]) > 0) {
                    T temp = this.heap[4 * index + count];
                    this.heap[4 * index + count] = this.heap[index];
                    this.heap[index] = temp;
                    index = 4 * index + count;
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
        if (this.size < this.heap.length) {
            this.heap[this.size] = item;
        } else {
            T[] temp = this.makeArrayOfT(this.heap.length * 2);
            for (int i = 0; i < this.size; i++) {
                temp[i] = this.heap[i];
            }
            this.heap = temp;
        }
        boolean found = false;
        int index = this.size;
        while (!found) {
            if (this.size >= index / 4 && this.heap[index].compareTo(this.heap[index / 4]) < 0) {
                T temp = this.heap[index / 4];
                this.heap[index / 4] = this.heap[index];
                this.heap[index] = temp;
                index = index / 4;
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
