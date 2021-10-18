import java.io.*;
import java.util.*;

public class FileWriterWithStops {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        String fileName;

        System.out.println("Type the name of the file in which you want to search: ");
        fileName = sc.nextLine();

        try {
            BufferedReader br = new BufferedReader( new FileReader(fileName));
            int numOfLines = 0;
            String lineToPrint;

            while (( lineToPrint = br.readLine() ) != null) {

                System.out.println(lineToPrint);
                numOfLines++;

                if (numOfLines % 23 == 0) {
                    System.out.println("Pulse Enter key to continue writing: ");
                    sc.nextLine();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
