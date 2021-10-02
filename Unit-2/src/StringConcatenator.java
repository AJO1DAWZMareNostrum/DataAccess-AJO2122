import java.util.Scanner;

public class StringConcatenator {

    public static void main(String[] args) {
        Scanner myScanner = new Scanner(System.in);
        System.out.print("Enter your first name: ");
        String name = myScanner.nextLine();
        System.out.print("Enter your surnames: ");
        String surnames = myScanner.nextLine();

        System.out.println("Hello, " + name + " " + surnames + "!");
        myScanner.close();
    }
}
