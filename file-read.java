import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class FileMerger {
    public static void main(String[] args) {
        String file1 = "file1.txt";
        String file2 = "file2.txt";
        String outputFile = "output.txt";

        try (BufferedReader reader1 = new BufferedReader(new FileReader(file1));
             BufferedReader reader2 = new BufferedReader(new FileReader(file2));
             PrintWriter writer = new PrintWriter(new FileWriter(outputFile))) {

            String line1;
            String line2;

            while ((line1 = reader1.readLine()) != null 
                && (line2 = reader2.readLine()) != null) {
                writer.println(line1 + " " + line2);
            }

            // If file1 has more lines than file2, write the remaining lines from file1
            while ((line1 = reader1.readLine()) != null) {
                writer.println(line1);
            }

            // If file2 has more lines than file1, write the remaining lines from file2
            while ((line2 = reader2.readLine()) != null) {
                writer.println(line2);
            }

        } catch (IOException e) {
            System.err.println("Error reading or writing files: " + e.getMessage());
        }
    }
}
