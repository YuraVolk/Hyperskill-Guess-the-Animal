package animals;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.*;

public class GuessingGame {
    private KnowledgeDatabase database = new KnowledgeDatabase();
    private Gson gson = new Gson();
    private Scanner scanner = new Scanner(System.in);
    private Validator validator = new Validator();
    private String type = "json";
    private boolean isInitialized = false;
    private String rootAnimal = "";
    private String article = "";
    private ResourceBundle appResource = ResourceBundle.getBundle("App");


    private String getDistinct() {
        String distinct = "";
        while (true) {
            distinct = scanner.nextLine().toLowerCase();
            if (distinct.startsWith(appResource.getString("can-question"))
                    || distinct.startsWith(appResource.getString("has-question"))
                    || distinct.startsWith(appResource.getString("is-question"))) {
                System.out.printf("%s\n%s",
                        appResource.getString("yes-or-no"),
                        appResource.getString("example-statements"));
                continue;
            }
            if (distinct.contains(appResource.getString("can-verb"))
                    || distinct.contains(appResource.getString("has-verb"))
                    || distinct.contains(appResource.getString("is-verb"))) {
                break;
            }
            System.out.printf("%s\n%s",
                    appResource.getString("yes-or-no"),
                    appResource.getString("example-statements"));
        }

        return distinct;
    }

    public void startMenu() {
        System.out.println(appResource.getString("menu"));
        int choice = scanner.nextInt();
        scanner.nextLine();
        switch (choice) {
            case 1:
                if (!isInitialized) {
                    beginningKnowledgeTree();
                } else {
                    playGame();
                }
                break;
            case 3:
                System.out.println(appResource.getString("animal-input"));
                String animal = scanner.nextLine();
                findAnimal(animal);
                break;
            case 2:
                getAllAnimals();
                break;
            case 4:
                printStatistics();
                break;
            case 0:
                return;
            default:
                System.out.println(appResource.getString("error-menu"));
        }

        startMenu();
    }

    private void printStatistics() {
        System.out.println(appResource.getString("tree-stats-title"));
        int totalAnimals = 5;
        int totalNumber = 9;
        int totalStatements = 4;
        int totalDepth = 3;
        int minDepth = 2; // TODO rework
        double averageDepth = 2.4;

        System.out.printf("- %s%s\n" +
                "- %s%s\n" +
                "- %s%s\n" +
                "- %s%s\n" +
                "- %s%s\n" +
                "- %s%s\n" +
                "- %s%s\n",
                appResource.getString("tree-stats-root"),
                validator.generateStatement(database.get(validator.generateListPath(
                        new ArrayList<>(), "data")), "It", false),
                appResource.getString("tree-stats-nodes"), totalNumber,
                appResource.getString("tree-stats-animals"), totalAnimals,
                appResource.getString("tree-stats-statements"), totalStatements,
                appResource.getString("tree-stats-max-depth"), totalDepth,
                appResource.getString("tree-stats-min-depth"), minDepth,
                appResource.getString("tree-stats-avr-depth"), averageDepth);

    }

    private void getAllAnimals() {
        System.out.println(appResource.getString("known-animals"));
        List<Node> animals = database.getAnimals();
        Collections.sort(animals, Comparator.comparing(Node::getData));

        for (Node animal : animals) {
            System.out.println(animal.getData());
        }
    }

    private void findAnimal(String animal) {
        List<Node> path = new ArrayList<>();
        database.findElement(animal, path);
        if (path.size() == 0) {
            if (appResource.getString("no-facts-boolean").equals("true")) {
                System.out.printf(appResource.getString("no-facts"));
            } else {
                System.out.printf("%s %s.\n", appResource.getString("no-facts"), animal);
            }
        } else {
            for (int i = 0; i < path.size() - 1; i++) {
                System.out.print("- ");
                if (path.get(i).getYes().equals(path.get(i + 1))) { // Positive
                    System.out.println(validator
                            .generateStatement(path.get(i).getData(), appResource.getString("it-statement"), false));
                } else if (path.get(i).getNo().equals(path.get(i + 1))) { // Negative
                    System.out.println(validator
                            .generateStatement(path.get(i).getData(), appResource.getString("it-statement"), true));
                }
            }
        }
    }

