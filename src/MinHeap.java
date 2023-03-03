/**
 * This class implements the minHeap

 *
 */
@SuppressWarnings("rawtypes")
public class MinHeap {

    private Comparable[] heap; // Pointer to the heap array
    private int size; // Maximum size of the heap
    private int n; // Number of things now in heap

    /**
     * This method initializes the minHeap
     * 
     * @param heapArray
     *            This is the heap array
     * @param num
     *            the number of things in the array
     * @param max
     *            The max capacity of the array
     */
    MinHeap(Comparable[] heapArray, int num, int max) {
        heap = heapArray; // Initializing the heap
        n = num;
        size = max; // maximum size of the heap
        buildheap(); // Build a heap
    }


    /**
     * Function to check if the record at the specified position is a leaf
     * node.
     * 
     * @param pos
     *            pos to check for leaf
     * 
     * @return returns true if leaf, false otherwise
     */
    boolean isLeaf(int pos) {
        return (pos >= n / 2) && (pos < n);
    }


    /**
     * Function to check if the record at the specified position is a left
     * child
     * 
     * @param pos
     *            pos to check for left child
     * 
     * @return returns the int
     * 
     */
    int leftchild(int pos) {
        if (pos >= n / 2) {
            return -1;
        }
        return 2 * pos + 1;
    }


    /**
     * Function to check if the record at specified position is a parent
     * 
     * @param pos
     *            pos to check for parent
     * 
     * @return returns the int
     */
    int parent(int pos) {
        if (pos <= 0) {
            return -1;
        }
        return (pos - 1) / 2;
    }


    /**
     * Function to insert the value in the heap
     * 
     * @param val
     *            the value to insert
     */
    @SuppressWarnings("unchecked")
    void insert(Comparable val) {
        if (n >= size) {
            System.out.println("Heap is full");
            return;
        }
        int curr = n++;
        heap[curr] = val;

        while ((curr != 0) && (heap[curr].compareTo(heap[parent(curr)]) < 0)) {
            swap(heap, curr, parent(curr));
            curr = parent(curr);
        }
    }


    /**
     * Function to insert at the end of the heap
     * 
     * @param val
     *            The value to insert
     */
    void insertAtEnd(Comparable val) {
        heap[n] = val;
    }


    /**
     *
     * Function to build the heap which satisfies all conditions.
     */
    void buildheap() {
        for (int i = n / 2 - 1; i >= 0; i--) {
            siftdown(i);
        }
    }


    /**
     * 
     * Function to readjust the heap and pushing all the greater values
     * down if greater values are read from the input buffer.
     * 
     * @param pos
     *            the pos to siftfown
     */

    @SuppressWarnings("unchecked")
    void siftdown(int pos) {
        if ((pos < 0) || (pos >= n)) {
            return;
        }
        while (!isLeaf(pos)) {
            int j = leftchild(pos);
            if ((j < (n - 1)) && (heap[j].compareTo(heap[j + 1]) > 0)) {
                j++;
            }
            if (heap[pos].compareTo(heap[j]) <= 0) {
                return;
            }
            swap(heap, pos, j);
            pos = j;
        }
    }


    /**
     * Remove and return minimum value
     * 
     * @return the minimum value in the heap
     */
    Comparable removemin() {
        Comparable temp;
        if (n == 0) {
            return -1;
        }
        else {
            n--;
            swap(heap, 0, n);
            temp = heap[n];
            heap[n] = null;

        }

        siftdown(0);
        return temp;
    }


    /**
     * Function to get the smallest element from the heap which is the
     * first element in the minHeap.
     * 
     * @return the min value in the heap
     */
    Comparable getmin() {
        if (n == 0) {
            return -1;
        }
        else {
            return heap[0];
        }
    }


    /**
     * Function to modify the heap.
     * 
     * @param pos
     *            pos to modify
     * 
     * @param data
     *            The data to put in the pos given
     */
    void modify(int pos, Comparable data) {
        if (!(pos >= 0 && pos < n)) {
            return;
        }
        else {
            heap[pos] = data;
            update(pos);
        }
    }


    /**
     * Helper function to push down the greater values down the heap by
     * comparing the parent element and the child element.
     * 
     * @param pos
     *            the pos to update
     */
    @SuppressWarnings("unchecked")
    void update(int pos) {

        while ((pos > 0) && (heap[pos].compareTo(heap[parent(pos)]) < 0)) {
            swap(heap, pos, parent(pos));
            pos = parent(pos);
        }
        siftdown(pos);
    }


    /**
     * Function to swap 2 elements. helper function for Update and
     * siftDown.
     * 
     * @param h
     *            The minHeap array
     * 
     * @param a
     *            The index of the element to be swapped
     * @param b
     *            The second index of the element to be swapped.
     */
    void swap(Comparable[] h, int a, int b) {
        Comparable temp;
        temp = h[a];
        h[a] = h[b];
        h[b] = temp;
    }


    /**
     * Function to check if the heap is empty
     * 
     * @return true if heap is empty, false otherwise
     */
    boolean isEmpty() {
        return (n == 0);
    }


    /**
     * Function to get an element from the specified location in a heap
     * 
     * @param pos
     *            the pos to get
     * 
     * @return returns the element at given pos
     */
    public Comparable get(int pos) {
        return heap[pos];
    }


    /**
     * This method reorders the heap and pushes all the null to end
     */
    public void reorder() {
        int i;
        int j;
        int flag = 0;
        int length = heap.length;
        for (i = 0; i < length; i++) {
            flag = 0;
            if (heap[i] != null) {

                continue;
            }
            for (j = i + 1; i < length; j++) {

                if (heap[j] != null) {
                    Comparable temp = heap[j];
                    heap[j] = null;
                    heap[i] = temp;
                    break;
                }

                if (j == (length - 1)) {
                    flag = 1;
                    break;
                }
            }
            if (flag == 1) {
                break;
            }
        }
        n = i;
        buildheap();
    }


    /**
     * Function to clear the heap
     */
    public void clear() {
        n = 0;
        int i = 0;
        while (i < heap.length) {
            heap[i] = null;
            i++;
        }

    }


    /**
     * Function to put the value on a specified position in the heap
     * 
     * @param pos
     *            the pos to put
     * 
     * @param data
     *            the data to put at given pos
     * 
     */
    public void put(int pos, Comparable data) {
        heap[pos] = data;
    }

}
