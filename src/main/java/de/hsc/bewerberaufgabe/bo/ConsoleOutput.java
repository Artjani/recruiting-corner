package de.hsc.bewerberaufgabe.bo;

import java.util.*;

public class ConsoleOutput implements OutputConverter {
    @Override
    public void convert(List<Library> libraries) {
        printTree(libraries);
        printSortedLibs(libraries);
        printLicensesWithLibs(libraries);
    }

    private void printTree(List<Library> libraries){

        System.out.println("--- TREE-VIEW ---");
        for (Library lib : libraries) {
            System.out.println(lib.getGroupId() + ":" + lib.getArtifactId() + ":" + lib.getVersion());

            if (!lib.getLicenses().isEmpty()) {
                for (License license : lib.getLicenses()) {
                    System.out.println(license.getName() + " (" + license.getUrl() + ")");
                }
            }

            if (!lib.getDependencies().isEmpty()) {
                for (Library dependency : lib.getDependencies()) {
                    System.out.println(dependency.getGroupId() + ":" + dependency.getArtifactId() + ":" + dependency.getVersion());
                }
            }
        }
    }

    private void printSortedLibs (List<Library> libraries){
        libraries.sort(((o1, o2) -> o1.getGroupId().trim().compareToIgnoreCase(o2.getGroupId().trim())));

        System.out.println("--- SORTED LIBRARIES ---");
        for (Library library : libraries) {
            System.out.println(library.getGroupId().trim() + ":" + library.getArtifactId() + ":" + library.getVersion());
        }
    }

    private void printLicensesWithLibs (List<Library> libraries){

        Map<String, Set<String>> licenseToLibsMap = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);

        for (Library lib : libraries) {
            for (License license : lib.getLicenses()) {
                licenseToLibsMap.computeIfAbsent(license.getName().trim(), k -> new TreeSet<>(String.CASE_INSENSITIVE_ORDER))
                        .add(lib.getGroupId().trim() + ":" + lib.getArtifactId() + ":" + lib.getVersion());
            }
        }

        System.out.println("--- LICENSES ---");
        for (Map.Entry<String, Set<String>> entry : licenseToLibsMap.entrySet()) {
            String licenseName = entry.getKey();
            Set<String> libsWithLicense = entry.getValue();

            System.out.println("Lizenz: " + licenseName);
            for (String library : libsWithLicense) {
                System.out.println("  - " + library);
            }
        }
    }
}