    private void beginningKnowledgeTree() {
        while (true) {
            System.out.println(appResource.getString("starter-game-question"));
            scanner.nextLine();

            if (rootAnimal.startsWith(appResource.getString("indefinite-article-consonant") + " ")) {
                rootAnimal = rootAnimal.substring(appResource.getString("indefinite-article-consonant").length() + 1);
            } else if (rootAnimal.startsWith(appResource.getString("indefinite-article-vowel") + " ")) {
                rootAnimal = rootAnimal.substring(appResource.getString("indefinite-article-vowel").length() + 1);
            }

            System.out.println(article.isBlank());
            System.out.printf("%s %s %s?\n", appResource.getString("is-question"), article.trim(), rootAnimal.trim());
            boolean isTrue = validator.query();
            if (isTrue) {
                System.out.println(appResource.getString("learn-finish-starter"));
                boolean isAgain = validator.query();
                if (isAgain) {
                    continue;
                } else {
                    return;
                }
            } else {
                break;
            }
        }

        System.out.println("I give up. What animal do you have in mind?");
        String secondAnimal = scanner.nextLine().toLowerCase();
        String secondArticle = validator.validateAnimal(secondAnimal);
        if (secondAnimal.startsWith(appResource.getString("indefinite-article-consonant") + " ")) {
            secondAnimal = secondAnimal.substring(appResource.getString("indefinite-article-consonant").length() + 1);
        } else if (secondAnimal.startsWith(appResource.getString("indefinite-article-vowel") + " ")) {
            secondAnimal = secondAnimal.substring(appResource.getString("indefinite-article-vowel").length() + 1);
        }

        System.out.printf("%s %s %s %s %s.\n" + "%s",
                appResource.getString("fact-start"),
                rootAnimal, appResource.getString("fact-middle"),
                secondArticle, secondAnimal,
                appResource.getString("examples"));

        String distinct = getDistinct();
        System.out.printf("%s %s?\n",
                appResource.getString("statement-correctness"), secondAnimal);
        boolean isTrue = validator.query();
        database.set(validator.stringToObject("data"), validator.stringToObject(distinct));

        System.out.println(appResource.getString("learnt-facts"));
        System.out.printf("- %s ", appResource.getString("definite-article"));
        if (!isTrue) {
            System.out.println(validator.generateStatement(distinct, rootAnimal, false));
            System.out.printf("- %s ", appResource.getString("definite-article"));
            System.out.println(validator.generateStatement(distinct, secondAnimal, true));
            database.set(validator.stringToObject("yes"),
                    validator.generateSingleValue(article + " " + rootAnimal));
            database.set(validator.stringToObject("no"),
                    validator.generateSingleValue(secondArticle + " " + secondAnimal));
        } else {
            System.out.println(validator.generateStatement(distinct, rootAnimal, true));
            System.out.printf("- %s ", appResource.getString("definite-article"));
            System.out.println(validator.generateStatement(distinct, secondAnimal, false));
            database.set(validator.stringToObject("yes"),
                    validator.generateSingleValue(secondArticle + " " + secondAnimal));
            database.set(validator.stringToObject("no"),
                    validator.generateSingleValue(article + " " + rootAnimal));
        }

        System.out.println();
        isInitialized = true;
        database.saveDatabase(type);

        System.out.println(appResource.getString("play-again"));
        boolean playAgain = validator.query();
        if (playAgain) {
            playGame();
        } else {
            startMenu();
        }
    }

