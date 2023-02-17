

package de.hdm.devutils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class AsciiArt {

    public static void display_welcome_message() {
        String path = "src/main/java/de/hdm/devutils/ascii/Welcome.txt";
        try {
            String ascii_art = new String(Files.readAllBytes(Paths.get(path)));
            System.out.println(ascii_art);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    //read the ascii art from a file
    public static String readFile(String fileName) {
        String content = "";
        try {
            content = new String(Files.readAllBytes(Paths.get(fileName)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return content;
    }

}