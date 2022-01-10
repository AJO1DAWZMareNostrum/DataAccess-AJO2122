import com.db4o.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Scanner;

public class MainDb4o {

    public static void main(String[] args) throws Exception {

        Scanner sc = new Scanner(System.in);
        int userOption = -1;
        ObjectContainer db = null;

        do {
            System.out.println("1. Insert author, artwork, painting or sculpture.");
            System.out.println("2. List all the artworks of a given author.");
            System.out.println("3. List artwork from a specific category (Painting Type, Material Type, or Styles)");
            System.out.println("0. Exit program.");
            System.out.println("Introduce the number of an option, or 0 to exit program: ");

            if (userOption == 1 || userOption == 2 || userOption == 3 || userOption == 0)
                userOption = sc.nextInt();
            else
                System.out.println("Option introduced is not valid. Try again.");

            // Inserting elements to the database
            if (userOption == 1) {
                int option = 0, insertOption = 0;
                System.out.println("\n\n1. Insert an Author.");
                System.out.println("2. Insert an Artwork.");
                System.out.println("3. Insert a Painting");
                System.out.println("4. Insert a Sculpture.");
                System.out.println("Introduce an option: ");
                option = Integer.parseInt(sc.nextLine());

                if (option == 1 || option == 2 || option == 3 || option == 4)
                    insertOption = option;
                else
                    System.out.println("Option introduced is not a valid one.");

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

                            if (db.queryByExample(author).hasNext() == false) {
                                // if it doesn’t exist previously, we add it
                                db.store(author);
                                db.commit();
                            }
                        } finally {
                            if (db != null)
                                db.close();
                        }
                        break;

                    case 2:
                        // Asks for the properties of a given new ArtWork
                        int code, styleOption;
                        String title, authorCodeArtwork;
                        Styles style;
                        LocalDate dated;

                        System.out.println("Introduce a number code for the artwork: ");
                        code = Integer.parseInt(sc.nextLine());
                        System.out.println("Introduce the title of the artwork: ");
                        title = sc.nextLine();
                        System.out.println("Introduce the creation date of the artwork (year, month, day): ");
                        String stringDate = sc.nextLine();
                        dated = LocalDate.parse(stringDate, DateTimeFormatter.BASIC_ISO_DATE);

                        System.out.println("Introduce the style of the artwork (1=GRECOROMAN, 2=NEOCLASSIC, 3=CUBISM): ");
                        styleOption = sc.nextInt();
                        switch (styleOption){
                            case 1:
                                style = Styles.GRECOROMAN;
                                break;
                            case 2:
                                style = Styles.NEOCLASSIC;
                                break;
                            case 3:
                                style = Styles.CUBISM;
                                break;
                            default:
                                style = null;
                        }

                        System.out.println("Introduce the code of the author (format MUST be 3 initials plus" +
                                "4 digits of year of birth):");
                        authorCodeArtwork = sc.nextLine();

                        try {
                            db = Db4o.openFile("artworks.dat");
                            ArtWork artwork = new ArtWork(code, title, dated, style, authorCodeArtwork);

                            if (db.queryByExample(artwork).hasNext() == false) {
                                // if it doesn’t exist previously, we add it
                                db.store(artwork);
                                db.commit();
                            }
                        } finally {
                            if (db != null)
                                db.close();
                        }
                        break;

                    case 3:
                        // Asks for the properties of a given new Painting
                        int codePaint, typeOption;
                        float width, height;
                        String titlePainting, authorCodePainting;
                        PaintingTypes type;
                        LocalDate datedPainting;

                        System.out.println("Introduce a number code for the artwork: ");
                        codePaint = Integer.parseInt(sc.nextLine());
                        System.out.println("Introduce the title of the artwork: ");
                        titlePainting = sc.nextLine();
                        System.out.println("Introduce the creation date of the artwork (year, month, day): ");
                        String paintingDate = sc.nextLine();
                        datedPainting = LocalDate.parse(paintingDate, DateTimeFormatter.BASIC_ISO_DATE);

                        System.out.println("Introduce the code of the author (format MUST be 3 initials plus" +
                                "4 digits of year of birth):");
                        authorCodePainting = sc.nextLine();

                        // Properties specific to Painting class
                        System.out.println("Introduce the type of the painting (1=OILPAINTING, 2=WATERCOLOUR, 3=PASTEL): ");
                        typeOption = Integer.parseInt(sc.nextLine());
                        switch (typeOption) {
                            case 1:
                                type = PaintingTypes.OILPAINTING;
                                break;
                            case 2:
                                type = PaintingTypes.WATERCOLOUR;
                                break;
                            case 3:
                                type = PaintingTypes.PASTEL;
                                break;
                            default:
                                type = null;
                        }
                        System.out.println("Introduce the painting´s width: ");
                        width = Float.parseFloat(sc.nextLine());
                        System.out.println("Introduce the painting´s height: ");
                        height = Float.parseFloat(sc.nextLine());

                        try {
                            db = Db4o.openFile("painting.dat");
                            Painting painting = new Painting(codePaint, titlePainting, datedPainting, authorCodePainting,
                                                                type, width, height);
                            if (db.queryByExample(painting).hasNext() == false) {
                                // if it doesn’t exist previously, we add it
                                db.store(painting);
                                db.commit();
                            }
                        } finally {
                            if (db != null)
                                db.close();
                        }
                        break;

                    case 4:
                        // Asks for the properties of a given new Sculpture
                        int codeSculpt, styleOptionSculpture, materialOption;
                        float weight;
                        String sculptureTitle, authorCodeSculpture;
                        MaterialTypes material;
                        LocalDate datedSculpture;

                        System.out.println("Introduce a number code for the artwork: ");
                        codeSculpt = Integer.parseInt(sc.nextLine());
                        System.out.println("Introduce the title of the artwork: ");
                        sculptureTitle = sc.nextLine();
                        System.out.println("Introduce the creation date of the artwork (year, month, day): ");
                        String sculptureDate = sc.nextLine();
                        datedSculpture = LocalDate.parse(sculptureDate, DateTimeFormatter.BASIC_ISO_DATE);

                        System.out.println("Introduce the code of the author (format MUST be 3 initials plus" +
                                "4 digits of year of birth):");
                        authorCodeSculpture = sc.nextLine();

                        // Properties specific to Sculpture class
                        System.out.println("Introduce the material of the sculpture (1=IRON, 2=BRONZE, 3=MARBLE): ");
                        materialOption = Integer.parseInt(sc.nextLine());
                        switch (materialOption) {
                            case 1:
                                material = MaterialTypes.IRON;
                                break;
                            case 2:
                                material = MaterialTypes.BRONZE;
                                break;
                            case 3:
                                material = MaterialTypes.MARBLE;
                                break;
                            default:
                                material = null;
                        }
                        System.out.println("Introduce the sculpture´s weigth: ");
                        weight = Float.parseFloat(sc.nextLine());

                        try {
                            db = Db4o.openFile("sculpture.dat");
                            Sculpture sculpture = new Sculpture(codeSculpt, sculptureTitle, datedSculpture,
                                                                authorCodeSculpture, material, weight);
                            if (db.queryByExample(sculpture).hasNext() == false) {
                                // if it doesn’t exist previously, we add it
                                db.store(sculpture);
                                db.commit();
                            }
                        } finally {
                            if (db != null)
                                db.close();
                        }
                        break;

                    default:
                        System.out.println("Option introduced is not a valid one.");
                }
            } else if (userOption == 2) {

                // List all the artworks of a given author
                ObjectSet artworks = db.queryByExample( new Author())


            } else if (userOption == 3) {

                // List artwork from a specific category (Painting Type, Material Type, or Styles)

            } else
                System.out.println("Option is NOT a valid one. Try again:");

        } while(userOption != 0);

        sc.close();
    }

}
