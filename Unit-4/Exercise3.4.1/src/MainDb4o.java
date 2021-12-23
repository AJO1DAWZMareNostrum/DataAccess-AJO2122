import com.db4o.*;
import java.util.Scanner;

public class MainDb4o {

    public static void main(String[] args) throws  Exception{

        Scanner sc = new Scanner(System.in);
        int userOption = -1;
        ObjectContainer db = null;

        do {
            System.out.println("1. Insert author, artwork, painting or sculpture.");
            System.out.println("2. List all the artworks of a given author.");
            System.out.println("3. List artwork from a specific category (Painting Type, Material Type, or Styles)");
            System.out.println("0. Exit program.");

            if (userOption == 1 || userOption == 2 || userOption == 3 || userOption == 0)
                userOption = sc.nextInt();
            else
                System.out.println("Option introduced is not valid. Try again.");

            if (userOption == 1) {
                int insertOption = 0;
                System.out.println("\n\n1. Insert an Author.");
                System.out.println("2. Insert an Artwork.");
                System.out.println("3. Insert a Painting");
                System.out.println("4. Insert a Sculpture.");

                if (userOption == 1 || userOption == 2 || userOption == 3 || userOption == 0)
                    insertOption = sc.nextInt();

                switch (insertOption) {
                    case 1:
                        // Asks for the properties of a given new Author
                        String authorCode, authorName, nationality;
                        System.out.println("Introduce the code of the author (format MUST be 3 initials plus" +
                                "4 digits of year of birth):");
                        authorCode = sc.nextLine();
                        System.out.println("Introduce the name of the author: ");
                        authorName = sc.nextLine();
                        System.out.println("Introduce the author´s nationality: ");
                        nationality = sc.nextLine();

                        try {
                            db = Db4o.openFile("authors.dat");
                            Author author = new Author(authorCode, authorName, nationality);

                            // I have used the 'store method' instead of set, because set method wasn´t being recognized as valid
                            db.store(author);
                            db.commit();
                        } finally {
                            if (db != null)
                                db.close();
                        }
                        break;

                    case 2:
                        // Asks for the properties of a given new ArtWork
                        
                }
            }

        } while(userOption != 0);
    }
}
