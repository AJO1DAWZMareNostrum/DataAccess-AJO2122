import com.db4o.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Scanner;

public class MainDb4o {

    public static void main(String[] args) throws Exception {

        Scanner sc = new Scanner(System.in);
        int userOption = -1, optionMainMenu = -1;
        ObjectContainer db = null;

        do {
            System.out.println("1. Insert author, artwork, painting or sculpture.");
            System.out.println("2. List all the artworks of a given author.");
            System.out.println("3. List artwork from a specific category (Painting Type, Material Type, or Styles)");
            System.out.println("0. Exit program.");
            System.out.println("Introduce the number of an option, or 0 to exit program: ");
            optionMainMenu = Integer.parseInt(sc.nextLine());

            if (optionMainMenu == 1 || optionMainMenu == 2 || optionMainMenu == 3 || optionMainMenu == 0)
                userOption = optionMainMenu;
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
                        String title, dated, authorCodeArtwork;
                        Styles style;

                        System.out.println("Introduce a number code for the artwork: ");
                        code = Integer.parseInt(sc.nextLine());
                        System.out.println("Introduce the title of the artwork: ");
                        title = sc.nextLine();
                        System.out.println("Introduce the creation date of the artwork (day, month, year): ");
                        dated = sc.nextLine();

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
                        String titlePainting, authorCodePainting, datedPainting;
                        PaintingTypes type;

                        System.out.println("Introduce a number code for the artwork: ");
                        codePaint = Integer.parseInt(sc.nextLine());
                        System.out.println("Introduce the title of the artwork: ");
                        titlePainting = sc.nextLine();
                        System.out.println("Introduce the creation date of the artwork (day, month, year): ");
                        datedPainting = sc.nextLine();

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
                        String sculptureTitle, authorCodeSculpture, datedSculpture;
                        MaterialTypes material;

                        System.out.println("Introduce a number code for the artwork: ");
                        codeSculpt = Integer.parseInt(sc.nextLine());
                        System.out.println("Introduce the title of the artwork: ");
                        sculptureTitle = sc.nextLine();
                        System.out.println("Introduce the creation date of the artwork (day, month, year): ");
                        datedSculpture = sc.nextLine();

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
                System.out.println("Enter the code of an author to see its artworks: ");
                String authorCode = sc.nextLine();
                ObjectSet artworks = db.queryByExample( new ArtWork(0, null, null, null, authorCode));
                if (!artworks.hasNext())
                    System.out.println("Code of the author has not been found in the database.");
                while (artworks.hasNext())
                    System.out.println(artworks.next());


            } else if (userOption == 3) {

                // List artwork from a specific category (Painting Type, Material Type, or Styles)
                System.out.println("1. List all paintings by painting type.");
                System.out.println("2. List all sculptures by material type.");
                System.out.println("3. List all artworks by style.");
                System.out.println("0. Exit");
                System.out.println("Introduce an option about listing: ");

                int listOption = Integer.parseInt(sc.nextLine());
                do {
                    switch (listOption) {

                        // List paintings by painting type
                        case 1:
                            System.out.println("1. List all the paintings tha use Oil Painting\n" +
                                               "2. List all the painting that use Watercolour\n" +
                                               "3. List all the paintings that use Pastel\n" +
                                               "Select one of the above options to list filtered paintings: ");
                            int paintingStyleOption = Integer.parseInt(sc.nextLine());

                            if (paintingStyleOption == 1) {
                                // List paintings that use Oil Painting
                                ObjectSet oilPaintings = db.queryByExample(
                                                            new Painting(0, null, null, null, PaintingTypes.OILPAINTING, 0, 0));
                                if (!oilPaintings.hasNext())
                                    System.out.println("Artworks with Oil Painting style haven´t been found in the database.");
                                while (oilPaintings.hasNext())
                                    System.out.println(oilPaintings.next());
                            }
                            else if (paintingStyleOption == 2) {
                                // List paintings that use Watercolour
                                ObjectSet watercolourPaintings = db.queryByExample(
                                        new Painting(0, null, null, null, PaintingTypes.WATERCOLOUR, 0, 0));
                                if (!watercolourPaintings.hasNext())
                                    System.out.println("Artworks with Oil Painting style haven´t been found in the database.");
                                while (watercolourPaintings.hasNext())
                                    System.out.println(watercolourPaintings.next());
                            }
                            else if (paintingStyleOption == 3) {
                                // List paintings that use Pastel
                                ObjectSet pastelPaintings = db.queryByExample(
                                        new Painting(0, null, null, null, PaintingTypes.PASTEL, 0, 0));
                                if (!pastelPaintings.hasNext())
                                    System.out.println("Artworks with Oil Painting style haven´t been found in the database.");
                                while (pastelPaintings.hasNext())
                                    System.out.println(pastelPaintings.next());
                            }
                            else
                                System.out.println("Option/number introduced is NOT a valid one.");
                            break;

                        case 2:
                            // List sculptures by material type
                            System.out.println("1. List all the sculptures tha use Iron\n" +
                                    "2. List all the sculptures tha use Bronze\n" +
                                    "3. List all the sculptures tha use Marble\n" +
                                    "Select one of the above options to list filtered sculptures: ");
                            int materialOption = Integer.parseInt(sc.nextLine());

                            if (materialOption == 1) {
                                // List all sculptures that use Iron material
                                ObjectSet ironSculptures = db.queryByExample(
                                        new Sculpture(0, null, null, null, MaterialTypes.IRON, 0));
                                if (!ironSculptures.hasNext())
                                    System.out.println("Sculptures with Iron material haven´t been found in the database.");
                                while (ironSculptures.hasNext())
                                    System.out.println(ironSculptures.next());
                            }
                            else if (materialOption == 2) {
                                // List all sculptures that use Bronze material
                                ObjectSet bronzeSculptures = db.queryByExample(
                                        new Sculpture(0, null, null, null, MaterialTypes.BRONZE, 0));
                                if (!bronzeSculptures.hasNext())
                                    System.out.println("Sculptures with Iron material haven´t been found in the database.");
                                while (bronzeSculptures.hasNext())
                                    System.out.println(bronzeSculptures.next());
                            }
                            else if (materialOption == 3) {
                                // List all sculptures that use Marble material
                                ObjectSet marbleSculptures = db.queryByExample(
                                        new Sculpture(0, null, null, null, MaterialTypes.MARBLE, 0));
                                if (!marbleSculptures.hasNext())
                                    System.out.println("Sculptures with Iron material haven´t been found in the database.");
                                while (marbleSculptures.hasNext())
                                    System.out.println(marbleSculptures.next());
                            }
                            else
                                System.out.println("Option/number introduced is NOT a valid one.");
                            break;

                        case 3:
                            // List artworks by style
                            System.out.println("1. List all the artworks with Grecoroman style\n" +
                                    "2. List all the artworks with Neoclassic style\n" +
                                    "3. List all the artworks with Cubism style\n" +
                                    "Select one of the above options to list filtered artworks: ");
                            int styleOption = Integer.parseInt(sc.nextLine());

                            if (styleOption == 1) {
                                // List all the artworks with Grecoroman style
                                ObjectSet grecoromanArt = db.queryByExample(
                                        new ArtWork(0, null, null, Styles.GRECOROMAN, null));
                                if (!grecoromanArt.hasNext())
                                    System.out.println("Artworks of Grecoroman style haven´t been found in the database.");
                                while (grecoromanArt.hasNext())
                                    System.out.println(grecoromanArt.next());
                            }
                            else if (styleOption == 2) {
                                // List all the artworks with Neoclassic style
                                ObjectSet neoclassicArt = db.queryByExample(
                                        new ArtWork(0, null, null, Styles.NEOCLASSIC, null));
                                if (!neoclassicArt.hasNext())
                                    System.out.println("Artworks of Grecoroman style haven´t been found in the database.");
                                while (neoclassicArt.hasNext())
                                    System.out.println(neoclassicArt.next());
                            }
                            else if (styleOption == 3) {
                                // List all the artworks with Cubist style
                                ObjectSet cubistArt = db.queryByExample(
                                        new ArtWork(0, null, null, Styles.CUBISM, null));
                                if (!cubistArt.hasNext())
                                    System.out.println("Artworks of Grecoroman style haven´t been found in the database.");
                                while (cubistArt.hasNext())
                                    System.out.println(cubistArt.next());
                            }
                            else
                                System.out.println("Option/number introduced is NOT a valid one.");
                            break;

                        default:
                            System.out.println("Option/number introduced is NOT a valid one.");
                    }

                } while (listOption != 0);

            } else
                System.out.println("Option is NOT a valid one. Try again:");

        } while(userOption != 0);

        System.out.println("Program has been terminated. Goodbye!");
        sc.close();
    }

}
