import student.TestCase;
import student.testingsupport.PrintStreamWithHistory;

/**
 * This class is to test the minHeap class

 */

public class MinHeapTest extends TestCase {
    /*
     * Declaration of object of the minHeap class to use the minHeap.
     */
    private MinHeap minHeap;
    /*
     * Declaration of the PrintStreamWithHistory object to access elements from
     * the console.
     */
    private PrintStreamWithHistory cons;

    /**
     * The setup method for testing
     */
    public void setUp() {
        minHeap = new MinHeap(new String[5], 0, 5);
        cons = (PrintStreamWithHistory)System.out;
    }

/*
 * Function to test the size of the heap
 */
// public void testHeapSize() {
// assertEquals(0, minHeap.heapsize());
// }


    /**
     * Function to test if the element or the record is a leaf node
     */
    public void testIsLeaf() {
        minHeap.insert("0");
        minHeap.insert("1");
        minHeap.insert("2");
        minHeap.insert("5");
        minHeap.insert("3");

        assertFalse(minHeap.isLeaf(0));
        assertFalse(minHeap.isLeaf(6));
        assertTrue(minHeap.isLeaf(4));

    }


    /**
     * Function to test if the program computes correct left child of the
     * element
     * or the record
     */
    public void testLeftChild() {
        minHeap.insert("0");
        minHeap.insert("1");
        minHeap.insert("2");
        minHeap.insert("5");
        minHeap.insert("3");

        assertEquals(3, minHeap.leftchild(1));
        assertEquals(-1, minHeap.leftchild(2));
    }


    /**
     * Function to test if the program computes the correct parent of the
     * element or
     * the record.
     */
    public void testParent() {
        minHeap.insert("0");
        minHeap.insert("1");
        minHeap.insert("2");
        minHeap.insert("5");
        minHeap.insert("3");

        assertEquals(0, minHeap.parent(1));
        assertEquals(-1, minHeap.parent(0));
    }


    /**
     * Function to test the insert function of the minheap
     */
    public void testInsert() {

        minHeap.insert("0");
        minHeap.insert("1");
        minHeap.insert("2");
        minHeap.insert("5");
        minHeap.insert("3");

        minHeap.insert("9");
        assertEquals("Heap is full\n", cons.getHistory());
    }


    /**
     * Function to test if the element or the record is properly inserted at the
     * end
     * of the minHeap.
     */

    public void testInsertAtEnd() {
        minHeap.insert("1");
        minHeap.insertAtEnd("0");

        assertEquals("0", minHeap.get(1));
    }


    /**
     * Function to test the siftDown method of the minHeap.
     */
    public void testsiftDown() {
        minHeap.insert("0");
        minHeap.insert("1");
        minHeap.insert("2");
        minHeap.insert("5");
        minHeap.insert("3");

        minHeap.siftdown(-1);
        minHeap.siftdown(19);
        
        assertFalse(minHeap.isEmpty());
    }


    /**
     * Function to test the smallest element from the minHeap.
     */
    public void testremoveMin() {
        assertEquals(-1, minHeap.removemin());

        minHeap.insert("0");
        minHeap.insert("1");
        minHeap.insert("2");
        minHeap.insert("5");
        minHeap.insert("3");

        assertEquals("0", minHeap.removemin());
    }


    /**
     * Function to test the retrieve the smallest element of the minHeap.
     */
    public void testGetMin() {
        assertEquals(-1, minHeap.getmin());

        minHeap.insert("0");
        minHeap.insert("1");
        minHeap.insert("2");
        minHeap.insert("5");
        minHeap.insert("3");

        assertEquals("0", minHeap.getmin());
    }


    /**
     * Function to test the modify element method of the minHeap to modify the
     * given
     * element.
     */
    public void testModify() {

        minHeap.insert("0");
        minHeap.insert("1");
        minHeap.insert("2");
        minHeap.insert("5");
        minHeap.insert("3");

        assertEquals("0", minHeap.getmin());

        minHeap.modify(0, "7");

        minHeap.modify(-1, "7");
        minHeap.modify(200, "7");
    }


    /**
     * Function to test the Update method of the minHeap
     */
    public void testUpdate() {
        minHeap.insert("0");
        minHeap.insert("1");
        minHeap.insert("7");
        minHeap.insert("9");
        minHeap.insert("13");

        minHeap.modify(3, "-1");
        minHeap.update(3);
        
        assertFalse(minHeap.isEmpty());
    }


    /**
     * Function to test IsEmpty function to check if the heap is empty.
     */

    public void testIsEmpty() {
        assertTrue(minHeap.isEmpty());

        minHeap.insert("0");
        assertFalse(minHeap.isEmpty());
    }


    /**
     * Function to test the reorder function of the minHeap
     */
    public void testReorder() {

        minHeap.reorder();
        minHeap.reorder();

        minHeap.put(4, null);
        minHeap.reorder();

        minHeap.insert("10");

        minHeap.insert("");
        minHeap.reorder();

        minHeap.insert("2");
        minHeap.reorder();

        minHeap.insert("4");
        minHeap.insert("5");

        minHeap.reorder();

        minHeap.removemin();

        minHeap.put(4, null);
        minHeap.put(2, null);
        try {
            minHeap.reorder();
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
        
        assertFalse(minHeap.isEmpty());

    }


    /**
     * Function to test if the heap gets cleared by the clear function
     */
    public void testClear() {
        minHeap.insert("0");
        minHeap.insert("1");
        minHeap.insert("2");

        assertEquals("0", minHeap.get(0));

        minHeap.clear();
        assertTrue(minHeap.isEmpty());
    }


    /**
     * Function to test the get method of the heap to get the specified element
     * on
     * that index.
     */
    public void testGet() {
        minHeap.insert("0");
        minHeap.insert("1");
        minHeap.insert("2");

        assertEquals("0", minHeap.get(0));
    }


    /**
     * Function to test the put method of the heap if it puts the specified
     * element
     * on the given index or location.
     */
    public void testPut() {
        minHeap.insert("0");
        minHeap.put(0, "2");

        assertEquals("2", minHeap.get(0));
    }

}
