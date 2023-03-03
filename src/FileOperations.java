import java.io.File;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;

/**
 * This class is for input and output operations of taking from the file and
 * writing to the file.
 */
public class FileOperations {

    private RandomAccessFile randomAcess;
    private FileOutputStream fileOutput;
    private ByteBuffer buffer;
    private long fileLength;

    /**
     * This takes a boolean value for the input
     */
    private boolean input;

    /**
     * Constructor for class FileOperations
     * 
     * @param n
     *            the buffer to allocate
     * @param bool
     *            the boolean value for input
     */
    public FileOperations(int n, boolean bool) {
        buffer = ByteBuffer.allocate(n);
        input = bool;
    }


    /**
     * Constructor for class FileOperations
     */
    public FileOperations() {
        // Default constructor
    }


    /**
     * This function reads form the file if file exists or creates a new file to
     * read from.
     * 
     * @param file
     *            Passing the file name to the function
     * @param flag
     *            Flag for the existence of the file.
     * @return
     *         returns an int
     */
    public int init(String file, boolean flag) {
        try {

            buffer = ByteBuffer.allocate(8192);
            if (flag) {
                randomAcess = new RandomAccessFile(file, "r");
                fileLength = randomAcess.length();
                input = true;
                buffer.clear();
                return randomAcess.read(buffer.array());

            }

            else

            {
                File runFile = new File(file);
                fileOutput = new FileOutputStream(runFile);
                input = false;
            }

        }
        catch (IOException exception) {
            System.out.println("Invalid input file!");
        }
        return 0;
    }


    /**
     * This function is to take the cursor to the specified position in the
     * file.
     * 
     * @param position
     *            Position at which the cursor has to currently point to.
     */
    public void seek(long position) {
        try {

            randomAcess.seek(position);

            buffer.clear();
            buffer.limit(buffer.position());
            buffer.position(0);
        }
        catch (IOException exception) {
            exception.printStackTrace();
        }
    }


    /**
     * This function is to remove all the remaining elements of the Output
     * buffer to the file.
     * 
     * @return
     *         the remaining records in the buffer.
     */
    public int emptyOutputBuffer() {
        if (buffer.position() == 0) {

            return 0;
        }

        int remaining = buffer.remaining();

        if (buffer.remaining() == buffer.limit())
        {
            remaining = 0;
        }
        
        try {
            fileOutput.write(buffer.array());
        }
        catch (IOException exception) {
            exception.printStackTrace();
        }
        bufferClear();
        return remaining;
    }


    /**
     * Get method to get the record from the file
     * 
     * @param destination
     *            The file provided to get the records from.
     * @return returns the length of the elements gotten from the file.
     */
    public int get(byte[] destination) {
        try {

            buffer.get(destination);
        }
        catch (BufferUnderflowException exception) {
            int value;
            try {
                buffer.clear();

                value = randomAcess.read(buffer.array());
            }

            catch (IOException e) {
                e.printStackTrace();
                value = 0;
            }

            if (value <= 0) {

                return 0;
            }
            buffer.get(destination);
        }
        return destination.length;
    }


    /**
     * Function to close the file.
     */
    public void close() {
        try {
            if (input)
            {
                randomAcess.close();
            }
            else
            {
                fileOutput.close();
            }
        }
        catch (IOException exception) {
            exception.printStackTrace();
        }
    }


    /**
     * This function is is to write to the file.
     * 
     * @param source
     *            The file on which we have to write.
     */
    public void write(byte[] source) {
        if (buffer.remaining() != 0) {

            buffer.put(source);
            return;
        }
        else {
            emptyOutputBuffer();
            buffer.put(source);
        }

    }


    /**
     * Function to clear the buffer and reinitializing the buffer
     */
    public void bufferClear() {

        if (buffer.remaining() != 0) {
            buffer.clear();
        }

        buffer.clear();
        byte[] bufferArray = buffer.array();
        for (int i = 0; i < bufferArray.length; i++) {
            bufferArray[i] = 0;

        }

    }


    /**
     * This function is to get the length of the file.
     * 
     * @return returns the length of the file
     */
    public long getFileLength() {
        return fileLength;
    }

}
