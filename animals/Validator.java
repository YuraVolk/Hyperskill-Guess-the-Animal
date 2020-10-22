package animals;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Scanner;

public class Validator {
    private JsonParser parser = new JsonParser();
    private ResourceBundle bundle = ResourceBundle.getBundle("App");

    String validateAnimal(String animal) {
        if (animal.startsWith(bundle.getString("indefinite-article-consonant") + " ")) {
            return bundle.getString("indefinite-article-consonant");
        } else if (animal.startsWith(bundle.getString("indefinite-article-vowel") + " ")) {
            return bundle.getString("indefinite-article-vowel");
        } else {
            if ("eaiou".indexOf(animal.charAt(0)) >= 0) {
                return bundle.getString("indefinite-article-vowel");
            } else {
                return bundle.getString("indefinite-article-consonant");
            }
        }
    }

    boolean query() {
        String[] positiveAnswers = bundle.getStringArray("positive-answers");
        String[] negativeAnswers = bundle.getStringArray("negative-answers");
        while (true) {
            String answer = new Scanner(System.in).nextLine().toLowerCase().trim();
            if (answer.endsWith("!") | answer.endsWith(".")) {
                answer = answer.substring(0, answer.length() - 1);
            }

            if (Arrays.stream(positiveAnswers).anyMatch(answer::equals)) {
                // Positive
                return true;
            } else if (Arrays.stream(negativeAnswers).anyMatch(answer::equals)) {
                // Negative
                return false;
            } else {
                System.out.println(bundle.getString("yes-or-no"));
            }
        }
    }

    JsonElement stringToObject(String source) {
        String jsonObject = String.format("{\"key\":\"%s\"}", source);
        JsonObject object = (JsonObject) parser.parse(jsonObject);
        return object.get("key");
    }

    JsonElement generateSingleValue(String data) {
        String jsonObject = String.format("{\"key\":{\"data\":\"%s\"}}", data);
        JsonObject object = (JsonObject) parser.parse(jsonObject);
        return object.get("key");
    }

    String generateStatement(String distinct, String animal, boolean isNegation) {
        String modifiedAnimal = animal;
        if (modifiedAnimal.startsWith(bundle.getString("indefinite-article-consonant") + " ")) {
            modifiedAnimal = modifiedAnimal.substring(bundle.getString("indefinite-article-consonant").length() + 1);
        } else if (modifiedAnimal.startsWith(bundle.getString("indefinite-article-vowel") + " ")) {
            modifiedAnimal = modifiedAnimal.substring(bundle.getString("indefinite-article-vowel").length() + 1);
        }

        String verb = "";
        String negationVerb = "";
        if (distinct.contains(bundle.getString("can-verb"))) {
            verb = bundle.getString("can-verb");
            negationVerb = bundle.getString("can-verb-negation");
        } else if (distinct.contains(bundle.getString("has-verb"))) {
            verb = bundle.getString("has-verb");
            negationVerb = bundle.getString("has-verb-negation");
        } else if (distinct.contains(bundle.getString("is-verb"))) {
            verb = bundle.getString("is-verb");
            negationVerb = bundle.getString("is-verb-negation");
        }

        String[] animalFact = distinct.split(verb);
        if (isNegation) {
            return String.format("%s %s %s.", modifiedAnimal,
                    negationVerb, animalFact[1].trim());
        } else {
            return String.format("%s %s %s.", modifiedAnimal,
                    verb, animalFact[1].trim());
        }
    }

    private String generateQuestionOnStatement(String distinct) {
        String question = "";
        String verb = "";
        if (distinct.contains(bundle.getString("can-verb"))) {
            question = bundle.getString("can-question");
            verb = bundle.getString("can-verb");
        } else if (distinct.contains(bundle.getString("has-verb"))) {
            question = bundle.getString("has-question");
            verb = bundle.getString("has-verb");
        } else if (distinct.contains(bundle.getString("is-verb"))) {
            question = bundle.getString("is-question");
            verb = bundle.getString("is-verb");
        }

        String animalFact = distinct.split(verb)[1];
        return String.format("%s%s?", question, animalFact);
    }

    String generateQuestion(String distinct) {
        if (!distinct.startsWith(bundle.getString("it-statement"))) {
            return String.format("%s %s?", bundle.getString("is-question"), distinct);
        } else {
            return generateQuestionOnStatement(distinct);
        }
    }

    JsonElement generateListPath(List<String> path, String endPoint) {
        StringBuilder builder = new StringBuilder();
        for (String route : path) {
            builder.append("\"");
            builder.append(route);
            builder.append("\",");
        }
        builder.append(endPoint);

        String jsonObject = String.format("{\"key\":[%s]}", builder.toString());
        JsonObject object = (JsonObject) parser.parse(jsonObject);
        return object.get("key");
    }
}
