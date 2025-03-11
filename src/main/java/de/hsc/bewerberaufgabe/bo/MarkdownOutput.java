package de.hsc.bewerberaufgabe.bo;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class MarkdownOutput implements OutputConverter {

    @Override
    public void convert(List<Library> libraries) {

        StringBuilder markdownContent = new StringBuilder();
        markdownContent.append("# Library Lizenzübersicht\n");

        for (Library library : libraries) {
            markdownContent.append("## ").append(library.getGroupId()).append(":").append(library.getArtifactId()).append(":").append(library.getVersion()).append("\n");

            if (!library.getLicenses().isEmpty()) {
                markdownContent.append("### Lizenzen\n");
                for (License license : library.getLicenses()) {
                    markdownContent.append("- **").append(license.getName()).append("** [Link](").append(license.getUrl()).append(")\n");
                }
            }

            if (!library.getDependencies().isEmpty()) {
                markdownContent.append("### Dependencies\n");
                for (Library dependency : library.getDependencies()) {
                    markdownContent.append("- ").append(dependency.getGroupId()).append(":").append(dependency.getArtifactId()).append(":").append(dependency.getVersion()).append("\n");
                }
            }
        }

        try (FileWriter writer = new FileWriter("licenses.md")) {
            writer.write(markdownContent.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
