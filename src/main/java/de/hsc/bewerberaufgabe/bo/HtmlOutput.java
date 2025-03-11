package de.hsc.bewerberaufgabe.bo;

import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class HtmlOutput implements OutputConverter {
    @Override
    public void convert(List<Library> libraries) {
        String treeHtml = printTree(libraries);
        String sortedHtml = printSortedLibraries(libraries);
        String licensesHtml = printLicensesWithLibs(libraries);
        String html = treeHtml + sortedHtml + licensesHtml;
        writeToFile(html, "licenses.html");
    }

    private String printTree(List<Library> libraries){
        StringBuilder html = new StringBuilder();

        html.append("<html>\n<head>\n<title>Licenses</title>\n</head>\n<body>\n");
        html.append("<a href=\"#lib_tree\">Libs (Tree)</a><br/>\n");
        html.append("<a href=\"#lib_list\">Libs (List)</a><br/>\n");
        html.append("<a href=\"#license_list\">Licenses (List)</a><br/>\n");
        html.append("<h1 id=\"lib_tree\">Libs of Data Gateway Server (tree)</h1>\n<ul>\n");

        for (Library lib : libraries) {
            html.append("<li>\n<h3><b>")
                    .append(lib.getGroupId()).append(":").append(lib.getArtifactId())
                    .append(" (").append(lib.getVersion()).append(")")
                    .append("</b></h3>\n<b>Parent: Data Gateway Server (direct)<br/>\n");

            if (!lib.getLicenses().isEmpty()) {
                html.append("<b>Licenses:</b>\n<ul>\n");
                for (License license : lib.getLicenses()) {
                    html.append("<li><b>").append(license.getName()).append("</b> (")
                            .append("<a href=\"").append(license.getUrl()).append("\" target=\"_BLANK\">")
                            .append(license.getUrl()).append("</a>)</li>\n");
                }
                html.append("</ul>\n");
            }

            if (!lib.getDependencies().isEmpty()) {
                html.append("<ul>\n");
                for (Library dependency : lib.getDependencies()) {
                    html.append("<li>\n<h3><b>")
                            .append(dependency.getGroupId()).append(":").append(dependency.getArtifactId())
                            .append(" (").append(dependency.getVersion()).append(")")
                            .append("</b></h3>\n<b>Parent: ").append(lib.getArtifactId()).append("<br/>\n");

                    if (!dependency.getLicenses().isEmpty()) {
                        html.append("<b>Licenses:</b>\n<ul>\n");
                        for (License depLicense : dependency.getLicenses()) {
                            html.append("<li><b>").append(depLicense.getName()).append("</b> (")
                                    .append("<a href=\"").append(depLicense.getUrl()).append("\" target=\"_BLANK\">")
                                    .append(depLicense.getUrl()).append("</a>)</li>\n");
                        }
                        html.append("</ul>\n");
                    }
                    html.append("</b>\n</li>\n");
                }
                html.append("</ul>\n");
            }
            html.append("</b>\n</li>\n");
        }
        html.append("</ul>\n</body>\n</html>");

        return html.toString();
    }

    private String printSortedLibraries(List<Library> libraries){
        StringBuilder html = new StringBuilder();

        libraries.sort(Comparator.comparing(o -> o.getGroupId().trim().toLowerCase()));

        html.append("<h1 id=\"lib_list\">Libraries (Alphabetical List)</h1>\n<ul>\n");

        for (Library library : libraries) {
            html.append("<li><b>")
                    .append(library.getGroupId().trim()).append(":")
                    .append(library.getArtifactId()).append(" (")
                    .append(library.getVersion()).append(")</b></li>\n");
        }
        html.append("</ul>\n</body>\n</html>");

        return html.toString();
    }

    private String printLicensesWithLibs(List<Library> libraries) {
        StringBuilder html = new StringBuilder();

        html.append("<h1 id=\"license_list\">Licenses (List)</h1>\n<ul>\n");

        Map<String, Set<String>> licenseToLibsMap = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);

        for (Library lib : libraries) {
            String libraryEntry = formatLibrary(lib);
            for (License license : lib.getLicenses()) {
                licenseToLibsMap.computeIfAbsent(license.getName(), k -> new HashSet<>()).add(libraryEntry);
            }
        }

        for (Map.Entry<String, Set<String>> entry : licenseToLibsMap.entrySet()) {
            String licenseName = entry.getKey();
            Set<String> libsWithLicense = entry.getValue();

            html.append("<li>\n<h3><b>").append(licenseName).append("</b></h3>\n<ul>\n");

            for (String lib : libsWithLicense) {
                html.append("<li><b>").append(lib).append("</b></li>\n");
            }
            html.append("</ul>\n</li>\n");
        }
        return html.toString();
    }

    private String formatLibrary(Library lib) {
        return lib.getGroupId() + ":" + lib.getArtifactId() + ":" + lib.getVersion();
    }

    private void writeToFile(String content, String fileName){
        try (FileWriter writer = new FileWriter(fileName)) {
            writer.write(content);
            System.out.println("HTML file generated: " + fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
