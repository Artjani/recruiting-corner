package de.hsc.bewerberaufgabe.bo;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class JSONParser implements InputParser{

    @Override
    public List<Library> parseInput(String filePath) {

        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        try (FileReader reader = new FileReader(filePath)) {
            Library library = new Gson().fromJson(reader, Library.class);

            System.out.println(library);
            addDependencies(library);
            List<Library> libraries = new ArrayList<>();
            libraries.add(library);

            List<Library> dependencies = library.getDependencies();
            if (dependencies != null) {
                System.out.println("Dependencies gefunden: " + dependencies.size());
                for (Library dep : dependencies) {
                    System.out.println(" - " + dep);
                }
            } else {
                System.out.println("Keine Dependencies gefunden.");
            }

            return libraries;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private void addDependencies(Library lib){
        if(lib.getDependencies() != null){
            List<Library> newDependencies = new ArrayList<>();
            for (Library dependency : lib.getDependencies()){
                dependency.setParentLib(lib);
                newDependencies.add(dependency);
                addDependencies(dependency);
            }
            lib.getDependencies().addAll(newDependencies);
        }
    }

}
