/**
 *
 * This class is for performing replacement selection for sorting.
 */

public class ReplacementSelection {

    private static short count = 0;

    /*
     * object of the Record class.
     */
    private Record r = new Record();
    /*
     * object of the MinHeap class to initialize a MinHeap
     */
    private MinHeap minHeap;
    /**
     * Array to keep a track of all the runs for multiway-merge
     */
    private int[] runs = new int[100];

    /**
     * Initializing an input buffer to read from the file one block of
     * data
     */
    private FileOperations inputBuffer;

    /**
     * To keep a count of the runs and the blocks.
     */
    private int[] runBlockMarkers = new int[8];

    /**
     * Declaring the object of FileOperations
     */
    private FileOperations outputBuffer;

    /**
     * This method for replacement selection to take from the input,
     * process in the heap and give it to the output buffer.s
     * 
     * @param file The file through which data will be read
     * @return Returns the count of runs.
     */
    public short replacementSelection(String file) {
        // Initializing a buffer to read form the file
        count = 0;
        inputBuffer = new FileOperations();
        inputBuffer.init(file, true);
        // Initializing the minHeap
        int heapRecord = 65536 / r.getSize();
        minHeap = new MinHeap(new Record[heapRecord], 0, heapRecord);
        int byteCount = 0;

        for (int i = 0; i < 4096; i++) {
            byte[] completeRecord = new byte[r.getSize()];
            int bytesRead = inputBuffer.get(completeRecord);
            Record record = new Record(completeRecord);
            minHeap.insert(record);
            // Keep inserting the record
        }
        inputBuffer.seek(65536);
        outputBuffer = new FileOperations();
        outputBuffer.init("run_file.bin", false);
        int pos = 0;
        boolean eof = false;

        while (!(eof && minHeap.isEmpty())) {
            while (!(minHeap.isEmpty())) {

                Record prevRecord = (Record) minHeap.getmin();
                // Keep record of the address.
                pos++;
                outputBuffer.write(prevRecord.getCompleteRecord());
                byte[] completeRecord = new byte[r.getSize()];

                if (inputBuffer.get(completeRecord) == 0) {
                    eof = true;
                }
                // Remove minimum if you have reached end of file
                if (eof) {
                    minHeap.removemin();
                    continue;
                }

                Record record = new Record(completeRecord);
                // Compare old record to new one.
                if (record.compareTo(prevRecord) < 0) {

                    minHeap.removemin();
                    minHeap.insertAtEnd(record);

                } 
                else {
                    minHeap.modify(0, record);
                }
            }

            // Empty the buffer and add the address
            int outputRecord = outputBuffer.emptyOutputBuffer()
                    / r.getSize();
            pos += outputRecord;

            count++;
            // Keep track of each runfile address and index
            runs[count] = pos;
            minHeap.reorder();
        }

        outputBuffer.emptyOutputBuffer();
        inputBuffer.close();
        outputBuffer.close();
        return count;
    }

    /**
     * This method is for the multiway merge which takes somewhat sorted
     * file and then keeps processing chunks
     * 
     * @param file the file to perform the multiway merge on
     */
    public void multiwayMerge(String file) {
        int[] outputRuns = new int[100];
        String mergeInputFileName = "run_file.bin";

        int pos;
        int end;
        short runNum = 1;
        // System.out.println("Count = "+ count);
        // Run this until only 1 run is left
        while (count > 1) {

            inputBuffer = new FileOperations();
            inputBuffer.init(mergeInputFileName, true);
            outputBuffer = new FileOperations();
            outputBuffer.init(file, false);
            // Calculate start and end position for the run file
            for (int start = 0; start < count; start = start + 8) {
                if ((start + 8) < count) {
                    end = start + 8;
                } 
                else {
                    end = count;
                }
                pos = merge(start, end);
                // Output file run address
                outputRuns[runNum] = pos / r.getSize();
                // OutputFile run counter
                runNum++;

            }
            inputBuffer.close();
            outputBuffer.close();
            count = --runNum;
            runs = outputRuns;
            runNum = 0;
            // Copy output file as inout file.
            Externalsort.copyFile(file, mergeInputFileName);
        }

    }

