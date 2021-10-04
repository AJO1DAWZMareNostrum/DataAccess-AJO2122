import java.io.FileInputStream;

public class ImageAnalizer {

    public static void main(String[] args) {
        try {
            FileInputStream fin = new FileInputStream("C:\\DataAccess\\exerciseImage.jpg");
            int i = fin.read();
            String header = Integer.toHexString(i);

            System.out.println("Header of the file starts at: " + header);

            if (header.equals("42")) {
                System.out.println("The file is type .BMP");
            } else if (header.equals("47")) {
                System.out.println("The file is type .GIF");
            } else if (header.equals("00")) {
                System.out.println("The file is type .ICO");
            } else if (header.equals("ff")) {
                System.out.println("The file is type .JPEG");
            } else if (header.equals("89")) {
                System.out.println("The file is type .PNG");
            } else {
                System.out.println("The file type has NOT been identified.");
            }

    } catch (Exception e) {
            System.out.println(e);
        }
    }
}
