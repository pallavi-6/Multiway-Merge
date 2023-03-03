import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import student.TestCase;

/**
 * This class is to test the main class External sort
 */
public class ExternalsortTest extends TestCase {

    /**
     * This method tests the external sort init
     */
    public void testExternalsortInit() {
        Externalsort sorter = new Externalsort();
        assertNotNull(sorter);
    }


    /**
     * This method tests the sample input
     */
    public void testSampleInput16() {
        Externalsort.copyFile("sampleInput16-backup.txt", "sampleInput16.bin");
        String inputFileName = "sampleInput16.bin";
        String outputFileName = "Expected_Std_Out.txt";
        Externalsort.main(new String[] { inputFileName });
        assertFuzzyEquals(getFileContent(outputFileName), systemOut()
            .getHistory());

    }


    /**
     * Utility method to get File contents as string
     * 
     * @param fileName
     *            name of the file
     * @return String
     *         the contents of the file
     */
    public String getFileContent(String fileName) {
        Scanner scan;
        String output = "";
        try {
            scan = new Scanner(new File(fileName));
            while (scan.hasNextLine()) {
                output = output + scan.nextLine();
                output = output + "\n";
            }
            scan.close();
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return output;
    }

}
