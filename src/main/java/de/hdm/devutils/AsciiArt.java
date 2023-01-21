

package de.hdm.devutils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class AsciiArt {

    public static void display_welcome_message(){
        System.out.println(" __          __  _                            _          _____  ____   _____ _____  ______ _____  \n" +
                " \\ \\        / / | |                          | |        |  __ \\|  _ \\ / ____|  __ \\|  ____|  __ \\ \n" +
                "  \\ \\  /\\  / /__| | ___ ___  _ __ ___   ___  | |_ ___   | |  | | |_) | |  __| |__) | |__  | |__) |\n" +
                "   \\ \\/  \\/ / _ \\ |/ __/ _ \\| '_ ` _ \\ / _ \\ | __/ _ \\  | |  | |  _ <| | |_ |  _  /|  __| |  ___/ \n" +
                "    \\  /\\  /  __/ | (_| (_) | | | | | |  __/ | || (_) | | |__| | |_) | |__| | | \\ \\| |____| |     \n" +
                "     \\/  \\/ \\___|_|\\___\\___/|_| |_| |_|\\___|  \\__\\___/  |_____/|____/ \\_____|_|  \\_\\______|_|     \n" +
                "                                                                                                  \n" +
                "                                                                                                  ");
    }

    //read the ascii art from a file
    public static String readFile(String fileName) {
        String content = "";
        try {
            content = new String (Files.readAllBytes(Paths.get(fileName)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return content;
    }

}