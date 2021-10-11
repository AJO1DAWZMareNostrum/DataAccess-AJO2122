import java.io.FileInputStream;

import java.util.Scanner;
import java.nio.file.Files;
import java.nio.file.Paths;

public class StringSearcher {

    public static void main(String args[]) {

        Scanner sc = new Scanner(System.in);

        try {
            System.out.println("Enter the path of the file in which you want to search the String:");
            String filePath = sc.nextLine();
            FileInputStream fin = new FileInputStream(filePath);

            byte[] content = Files.readAllBytes(Paths.get(filePath));

            System.out.println("Enter the string you want to search its occurrence of in the file: ");
            String searchedString = sc.nextLine();

            String contentInString = new String(content);

            int i = 0;
            while (i < contentInString.length()) {
                int positionResult = searchedString.indexOf(contentInString);
                if ( positionResult >= 0 ) {
                    System.out.println("Found occurence at " + positionResult + "\n");
                }

                i++;
            }

        } catch (Exception e) {
            System.out.println(e);
            System.out.println("The file can´t be found.");
        }
    }
}
