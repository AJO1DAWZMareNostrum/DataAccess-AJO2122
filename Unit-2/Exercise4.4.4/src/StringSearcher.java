import java.io.*;
import java.util.*;

public class StringSearcher {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String fileName;
        String searchedString;

        System.out.println("Introduce the name of the file on which you want to search: ");
        fileName = sc.nextLine();
        System.out.println("Enter the string to be searched inside the file: ");
        searchedString = sc.nextLine();

        try {
            BufferedReader buffRead = new BufferedReader(new FileReader(fileName));

            String actualLine;
            int numOfLines = 0;

            while ( (actualLine = buffRead.readLine()) != null) {
                if (actualLine.contains(searchedString)){
                    System.out.printf("Found in line %d: %s \n", numOfLines, actualLine);
                }
                numOfLines++;
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
