import java.util.Scanner;

public class DateReader {

    public static void main(String[] args) {
        // Creates the Scanner instance
        Scanner myScanner = new Scanner(System.in);

        // Prompts the user to introduce a date
        System.out.print("Enter a day: ");
        int day = myScanner.nextInt();
        System.out.print("Enter a month: ");
        int month = myScanner.nextInt();
        System.out.print("Enter a year: ");
        int year = myScanner.nextInt();

        // To check if the day and month are correct. and return an input based on that
        if (day < 1 || day > 31) {
            System.out.println("The day introduced is incorrect: it isn´t between 1 and 31");
        } else if (month < 1 || month > 12) {
            System.out.println("Month introduced is incorrect: it isn´t between 1 and 12");
        } else {
            System.out.println("You have introduces a VALID date! " + day + "/" + month
                    + "/" + year);
        }
        myScanner.close();
    }
}
