import java.io.*;
import java.util.Scanner;
import java.nio.file.Files;
import java.nio.file.Paths;

public class NumberedFileWriter {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.println("Write the name of the file you want to write");
        String fileName = sc.nextLine();

        PrintWriter printWriter = null;
        try {
            // If a file already exists with that name
            if (new File( fileName ).exists()) {
                System.out.println("Do you want to overwrite the file (Y), or append it (N)? Y/N");
                String yesOrNo = sc.nextLine();

                if (yesOrNo == "Y" || yesOrNo != "N") {
                    System.out.println("Do you want to overwrite the file (Y), or append it (N)? Y/N");
                } else {
                    throw new IllegalArgumentException();
                }

                if (yesOrNo.equals("Y")) {
                    // Write the file by overwriting previous data, without appending
                    printWriter = new PrintWriter(new BufferedWriter(
                            new FileWriter(fileName)));

                    boolean moreLines = true;
                    while (moreLines) {
                        System.out.println("Enter the line you want to write: ");
                        String line = sc.nextLine();
                        long lineCounter = Files.lines( Paths.get(fileName)).count();
                        printWriter.println(lineCounter + ":  " + line);

                        System.out.println("Do you want to introduce more lines? Y/N");
                        String lines = sc.nextLine();
                        if (lines.equals("Y")) {
                            moreLines = true;
                        } else if (lines.equals("N")) {
                            moreLines = false;
                        } else {
                            throw new IllegalArgumentException();
                        }
                    }
                } else {
                    // Write the file without overwriting previous data, by appending the new lines
                    printWriter = new PrintWriter(new BufferedWriter(
                            new FileWriter(fileName, true)));

                    boolean moreLines = true;
                    while (moreLines) {
                        System.out.println("Enter the line you want to write: ");
                        String line = sc.nextLine();
                        long lineCounter = Files.lines(Paths.get(fileName)).count();
                        printWriter.println(lineCounter + ":  " + line);

                        System.out.println("Do you want to introduce more lines? Y/N");
                        String lines = sc.nextLine();
                        if (lines.equals("Y")) {
                            moreLines = true;
                        } else if (lines.equals("N")) {
                            moreLines = false;
                        } else {
                            throw new IllegalArgumentException();
                        }
                    }
                }
            }
            // If the file doesn´t already exists
             else {
                // Write the file without overwriting previous data, by appending the new lines
                printWriter = new PrintWriter(new BufferedWriter(
                        new FileWriter(fileName, true)));

                boolean moreLines = true;
                while (moreLines) {
                    System.out.println("Enter the line you want to write: ");
                    String line = sc.nextLine();
                    long lineCounter = Files.lines(Paths.get(fileName)).count();
                    printWriter.println(lineCounter + ":  " + line);

                    System.out.println("Do you want to introduce more lines? Y/N");
                    String lines = sc.nextLine();
                    if (lines.equals("Y")) {
                        moreLines = true;
                    } else if (lines.equals("N")) {
                        moreLines = false;
                    } else {
                        throw new IllegalArgumentException();
                    }
                }
            }

             printWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
