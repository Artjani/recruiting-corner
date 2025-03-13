package de.hsc.bewerberaufgabe.bo;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class JSONParser implements InputParser{

    @Override
    public List<Library> parseInput(String filePath) {
        Gson gson = new Gson();

        try (FileReader reader = new FileReader(filePath)) {
            JsonObject jsonObject = JsonParser.parseReader(reader).getAsJsonObject();
            List<Library> libraries = new ArrayList<>();
            libraries.add(printAsLibrary(jsonObject));
            return libraries;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Library printAsLibrary(JsonObject jsonObject) {
        String groupId = jsonObject.has("groupId") ? jsonObject.get("groupId").getAsString() : "";
        String artifactId = jsonObject.has("artifactId") ? jsonObject.get("artifactId").getAsString() : "";
        String version = jsonObject.has("version") ? jsonObject.get("version").getAsString() : "";

        Library library = new Library(groupId, artifactId, version);

        if (jsonObject.has("licenses")) {
            JsonArray licensesArray = jsonObject.getAsJsonArray("licenses");
            for (JsonElement licenseElement : licensesArray) {
                JsonObject licenseObject = licenseElement.getAsJsonObject();
                String name = licenseObject.has("name") ? licenseObject.get("name").getAsString() : "";
                String url = licenseObject.has("url") ? licenseObject.get("url").getAsString() : "";
                library.addLicense(new License(name, url));
            }
        }

        if (jsonObject.has("dependencies")) {
            JsonArray dependenciesArray = jsonObject.getAsJsonArray("dependencies");
            for (JsonElement dependencyElement : dependenciesArray) {
                JsonObject dependencyObject = dependencyElement.getAsJsonObject();
                Library dependentLib = printAsLibrary(dependencyObject);
                library.addDependencies(dependentLib);
            }
        }
        return library;
    }
}
