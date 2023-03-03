import student.TestCase;

/**
 * This class if for testing the Record class
 * 
 */
public class RecordTest extends TestCase {

    private byte[] newByte;
    private Record record;

    /**
     * The main setUp of the test class
     */
    public void setUp() {
        newByte = new byte[5];
        record = new Record(newByte);
        // record.putRecord(newByte);

    }


    /**
     * Function to test the getsize method of the Record class.
     */
    public void testGetSize() {
        record.putRecord(new byte[16]);
        assertEquals(16, record.getSize());
    }


    /**
     * Function to test the getOnerecord method of the Record class.
     */
    public void testGetOneRecord() {
        assertEquals(newByte, record.getCompleteRecord());
    }
}
