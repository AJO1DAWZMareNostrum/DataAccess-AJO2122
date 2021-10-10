import java.io.FileInputStream;
import java.math.BigInteger;
import java.nio.ByteBuffer;

public class BmpAnalizer {

    public static void main(String[] args) {
        try{
            FileInputStream fin = new FileInputStream("images\\bmpsample.bmp");
            byte[] data = new byte[54];

            fin.read(data);

            int size_bytes = 256*256*256*data[2] + 256*256*data[3] + 256*data[4] + data[5];
            System.out.println("This .bmp file has a size of " + size_bytes + " in bytes");

            int width_pixels = 256*256*256*data[18] + 256*256*data[19] + 256*data[20] + data[21];
            System.out.println("This .bmp file has a width of " + width_pixels + " in pixels");

            int height_pixels = 256*256*256*data[22] + 256*256*data[23] + 256*data[24] + data[25];
            System.out.println("This .bmp file has a height of " + height_pixels + " in pixels");

        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
