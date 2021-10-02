import java.util.Scanner;

public class Main {
    public static void main(String[] args){
        Scanner myScanner = new Scanner(System.in);
        int day, month, year;

        System.out.print("Enter a day: ");
        day = myScanner.nextInt();
        System.out.print("Enter a month: ");
        month = myScanner.nextInt();
        System.out.print("Enter a year: ");
        year = myScanner.nextInt();

        Date date1 = new Date(day, month, year);

        System.out.println("Your introduced date is: " + date1.getDay() + "/" + date1.getMonth()
                + "/" + date1.getYear());
    }
}
