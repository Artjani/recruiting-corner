package de.hsc.bewerberaufgabe.bo;

import java.util.*;
import java.util.stream.Collectors;

public class ConsoleOutput implements OutputConverter {
    @Override
    public void convert(List<Library> libraries) {
        printTree(libraries);
        printSortedLibs(libraries);
        printLicensesWithLibs2(libraries);
    }

    private void printTree(List<Library> libraries){

        System.out.println("--- TREE-VIEW ---");
        for (Library lib : libraries) {
            if (lib == null) continue;
            printLibrary(lib, 0);
        }
    }
    private void printLibrary(Library library, int shifting) {

        String spaceForLibs = "   ".repeat(shifting);
        String spaceForLicenses = "   ".repeat(shifting + 1);

        System.out.println(spaceForLibs + library.getGroupId() + ":" + library.getArtifactId() + ":" + library.getVersion());

        for (License license : library.getLicenses()) {
            System.out.println(spaceForLicenses + license.getName() + " (" + license.getUrl() + ")");
        }
        for (Library dependency : library.getDependencies()) {
            printLibrary(dependency, shifting + 2);
        }
    }

    private void printSortedLibs(List<Library> libraries) {

        System.out.println("--- SORTED LIBS ---");
        List<Library> allLibraries = new ArrayList<>();

        for (Library lib : libraries) {
            collectLibraries(lib, allLibraries);
        }
        allLibraries.sort(Comparator.comparing(Library::getGroupId)
                .thenComparing(Library::getArtifactId)
                .thenComparing(Library::getVersion));

        for (Library lib : allLibraries) {
            System.out.println(lib.getGroupId() + ":" + lib.getArtifactId() + ":" + lib.getVersion());
            for (License license : lib.getLicenses()) {
                System.out.println("   " + license.getName() + " (" + license.getUrl() + ")");
            }
        }
    }
    private void collectLibraries(Library library, List<Library> allLibraries) {
        if (library == null || allLibraries.contains(library)) {
            return;
        }
        allLibraries.add(library);

        if (library.getDependencies().size()>0) {
            for (Library dependentLib : library.getDependencies()) {
                collectLibraries(dependentLib, allLibraries);
            }
        }

    }

    private void printLicensesWithLibs2(List<Library> libraries) {

        System.out.println("--- LICENSES ---");

        List<License> allLicenses = new ArrayList<>();
        List<Library> allLibraries = new ArrayList<>();


        for (Library lib : libraries) {
            collectLibraries(lib, allLibraries);
        }
        System.out.println(allLibraries);
        collectLicenses(allLibraries, allLicenses);
        allLicenses.sort(Comparator.comparing(License::getName, String.CASE_INSENSITIVE_ORDER));

        for (License license : allLicenses) {
            System.out.println("Lizenz: " + license.getName() + " (" + license.getUrl() + ")");

            for (Library lib : allLibraries) {
                for(int i = 0; i< lib.getLicenses().size(); i++) {
                    if (lib.getLicenses().get(i).getName().contains(license.getName())) {
                        System.out.println("   - " + lib.getGroupId() + ":" + lib.getArtifactId() + ":" + lib.getVersion());
                    }
                }
            }
        }
    }
    private void collectLicenses(List<Library> libraries, List<License> allLicenses) {

        for (Library library : libraries) {
            if (library == null) continue;
            for (License license : library.getLicenses()) {
                boolean exists = false;
                for (License existingLicense : allLicenses) {
                    if (existingLicense.getName().equalsIgnoreCase(license.getName())) {
                        exists = true;
                        break;
                    }
                }
                if (!exists) {
                    allLicenses.add(license);
                }
            }
        }
        System.out.println(allLicenses);
    }
}
