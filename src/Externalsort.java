

public class Externalsort {
    /**
     * Counter variable
     */
    private static short counter = 0;

    /**
     * The main function
     * 
     * @param args To take args
     */
    public static void main(String[] args) {

        ReplacementSelection replacement = new ReplacementSelection();
        counter = replacement.replacementSelection(args[0]);
        // System.out.println(counter);
        // If the counter is 1, it is already sorted.
        if (counter == 1) {
            // Copy run_file to original file.
            copyFile("run_file.bin", args[0]);
            printOutput(args[0]);
            return;
        }

        replacement.multiwayMerge(args[0]);
        printOutput(args[0]);

    }

    /**
     * Function to print the output in the specified format
     * 
     * @param fileName The name of the file
     */
    public static void printOutput(String fileName) {

        FileOperations inputBuffer;
        inputBuffer = new FileOperations();
        inputBuffer.init(fileName, true);

        long index = 0;
        int columns = 0;
        long length = inputBuffer.getFileLength();

        while (index < length) {
            inputBuffer.seek(index);
            byte[] completeRecord = new byte[16];
            index = index + 8192;
            columns++;

            inputBuffer.get(completeRecord);
            Record record = new Record(completeRecord);

            if (record.getKey() != 0.0) {
                System.out.print(record.getData() + " " + record.getKey());
            }
            // Print 5 per column
            if (columns < 5) {
                System.out.print(" ");
                continue;
            }
            columns = 0;
            System.out.print("\n");

        }
        System.out.println();
        inputBuffer.close();
    }

    /**
     * Function to copy from the console the text and putting it into a
     * binary file
     * 
     * @param file1 The file to copy from
     * @param file2 The file to copy into
     */
    public static void copyFile(String file1, String file2) {

        byte[] record = new byte[16];
        FileOperations inputBuffer;
        inputBuffer = new FileOperations();
        inputBuffer.init(file1, true);
        FileOperations outputBuffer;
        outputBuffer = new FileOperations();
        outputBuffer.init(file2, false);

        // Copy data from one file and write to another.
        while (inputBuffer.get(record) != 0) {
            outputBuffer.write(record);
            record = new byte[16];
        }

        outputBuffer.emptyOutputBuffer();
        inputBuffer.close();
        outputBuffer.close();
    }

}
