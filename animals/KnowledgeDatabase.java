package animals;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;
import com.google.gson.*;
import org.yaml.snakeyaml.Yaml;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

public class KnowledgeDatabase {
    private Gson gson;
    private JsonObject database;
    private final String path = "animals";

    KnowledgeDatabase() {
        gson = new Gson();
        database = new JsonObject();
    }

    void findElement(String value, List<Node> path) {
        String jsonData = database.toString();
        JsonParser parser = new JsonParser();
        JsonElement element = parser.parse(jsonData);
        Node tree = gson.fromJson(element, Node.class);
        searchForElement(tree, value, path);
    }

    int getDepth() {
        String jsonData = database.toString();
        JsonParser parser = new JsonParser();
        JsonElement element = parser.parse(jsonData);
        Node tree = gson.fromJson(element, Node.class);
        return tree.getMaxDepth(tree);
    }

    double getSumDepth() {
        String jsonData = database.toString();
        JsonParser parser = new JsonParser();
        JsonElement element = parser.parse(jsonData);
        Node tree = gson.fromJson(element, Node.class);

        List<Integer> depths = new ArrayList<>();
        tree.getLevel(tree, depths);
        return depths.stream().mapToInt(val -> val).average().orElse(0.0);
    }

    List<Node> getAnimals() {
        String jsonData = database.toString();
        JsonParser parser = new JsonParser();
        JsonElement element = parser.parse(jsonData);
        Node tree = gson.fromJson(element, Node.class);
        return tree.getAllLeafs();
    }

    private boolean searchForElement(Node node, String value, List<Node> path) {
        if (node == null) {
            return false;
        }

        if (node.getData().equals(value)) {
            path.add(0, node);
            return true;
        }

        if (searchForElement(node.getYes(), value, path)
        || searchForElement(node.getNo(), value, path)) {
            path.add(0, node);
            return true;
        }

        return false;
    }