    /**
     * This is a helper method for multi-way merge for the actual merging
     * logic
     * 
     * @param start The beginning position in the runfile to start
     *              merging.
     * @param end   The ending position of the runfile till where to
     *              merge.
     * @return Returns the output runfile index
     */

    public int merge(int start, int end) {
        /**
         * To keep a count of which block is being processed in the heap.
         */
        int[] heapBlockMarkers = new int[8];

        minHeap.clear();
        int temp = 0;
        int pos = 0;

        for (int i = start; i < end; i++) {
            // Load first blocks from runfile to heap
            loadBlock(i);
        }

        int runsLeft = end - start;
        // Calculate heap block starting address
        while (temp < 8) {
            heapBlockMarkers[temp] = temp * r.getSize();
            temp++;
        }

        int prev;
        int next;

        while (runsLeft > 0) {

            pos++;
            int lastIndex;
            if (end % 8 == 0) {
                lastIndex = end;
            } 
            else {
                lastIndex = end % 8;
            }
            // Find minimum value of all heap block
            int minPos = findMin(start % 8, lastIndex, heapBlockMarkers);
            Record minRecord = (Record) minHeap.get(minPos);
            // Write it to output buffer
            outputBuffer.write(minRecord.getCompleteRecord());

            int i = minPos / r.getSize();
            heapBlockMarkers[i]++;

            if (runBlockMarkers[i] < 0) {
                continue;
            }

            if (heapBlockMarkers[i] % r.getSize() == 0) {

                heapBlockMarkers[i] = i * r.getSize();

                prev = runBlockMarkers[i];
                next = (runs[i + 1] - runs[i]);
                if (prev >= next) {
                    runBlockMarkers[i] = -1;
                    heapBlockMarkers[i] = -1;
                    prev = 0;
                    runsLeft--;
                    continue;
                }
                // Load a new block from the run file
                loadBlock(i);
            }
        }

        return pos;
    }

    /**
     * This method loads each block for merging.
     * 
     * @param run Number of the run at which the block is being processed
     *            for merge.
     */
    public void loadBlock(int run) {

        // Calculate address to load into heap from run file
        int address = runs[run] + runBlockMarkers[run];
        address = address * 16;
        inputBuffer.seek(address);

        // System.out.println(address);

        byte[] byteRecord;
        int j = 0;
        Record rec = new Record();
        while (j < rec.getSize()) {

            byteRecord = new byte[16];
            inputBuffer.get(byteRecord);
            // Calculate heap position
            int heapPosition = j + run * rec.getSize();
            runBlockMarkers[run]++;
            // Add the block to the heap
            minHeap.put(heapPosition, new Record(byteRecord));
            j++;
        }
    }

    /**
     * 
     * This method is to find the minimum element in the heap
     * 
     * @param start           The starting position
     * @param end             The ending position
     * @param heapBlockMarker To keep a track of the block which is being
     *                        processed in the heap.
     * @return Returns the position of the smallest element in the heap.
     */
    public int findMin(int start, int end, int[] heapBlockMarker) {
        Record min = null;
        boolean flag = true;
        int minPos = -1;

        for (int i = start; i < end; i++) {

            // No element in heap
            if (heapBlockMarker[i] == -1) {
                continue;
            }

            // First record
            if (flag || min == null) {
                min = (Record) minHeap.get(heapBlockMarker[i]);
                flag = false;
                minPos = heapBlockMarker[i];
                continue;
            }

            Record record = (Record) minHeap.get(heapBlockMarker[i]);

            if (record == null || record.getKey() == 0) {
                continue;
            }
            // If new record is smaller than min then make that as min.
            if (min.compareTo(record) > 0) {
                minPos = heapBlockMarker[i];
                min = record;
            }
        }
        return minPos;
    }

}
