package datastructures.sorting;

import misc.BaseTest;
import datastructures.concrete.ArrayHeap;
import datastructures.concrete.DoubleLinkedList;
import datastructures.interfaces.IList;
import datastructures.interfaces.IPriorityQueue;
import misc.Searcher;
import misc.exceptions.EmptyContainerException;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Iterator;

import org.junit.Test;

/**
 * See spec for details on what kinds of tests this class should include.
 */
public class TestTopKSortFunctionality extends BaseTest {
    @Test(timeout=SECOND)
    public void testSimpleUsage() {
        IList<Integer> list = new DoubleLinkedList<>();
        for (int i = 0; i < 100; i++) {
            list.add(i);
        }

        IList<Integer> top = Searcher.topKSort(10, list);
        assertEquals(10, top.size());
        for (int i = 0; i < top.size(); i++) {
            assertEquals(90 + i, top.get(i));
        }
    }
    
    @Test(timeout=SECOND)
    public void testElementLessCase() {
        IList<Integer> list = new DoubleLinkedList<>();
        list.add(5);
        list.add(4);
        list.add(7);
        list.add(9);
        list.add(3);
        
        IList<Integer> top = Searcher.topKSort(7, list);
        assertEquals(5, top.size());
        
        assertEquals(9, top.remove());
        assertEquals(7, top.remove());
        assertEquals(5, top.remove());
        assertEquals(4, top.remove());
        assertEquals(3, top.remove());
        assertTrue(top.isEmpty());
    }
    
    @Test(timeout=SECOND) 
    public void testMultipleSameElement() {
        IList<Integer> list = new DoubleLinkedList<>();
        
        for (int i = 0; i < 100; i++) {
            list.add(5);
        }
        
        IList<Integer> top = Searcher.topKSort(10, list);
        assertEquals(10, top.size());
        
        for (int i = 0; i < top.size(); i++) {
            assertEquals(5, top.get(i));
        }
        
    }
    
    @Test(timeout=SECOND)
    public void testIllegalCase() {
        IList<Integer> list = new DoubleLinkedList<>();
        for (int i = 0; i < 20; i++) {
            list.add(i);
        }

        try {
            IList<Integer> top = Searcher.topKSort(-1, list);
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // do nothing yay
        }
    }
    
    @Test(timeout=SECOND)
    public void testNullElementCase() {
        IList<Integer> list = new DoubleLinkedList<>();
        for (int i = 0; i < 100; i++) {
            list.add(i);
        }
        
        list.add(null);
        
        try {
            IList<Integer> top = Searcher.topKSort(10, list);
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // do nothing yay
        }
    }
    
    @Test(timeout=SECOND)
    public void testEmptyCase() {
        IList<Integer> list = new DoubleLinkedList<>();
        IList<Integer> top = Searcher.topKSort(5, list);
        
        assertTrue(top.isEmpty());
        
    }
    
    @Test(timeout=SECOND)
    public void testBigK() {
        IList<Integer> list = new DoubleLinkedList<>();
        for (int i = 0; i < 100; i++) {
            list.add(i);
        }
        
        IList<Integer> top = Searcher.topKSort(100, list);
        
        for (int i = 0; i < top.size(); i++) {
            assertEquals(i, top.get(i));
        }
    }
    
    @Test(timeout=SECOND)
    public void testHugeK() {
        IList<Integer> list = new DoubleLinkedList<>();
        for (int i = 0; i < 100; i++) {
            list.add(i);
        }
        
        IList<Integer> top = Searcher.topKSort(1000, list);
        
        for (int i = 0; i < top.size(); i++) {
            assertEquals(i, top.get(i));
        }
    }
    
    @Test(timeout=SECOND)
    public void testKZero() {
        IList<Integer> list = new DoubleLinkedList<>();
        for (int i = 0; i < 20; i++) {
            list.add(i);
        }
        
        IList<Integer> top = Searcher.topKSort(0, list);
        
        assertTrue(top.isEmpty());
    }
    
    @Test(timeout=SECOND)
    public void testKZeroError() {
        IList<Integer> list = new DoubleLinkedList<>();
        for (int i = 0; i < 20; i++) {
            list.add(i);
        }
        
        IList<Integer> top = Searcher.topKSort(0, list);
        
        try {
            top.remove();
            fail("Expected EmptyContainerException");
        } catch (EmptyContainerException e) {
            //do nothing
        }
    }
    
    @Test(timeout=SECOND)
    public void testBackwardBigK() {
        IList<Integer> list = new DoubleLinkedList<>();
        for (int i = 100; i > 0; i--) {
            list.add(i);
        }
        
        IList<Integer> top = Searcher.topKSort(100, list);
        
        for (int i = 0; i < top.size(); i++) {
            assertEquals(i + 1, top.get(i));
        }
    }
    
    @Test(timeout=SECOND)
    public void testBackward() {
        IList<Integer> list = new DoubleLinkedList<>();
        for (int i = 100; i > 0; i--) {
            list.add(i);
        }
        
        IList<Integer> top = Searcher.topKSort(20, list);
        
        for (int i = 0; i < top.size(); i++) {
            assertEquals(81 + i, top.get(i));
        }
    }
    
