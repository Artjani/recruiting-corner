package de.hsc.bewerberaufgabe.bo;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DATParser implements InputParser{


    @Override
    public List<Library> parseInput(String filePath) {
        List<Library> libraries = new ArrayList<>();
        Library parentLib = null;
        Library currentLib = null;
        License license = null;
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))){

            String line;

            while ((line = reader.readLine()) != null){

                if (line.isEmpty()) continue;

                int spaces = countSpaces(line);
                //line = line.trim();

                if (!line.contains("::")){
                    String[] libraryParts = line.split(":");
                    switch (libraryParts.length) {
                        case 1:
                            currentLib = new Library(libraryParts[0]);
                            break;
                        case 2:
                            currentLib = new Library(libraryParts[0], libraryParts[1]);
                            break;
                        case 3:
                        default:
                            currentLib = new Library(libraryParts[0], libraryParts[1], libraryParts[2]);
                            break;
                    }

                    if (spaces == 0) {
                        parentLib = currentLib;
                    } else {
                        currentLib.setParentLib(parentLib);
                        parentLib.addDependencies(currentLib);
                    }
                    libraries.add(currentLib);
                }

                if (line.contains("::")){
                    String[] licenseParts = line.split("::");
                    if(licenseParts.length<2){
                        license = new License(licenseParts[0]);
                    } else {
                        license = new License(licenseParts[0], licenseParts[1]);
                    }

                    LicenseValidator.checkLicense(currentLib);
                    currentLib.addLicense(license);
                }
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return libraries;
    }

    private int countSpaces(String line) {
        int count = 0;
        while (count < line.length() && line.charAt(count) == ' ') {
            count++;
        }
        return count;
    }
}
