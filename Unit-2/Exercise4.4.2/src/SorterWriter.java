import java.io.*;
import java.util.*;

public class SorterWriter {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        String name1, name2;
        System.out.println("Introduce the name of the first file to be sorted: ");
        name1 = sc.nextLine();
        System.out.println("Introduce the name of the second file to be sorted: ");
        name2 = sc.nextLine();

        try {
            BufferedReader in1 = new BufferedReader( new FileReader(name1));
            BufferedReader in2 = new BufferedReader( new FileReader(name2));
            BufferedWriter bw = new BufferedWriter( new FileWriter("sortedArchive.txt"));
            //TODO - Write in a file

            String line1 = in1.readLine();
            String line2 = in2.readLine();
            while (line1 != null || line2 != null) {
                if (line1 == null) {
                    bw.write("from file 2 -> " + line2);
                    bw.newLine();
                    line2 = in2.readLine();
                } else if (line2 == null) {
                    bw.write("from file 1 -> " + line1);
                    bw.newLine();
                    line1 = in1.readLine();
                } else if (line1.compareToIgnoreCase(line2) <= 0) {
                    bw.write("from file 1 -> " + line1);
                    bw.newLine();
                    line1 = in1.readLine();
                } else {
                    bw.write("from file 2 -> " + line2);
                    bw.newLine();
                    line2 = in2.readLine();
                }
            }

            in1.close();
            in2.close();
            bw.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
