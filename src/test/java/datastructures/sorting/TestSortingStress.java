package datastructures.sorting;

import misc.BaseTest;
import misc.Searcher;

import org.junit.Test;

import datastructures.concrete.ArrayHeap;
import datastructures.concrete.DoubleLinkedList;
import datastructures.interfaces.IList;
import datastructures.interfaces.IPriorityQueue;

import static org.junit.Assert.assertTrue;

/**
 * See spec for details on what kinds of tests this class should include.
 */
public class TestSortingStress extends BaseTest {
    
    @Test(timeout=10*SECOND)
    public void testHeapManyElementsInsert() {
        IPriorityQueue<Integer> heap = new ArrayHeap<>(); 
        for (int i = 0; i < 100000; i++) {
            heap.insert(i);
            assertEquals(i + 1, heap.size());
            assertEquals(0, heap.peekMin());
        }
    }
    
    @Test(timeout=10*SECOND)
    public void testHeapInsertAndRemoveMany() { // bug 80464 is found in index 80511
        IPriorityQueue<Integer> heap = new ArrayHeap<>(); 
        for (int i = 0; i < 100000; i++) {
            heap.insert(i);
            assertEquals(i + 1, heap.size());
        }
        
        for (int i = 0; i < 100000; i++) {
            assertEquals(100000 - i, heap.size());
            int temp = heap.removeMin();
            System.out.println("size: " + heap.size());
            assertEquals(i, temp);
        }
        

        assertTrue(heap.isEmpty());
        
    }
    
    @Test(timeout=10*SECOND)
    public void testHeapInsertAndRemoveSameElement() {
        IPriorityQueue<Integer> heap = new ArrayHeap<>(); 
        for (int i = 0; i < 100000; i++) {
            heap.insert(1000);
            assertEquals(i + 1, heap.size());
        }
        
        for (int i = 0; i < 100000; i++) {
            assertEquals(1000, heap.removeMin());
            assertEquals(100000 - i - 1, heap.size());
        }
        
    }
    
    @Test(timeout=10*SECOND)
    public void testHeapBackward() {
        IPriorityQueue<Integer> heap = new ArrayHeap<>(); 
        for (int i = 0; i < 100000; i++) {
            heap.insert(100000 - i - 1);
            assertEquals(i + 1, heap.size());
        }
        
        for (int i = 0; i < 100000; i++) {            
            assertEquals(i, heap.removeMin());
        }
    }
    
    @Test(timeout=10*SECOND)
    public void testSearchManyElements() {
        IList<Integer> list = new DoubleLinkedList<>();
        for (int i = 0; i < 100000; i++) {
            list.add(i);
        }
        
        IList<Integer> top = Searcher.topKSort(1000, list);
        
        for (int i = 0; i < top.size(); i++) {
            assertEquals(i + list.size() - 1000, top.get(i));
        }
    }
    
    @Test(timeout=10*SECOND)
    public void testSearchBigK() {
        IList<Integer> list = new DoubleLinkedList<>();
        for (int i = 0; i < 100000; i++) {
            list.add(i);
        }
        
        IList<Integer> top = Searcher.topKSort(100000, list);
        
        assertEquals(100000, top.size());
        for (int i = 0; i < top.size(); i++) {
            assertEquals(i, top.get(i));
        }          
    }
    
    @Test(timeout=10*SECOND)
    public void testSearchZeroK() {
        IList<Integer> list = new DoubleLinkedList<>();
        for (int i = 0; i < 100000; i++) {
            list.add(i);
        }
        
        IList<Integer> top = Searcher.topKSort(0, list);
        
        assertTrue(top.isEmpty());          
    }
    
}
