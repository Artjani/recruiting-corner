package de.hsc.bewerberaufgabe.bo;

import org.w3c.dom.*;
import javax.xml.parsers.*;
import java.io.*;
import java.util.*;

public class XMLParser implements InputParser{

    public List<Library> parseInput(String filePath) {
        List<Library> libraries = new ArrayList<>();
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(new File(filePath));
            document.getDocumentElement().normalize();
            NodeList dependencyNodes = document.getElementsByTagName("dependency");

            for (int i = 0; i < dependencyNodes.getLength(); i++) {
                Node dependencyNode = dependencyNodes.item(i);

                if (dependencyNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element dependencyElement = (Element) dependencyNode;

                    String groupId = getTagValue("groupId", dependencyElement);
                    String artifactId = getTagValue("artifactId", dependencyElement);
                    String version = getTagValue("version", dependencyElement);

                    Library library = new Library(groupId, artifactId, version);


                    NodeList licenseNodes = dependencyElement.getElementsByTagName("license");
                    for (int j = 0; j < licenseNodes.getLength(); j++) {
                        Node licenseNode = licenseNodes.item(j);
                        if (licenseNode.getNodeType() == Node.ELEMENT_NODE) {
                            Element licenseElement = (Element) licenseNode;
                            String licenseName = getTagValue("name", licenseElement);
                            String licenseUrl = getTagValue("url", licenseElement);
                            License license = new License(licenseName, licenseUrl);
                            library.addLicense(license);
                        }
                    }

                    NodeList subDependencies = dependencyElement.getElementsByTagName("dependency");
                    for (int k = 0; k < subDependencies.getLength(); k++) {
                        Node subDependencyNode = subDependencies.item(k);
                        if (subDependencyNode.getNodeType() == Node.ELEMENT_NODE) {
                            Element subDependencyElement = (Element) subDependencyNode;

                            String subGroupId = getTagValue("groupId", subDependencyElement);
                            String subArtifactId = getTagValue("artifactId", subDependencyElement);
                            String subVersion = getTagValue("version", subDependencyElement);

                            Library subLibrary = new Library(subGroupId, subArtifactId, subVersion);
                            library.addDependencies(subLibrary);
                        }
                    }
                    libraries.add(library);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return libraries;
    }

    private String getTagValue(String tag, Element element) {
        NodeList nodeList = element.getElementsByTagName(tag);
        if (nodeList != null && nodeList.getLength() > 0) {
            Node node = nodeList.item(0);
            return node.getTextContent();
        }
        return "";
    }
}