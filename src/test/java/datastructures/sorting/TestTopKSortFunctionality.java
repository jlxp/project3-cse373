package datastructures.sorting;

import misc.BaseTest;
import datastructures.concrete.DoubleLinkedList;
import datastructures.interfaces.IList;
import misc.Searcher;
import org.junit.Test;

/**
 * See spec for details on what kinds of tests this class should include.
 */
public class TestTopKSortFunctionality extends BaseTest {
    @Test(timeout=SECOND)
    public void testSimpleUsage() {
        IList<Integer> list = new DoubleLinkedList<>();
        for (int i = 0; i < 20; i++) {
            list.add(i);
        }

        IList<Integer> top = Searcher.topKSort(5, list);
        assertEquals(5, top.size());
        for (int i = 0; i < top.size(); i++) {
            assertEquals(15 + i, top.get(i));
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
    }
    
    @Test(timeout=SECOND) 
    public void testBasicPeek() {
        
    }
    
    @Test(timeout=SECOND)
    public void testBasicRemove() {
        
    }
    
    @Test(timeout=SECOND)
    public void testInsertAndRemove() {
        
    }
    
    @Test(timeout=SECOND)
    public void testInsertRepeat() {
        
    }
    
    @Test(timeout=SECOND)
    public void testRemoveError () {
        
    }
    
    @Test(timeout=SECOND)
    public void testPeekError() {
        
    }
    
    @Test(timeout=SECOND)
    public void testNullKey() {
        
    }  
}