    public void loadDatabase(String type) {
        database.loadDatabase(type);
        isInitialized = true;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void beginGame() {
        System.out.println(appResource.getString("starter-question"));
        String rootAnimal = scanner.nextLine().toLowerCase();
        String article = validator.validateAnimal(rootAnimal);
        System.out.println(appResource.getString("system-intro"));

        this.rootAnimal = rootAnimal;
        this.article = article;

        startMenu();
    }

    private void learnAnimal(List<String> currentPath) {
        currentPath.remove(currentPath.size() - 1);
        List<String> leafPath = new ArrayList<>(currentPath);
        String rootAnimal = database.get(validator.generateListPath(leafPath, "data"));

        String secondAnimal = scanner.nextLine().toLowerCase();
        String secondArticle = validator.validateAnimal(secondAnimal);
        if (secondAnimal.startsWith(appResource.getString("indefinite-article-consonant") + " ")) {
            secondAnimal = secondAnimal.substring(appResource.getString("indefinite-article-consonant").length() + 1);
        } else if (secondAnimal.startsWith(appResource.getString("indefinite-article-vowel") + " ")) {
            secondAnimal = secondAnimal.substring(appResource.getString("indefinite-article-vowel").length() + 1);
        }

        System.out.printf("%s %s %s %s %s.\n" + "%s",
                appResource.getString("fact-start"),
                rootAnimal, appResource.getString("fact-middle"),
                secondArticle, secondAnimal,
                appResource.getString("examples"));
        String distinct = getDistinct();
        System.out.printf("%s %s?\n",
                appResource.getString("statement-correctness"), secondAnimal);
        boolean isTrue = validator.query();
        database.set(validator.generateListPath(leafPath, "data"),
                validator.stringToObject(distinct));

        System.out.println(appResource.getString("learnt-facts"));
        System.out.printf("- %s ", appResource.getString("definite-article"));
        if (!isTrue) {
            System.out.println(validator.generateStatement(distinct, rootAnimal, false));
            System.out.printf("- %s ", appResource.getString("definite-article"));
            System.out.println(validator.generateStatement(distinct, secondAnimal, true));
            database.set(validator.generateListPath(leafPath, "yes"),
                    validator.generateSingleValue(rootAnimal));
            database.set(validator.generateListPath(leafPath, "no"),
                    validator.generateSingleValue(secondArticle + " " + secondAnimal));
        } else {
            System.out.println(validator.generateStatement(distinct, rootAnimal, true));
            System.out.printf("- %s ", appResource.getString("definite-article"));
            System.out.println(validator.generateStatement(distinct, secondAnimal, false));
            database.set(validator.generateListPath(leafPath, "yes"),
                    validator.generateSingleValue(secondArticle + " " + secondAnimal));
            database.set(validator.generateListPath(leafPath, "no"),
                    validator.generateSingleValue(rootAnimal));
        }

        database.saveDatabase(type);
        System.out.println();
        System.out.println(appResource.getString("learn-finish-starter"));
    }

    public void playGame() {
        System.out.println(appResource.getString("starter-game-question"));
        scanner.nextLine();
        List<String> currentPath = new ArrayList<>();
        while (true) {
            System.out.println(validator.generateQuestion(database.get(
                    validator.generateListPath(currentPath, "data"))));
            boolean isTrue = validator.query();
            List<String> checkLeafPath = new ArrayList<>(currentPath);
            checkLeafPath.add("yes");
            boolean exists = database.get(validator
                    .generateListPath(checkLeafPath, "data")) != null;

            if (isTrue) {
                currentPath.add("yes");
            } else {
                currentPath.add("no");
            }

            if (exists) {
                continue;
            } else {
                if (isTrue) {
                    System.out.println(appResource.getString("victory"));
                } else {
                    System.out.println(appResource.getString("loss"));
                    learnAnimal(currentPath);
                }

                System.out.println(appResource.getString("play-again"));
                boolean playAgain = validator.query();
                if (playAgain) {
                    playGame();
                }

                break;
            }
        }
    }
}