    void loadDatabase(String type) {
        try {
            String content = Files.readAllLines(Paths.get(path + ResourceBundle
                    .getBundle("App").getString("prefix") + "." + type), StandardCharsets.UTF_8)
                    .stream().collect(Collectors.joining("\n"));
            if (type.equals("json")) {
                JsonParser parser = new JsonParser();
                database = parser.parse(content).getAsJsonObject();
            } else if (type.equals("xml")) {
                XmlMapper xmlMapper = new XmlMapper();
                JsonNode node = xmlMapper.readTree(content.getBytes());
                ObjectMapper jsonMapper = new ObjectMapper();
                String json = jsonMapper.writeValueAsString(node);

                JsonParser parser = new JsonParser();
                database = parser.parse(json).getAsJsonObject();
            } else if (type.equals("yaml")) {
                ObjectMapper yamlReader = new ObjectMapper(new YAMLFactory());
                Object obj = yamlReader.readValue(content, Object.class);
                ObjectMapper jsonWriter = new ObjectMapper();
                String json = jsonWriter.writeValueAsString(obj);

                JsonParser parser = new JsonParser();
                database = parser.parse(json).getAsJsonObject();
                System.out.println(database);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void saveFile(String type, String content, String filePath) {
        try {
            FileWriter writer = new FileWriter(filePath + type);
            writer.write(content);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            System.out.println(e);
            e.printStackTrace();
        }
    }

    void saveDatabase(String type) {
        String json = gson.toJson(database);
        saveFile(type, json, "animals" + ResourceBundle
                .getBundle("App").getString("prefix") + ".");

        try {
            if (type.equals("xml")) {
                ObjectMapper mapper = new ObjectMapper();
                ObjectMapper xmlMapper = new XmlMapper();
                JsonNode tree = mapper.readTree(json);
                String xml = xmlMapper.writer().withRootName("root").writeValueAsString(tree);
                saveFile("xml", xml, "animals" + ResourceBundle
                        .getBundle("App").getString("prefix") + ".");
            } else if (type.equals("yaml")) {
                JsonNode jsonNodeTree = new ObjectMapper().readTree(json);
                String yaml = new YAMLMapper().writeValueAsString(jsonNodeTree);
                saveFile("yaml", yaml, "animals" + ResourceBundle
                        .getBundle("App").getString("prefix") + ".");
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

    }

    void set(JsonElement pos, JsonElement value) {
        JsonObject data = database;
        if (pos.isJsonArray()) {

            JsonArray path = pos.getAsJsonArray();


            for (int i = 0; i < path.size(); i++) {
                if (i == path.size() - 1) {
                    data.add(path.get(i).getAsString(), value);
                    break;
                } else if (data.has(path.get(i).getAsString())) {
                    if (!data.get(path.get(i).getAsString()).isJsonObject()) {
                        data.add(path.get(i).getAsString(), new JsonObject());
                    }
                } else {
                    data.add(path.get(i).getAsString(), new JsonObject());
                }
                data = data.getAsJsonObject(path.get(i).getAsString());
            }
        } else {
            database.add(pos.getAsString(), value);
        }
    }

    String get(JsonElement pos) {
        JsonObject data = new Gson().fromJson(database, JsonObject.class);
        if (pos.isJsonArray()) {
            JsonArray path = pos.getAsJsonArray();
            if (path.size() == 1) {
                if (!data.has(pos.getAsString())) {
                    return null;
                } else {
                    return data.get(path.get(0).getAsString()).getAsString();
                }
            } else {
                for (int i = 0; i < path.size(); i++) {
                    if (data.has(path.get(i).getAsString())) {
                        if (!data.get(path.get(i).getAsString()).isJsonObject()) {
                            if (i == path.size() - 1) {
                                return data.get(path.get(i).getAsString()).getAsString();
                            } else {
                                return null;
                            }
                        } else {
                            data = data.get(path.get(i).getAsString()).getAsJsonObject();
                        }
                    } else {
                        return null;
                    }
                }
            }
        } else {
            if (!data.has(pos.getAsString())) {
                return null;
            } else {
                return gson.toJson(data.get(pos.getAsString()));
            }
        }

        return null;
    }

    void delete(JsonElement pos) {
        JsonObject data = database;
        if (pos.isJsonArray()) {
            JsonArray path = pos.getAsJsonArray();
            for (int i = 0; i < path.size(); i++) {
                if (!data.has(path.get(i).getAsString())) {
                    throw new IllegalArgumentException("Wrong path to object");
                } else if (i == path.size() - 1) {
                    if (data.has(path.get(i).getAsString())) {
                        data.remove(path.get(i).getAsString());
                        break;
                    }
                } else if (!data.get(path.get(i).getAsString()).isJsonObject()) {
                    throw new IllegalArgumentException("Wrong path to object");
                }
                data = data.getAsJsonObject(path.get(i).getAsString());
            }
        } else {
            if (!data.has(pos.getAsString())) {
                throw new IllegalArgumentException("Wrong path to value");
            } else {
                database.remove(pos.getAsString());
            }
        }
    }

    void loadTestDB() {
        String test = "{\n" +
                "  \"data\" : \"It is a mammal\",\n" +
                "  \"yes\" : {\n" +
                "    \"data\" : \"It is living in the forest\",\n" +
                "    \"yes\" : {\n" +
                "      \"data\" : \"It has a long bushy tail\",\n" +
                "      \"yes\" : {\n" +
                "        \"data\" : \"a fox\"\n" +
                "      },\n" +
                "      \"no\" : {\n" +
                "        \"data\" : \"It is a shy animal\",\n" +
                "        \"yes\" : {\n" +
                "          \"data\" : \"a hare\"\n" +
                "        },\n" +
                "        \"no\" : {\n" +
                "          \"data\" : \"a wolf\"\n" +
                "        }\n" +
                "      }\n" +
                "    },\n" +
                "    \"no\" : {\n" +
                "      \"data\" : \"It can climb trees\",\n" +
                "      \"yes\" : {\n" +
                "        \"data\" : \"a cat\"\n" +
                "      },\n" +
                "      \"no\" : {\n" +
                "        \"data\" : \"a dog\"\n" +
                "      }\n" +
                "    }\n" +
                "  },\n" +
                "  \"no\" : {\n" +
                "    \"data\" : \"a shark\"\n" +
                "  }\n" +
                "}";

        JsonParser parser = new JsonParser();
        database = parser.parse(test).getAsJsonObject();
    }
}
