import java.io.IOException;

import student.TestCase;
import student.testingsupport.PrintStreamWithHistory;

/**
 * This class is to test the FileOperations class

 */
public class FileOperationsTest extends TestCase {

    /**
     * Declaration of the FileOperations object
     */
    private FileOperations fo;

    /**
     * Declaration of the PrintStreamWitHistory object to access the console
     */
    private PrintStreamWithHistory cons;

    /**
     * The setUp method for this class
     */
    public void setUp() {
        /**
         * Initialization of the FileOperations object fo
         */
        fo = new FileOperations(0, true);
        /**
         * Initialization of the PrintStreamWithHistory object
         */
        cons = (PrintStreamWithHistory)System.out;
        /**
         * Assigning the number of bytes
         */
        byte[] recordByte = new byte[8195];
        /**
         * Creating a new record with specified bytes
         */
        new Record(recordByte);
    }


    /**
     * Function to test if the input buffer takes in the values from the given
     * file.
     */
    public void testReadInputBuffer() {
        try {
            fo.init("ok.txt", false);
        }
        catch (Exception exception) {
            assertTrue(exception instanceof IOException);
        }

        try {
            fo.init("ok.txt", true);
        }
        catch (Exception exception) {
            assertTrue(exception instanceof IOException);
        }

        try {
            fo.init("ok", true);
        }
        catch (Exception exception) {
            assertTrue(exception instanceof IOException);
            assertEquals("Invalid input file!", cons.getHistory());
        }
    }


    /**
     * Function to test the write method if the outputbBuffer writes to the file
     * correctly.
     */
    public void testWrite() {
        fo = new FileOperations(8192, true);
        byte[] newByte = new byte[8192];
        fo.write(newByte);
        assertEquals(0, fo.getFileLength());

    }


    /**
     * Function to test if the buffer gets empty on t
     * =calling the function.
     */
    public void testEmptyOutputBuffer() {
        fo.emptyOutputBuffer();

        fo.bufferClear();
        fo.init("ok.txt", true);
        fo.seek(0);

        fo = new FileOperations(8192, true);
        fo.emptyOutputBuffer();

        assertEquals(0, fo.getFileLength());
    }


    /**
     * Function to test if the buffer gets clear on calling the function
     */
    public void testBufferClear() {
        fo.bufferClear();
        assertEquals(0, fo.getFileLength());

        fo = new FileOperations(8192, true);
        fo.bufferClear();
        assertEquals(0, fo.getFileLength());
    }


    /**
     * Function to test if the file closes properly on calling the function
     */
    public void testClose() {
        fo = new FileOperations(8192, true);
        try {
            byte[] newByte = new byte[8192];
            fo.write(newByte);
            fo.close();
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }

        fo = new FileOperations(8192, false);
        try {
            fo.init("ok.txt", false);
            byte[] newByte = new byte[8192];
            fo.write(newByte);
            fo.close();
        }
        catch (Exception exception) {
            assertTrue(exception instanceof IOException);
        }

    }


    /**
     * Function to test if seek correctly seeks the cursor to the specified
     * location
     */
    public void testSeek() {
        fo.init("ok.txt", true);
        fo.seek(0);

        try { 
            fo.seek(10);
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
        assertEquals(0, fo.getFileLength());
    }


    /**
     * Function to test if the get method returns the correct element from the
     * specified index
     */
    public void testGet() {
        fo = new FileOperations(8192, true);
        try {
            byte[] newByte = new byte[8192];
            fo.write(newByte);
            fo.get(newByte);
        }
        catch (Exception exception) {
            assertTrue(exception instanceof NullPointerException);
        }

        fo.bufferClear();
        assertEquals(0, fo.getFileLength());
        try {
            byte[] newByte = new byte[8192];
            fo.get(newByte);
        }
        catch (Exception exception) {
            assertFalse(exception instanceof NullPointerException);
        }

    }


    /**
     * Function to test if the file length is correctly returned on calling the
     * function.
     */
    public void testGetFileLength() {
        assertEquals(0, fo.getFileLength());
    }
}
