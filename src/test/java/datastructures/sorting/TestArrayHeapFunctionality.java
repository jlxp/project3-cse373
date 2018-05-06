package datastructures.sorting;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import misc.BaseTest;
import misc.exceptions.EmptyContainerException;
import datastructures.concrete.ArrayHeap;
import datastructures.interfaces.IPriorityQueue;
import org.junit.Test;

/**
 * See spec for details on what kinds of tests this class should include.
 */
public class TestArrayHeapFunctionality extends BaseTest {
    protected <T extends Comparable<T>> IPriorityQueue<T> makeInstance() {
        return new ArrayHeap<>();
    }

    @Test(timeout=SECOND)
    public void testBasicSize() {
        IPriorityQueue<Integer> heap = this.makeInstance();
        heap.insert(3);
        assertEquals(1, heap.size());
        assertTrue(!heap.isEmpty());
    }
    
    @Test(timeout=SECOND)
    public void testInsert() {
        IPriorityQueue<Integer> heap = this.makeInstance();
        heap.insert(20);
        assertEquals(20, heap.peekMin());
    }
    
    @Test(timeout=SECOND)
    public void testInsertFront() {
        IPriorityQueue<Integer> heap = this.makeInstance();
        heap.insert(10);
        heap.insert(5);
        assertEquals(5, heap.peekMin());
    }
    
    @Test(timeout=SECOND)
    public void testInsertUpdatesSizeCorrectly() {
        IPriorityQueue<Integer> heap = this.makeInstance();
        for (int i = 1; i <= 100; i++) {
            heap.insert(i);
            assertEquals(i, heap.size());
        }
    }
    
    @Test(timeout=SECOND) 
    public void testBasicPeek() {
        IPriorityQueue<Integer> heap = this.makeInstance();
        heap.insert(100);
        assertEquals(100, heap.peekMin());
        assertEquals(1, heap.size());
    }
    
    @Test(timeout=SECOND)
    public void testBasicRemove() {
        IPriorityQueue<Integer> heap = this.makeInstance();
        heap.insert(100);
        assertEquals(100, heap.removeMin());
        assertTrue(heap.isEmpty());
    }
    
    @Test//(timeout=SECOND)
    public void testRemoveUpdatesSizeCorrectly() {
        IPriorityQueue<Integer> heap = this.makeInstance();
        for (int i = 0; i < 100; i++) {
            heap.insert(i);
        }
        for (int i = 0; i < 100; i++) {
            int temp = heap.removeMin();
            assertEquals(i, temp);
            assertEquals(100 - i - 1, heap.size());
        }
        assertTrue(heap.isEmpty());
    }
    
    @Test(timeout=SECOND)
    public void testInsertAndRemove() {
        IPriorityQueue<Integer> heap = this.makeInstance();
        for (int i = 0; i < 100; i++) {
            heap.insert(i);
            assertEquals(i, heap.removeMin());
        }
        assertTrue(heap.isEmpty());
    }
    
    @Test(timeout=SECOND)
    public void testInsertRepeat() {
        IPriorityQueue<Integer> heap = this.makeInstance();
        for (int i = 0; i < 100; i++) {
            heap.insert(2);
            assertEquals(2, heap.peekMin());
            assertEquals(i + 1, heap.size());
        }
    }
    
    @Test(timeout=SECOND)
    public void testRemoveError () {
        IPriorityQueue<Integer> heap = this.makeInstance();
        try {
            heap.removeMin();
            fail("Expected EmptyContainerException");
        } catch (EmptyContainerException x) {
            // do nothing
        }
    }
    
    @Test(timeout=SECOND)
    public void testPeekError() {
        IPriorityQueue<Integer> heap = this.makeInstance();
        try {
            heap.peekMin();
            fail("Expected EmptyContainerException");
        } catch (EmptyContainerException x) {
            // do nothing
        }
    }
    
    @Test(timeout=SECOND)
    public void testNullKey() {
        IPriorityQueue<Integer> heap = this.makeInstance();
        try {
            heap.insert(null);
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException i) {
            // do nothing
        }
    }
    
    @Test(timeout=SECOND)
    public void testSingleElement() {
        IPriorityQueue<Integer> heap = this.makeInstance();
        heap.insert(3);
        
        assertEquals(3, heap.removeMin());
    }
    
    @Test(timeout=SECOND)
    public void testTwoChildren() {
        IPriorityQueue<Integer> heap = this.makeInstance();
        heap.insert(3);
        heap.insert(6);
        heap.insert(5);
        
        assertEquals(3, heap.removeMin());
        assertEquals(5, heap.removeMin());
        assertEquals(6, heap.removeMin());

    }
}
