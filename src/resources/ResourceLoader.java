package resources;

import java.awt.Image;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Scanner;

import javax.imageio.ImageIO;

public class ResourceLoader {
    public static Image getImage(String path) {
        URL url = ResourceLoader.class.getResource(path);
        Image img = null;
        try {
            img = ImageIO.read(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return img;
    }

    public static File getFile(String path) {
        URL url = ResourceLoader.class.getResource(path);
        System.out.println(url);
        File file = null;
        try {
            file = new File(url.toURI());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return file;
    }

    public static String getText(String path) {
        File file = getFile(path);
        Scanner scanner = null;
        try {
            scanner = new Scanner(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
        StringBuilder text = new StringBuilder();
        while (scanner.hasNext()) {
            text.append(scanner.nextLine()).append('\n');
        }
        return text.toString();
    }
}
