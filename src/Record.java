import java.nio.ByteBuffer;

public class Record implements Comparable<Record> {
    /*
     * Initializing one record consisting of 16 bytes.
     */
    private byte[] oneRecord;
    private int size = 16;

    /**
     * Empty Constructor for Completeness
     */
    public Record() {
        // default constructor
    }


    /**
     * Constructor for record, initializes a record object
     * @param record
     *              the length of record
     */
    public Record(byte[] record) {
        oneRecord = record;
    }


    /**
     * To get the Record
     * 
     * @return returns the complete record
     */
    public byte[] getCompleteRecord() {
        return oneRecord;
    }


    /**
     * To get the key of the Record
     * 
     * @return returns the key
     */
    public double getKey() {
        ByteBuffer buff = ByteBuffer.wrap(oneRecord);
        return buff.getDouble(8);
    }


    /**
     * Function to get the size of the record
     * 
     * @return returns the size
     */
    public int getSize() {
        return size;
    }


    /**
     * Inserts a new entry into the record
     * @param record
     *          The record to put
     */
    public void putRecord(byte[] record) {
        oneRecord = record;
    }


    /**
     * Function to get the Data part of the record
     * 
     * @return returns the data as a string
     */
    public String getData() {
        ByteBuffer buff = ByteBuffer.wrap(oneRecord);

        String s = String.valueOf(buff.getLong(0));
        return s;
    }


    @Override
    /**
     * Function to compare two records by their key values
     * 
     * @param toBeCompared
     *                  The record to compare this record to
     * @return returns 0 when equal, +ve or -ve otherwise
     */
    public int compareTo(Record toBeCompared) {
        return Double.compare(this.getKey(), toBeCompared.getKey());
    }

}
