package animals;

import java.io.File;
import java.util.*;

public class Main {
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        var appResource = ResourceBundle.getBundle("App");

        Validator validator = new Validator();
        GuessingGame guessingGame = new GuessingGame();
        String extension = "json";
        if (args.length != 0) {
            extension = args[1];
        }

        File dbFile = new File("animals" + appResource.getString("prefix") + "." + extension);
        if (args.length != 0) {
            guessingGame.setType(args[1]);
        }

        if (dbFile.exists() && !dbFile.isDirectory()) {
            if (args.length != 0) {
                guessingGame.loadDatabase(args[1]);
            } else {
                guessingGame.loadDatabase("json");
            }
            System.out.printf("%s\n\n",
                            appResource.getString("system-intro"));
            guessingGame.startMenu();
        } else {
            guessingGame.beginGame();
        }

        System.out.println(appResource.getString("bye"));
    }
}