    @Test(timeout=SECOND)
    public void testInput() {
        IList<Integer> list = new DoubleLinkedList<>();
        IList<Integer> list2 = new DoubleLinkedList<>();
        for (int i = 100; i > 0; i--) {
            list.add(i);
            list2.add(i);
        }
        
        IList<Integer> top = Searcher.topKSort(20, list);
        
        assertEquals(list.size(), list2.size());        
    }
    
    @Test(timeout=SECOND)
    public void testSingleElementList() {
        IList<Integer> list = new DoubleLinkedList<>();
        list.add(1);
        
        IList<Integer> top = Searcher.topKSort(1, list);
        
        assertEquals(1, top.get(0));
        
        IList<Integer> top2 = Searcher.topKSort(0, list);
        
        assertTrue(top2.isEmpty());
    }
    
    @Test(timeout=SECOND)
    public void testSingleElementListError() {
        IList<Integer> list = new DoubleLinkedList<>();
        list.add(1);
        
        IList<Integer> top = Searcher.topKSort(1, list);
        
        assertEquals(1, top.remove());
        
        try {
            top.remove();
            fail("Expected EmptyContainerException");
        } catch (EmptyContainerException e) {
            // do nothing
        }
        
        list.remove();
        assertTrue(top.isEmpty());
        
        IList<Integer> top2 = Searcher.topKSort(1, list);
        
        try {
            top2.remove();
            fail("Expected EmptyContainerException");
        } catch (EmptyContainerException e) {
            // do nothing
        }
        
    }
    
    @Test(timeout=SECOND)
    public void testK1() {
        IList<Integer> list = new DoubleLinkedList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        
        IList<Integer> top = Searcher.topKSort(1, list);
        
        assertEquals(3, top.get(0));        
    }
    
    @Test(timeout=SECOND)
    public void testStringList() {
        IList<String> list = new DoubleLinkedList<>();
        
        list.add("hello");
        list.add("hewwo");
        list.add("qwert");
        list.add("apple");
        list.add("hmmmm");
                
        IList<String> top = Searcher.topKSort(3, list);
        
        assertEquals("hewwo", top.get(0));
        assertEquals("hmmmm", top.get(1));
        assertEquals("qwert", top.get(2));
    }
    
    @Test(timeout=SECOND)
    public void testSortAndIter() {
        IList<Integer> list = new DoubleLinkedList<>();
        
        
        for (int i = 0; i < 100; i++) {
            list.add(i);
        }
        
        Iterator<Integer> iter = list.iterator(); 
        
        IList<Integer> top = Searcher.topKSort(100, list);
        int i = 0;
        while (iter.hasNext()) {
            assertEquals(iter.next(), top.get(i));
            i++;          
        }
    }
    
    @Test(timeout=SECOND)
    public void testSortAndIter2() {
        IList<Integer> list = new DoubleLinkedList<>();
        IList<Integer> list2 = new DoubleLinkedList<>();
        
        for (int i = 0; i < 100; i++) {
            list.add(i);
            
        }
        
        for (int i = 100; i < 120; i++) {
            list.add(i);
            list2.add(i);
        }
        
        Iterator<Integer> iter = list2.iterator(); 
        
        IList<Integer> top = Searcher.topKSort(20, list);
        int i = 0;
        while (iter.hasNext()) {
            assertEquals(iter.next(), top.get(i));
            i++;          
        }
    }
    
    @Test(timeout=SECOND)
    public void testSortAndHeap() {
        IList<Integer> list = new DoubleLinkedList<>();
        IPriorityQueue<Integer> heap = new ArrayHeap<>(); 

        for (int i = 0; i < 100; i++) {
            list.add(i);
            
        }
        
        for (int i = 100; i < 200; i++) {
            list.add(i);
            heap.insert(i);
        }
        
        IList<Integer> top = Searcher.topKSort(100, list);
        
        for (int i = 0; i < 100; i++) {
            assertEquals(top.get(i), heap.removeMin()); 
        }
    }
    
    @Test(timeout=SECOND)
    public void testOutput() {
        IList<Integer> list = new DoubleLinkedList<>();
        
        for (int i = 0; i < 100; i++) {
            list.add(i);
        }
        
        IList<Integer> top = Searcher.topKSort(20, list);
        
        for (int i = 0; i < 20; i++) {
            assertEquals(100 - 1 - i, top.remove());
        }
        
        assertTrue(top.isEmpty());
    }
    
    @Test(timeout=SECOND)
    public void testOutputIterator() {
        IList<Integer> list = new DoubleLinkedList<>();
        
        for (int i = 0; i < 100; i++) {
            list.add(i);
        }
        
        IList<Integer> top = Searcher.topKSort(20, list);

        Iterator<Integer> iter = top.iterator();
        int i = 0; 
        while (iter.hasNext()) {
            assertEquals(iter.next(), list.get(80+i));
            i++;
        }
        
        assertFalse(top.isEmpty());
    }
    
}
