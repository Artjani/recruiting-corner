package de.hsc.bewerberaufgabe.bo;

import java.util.ArrayList;
import java.util.List;

public class Library {
    private String groupId;
    private String artifactId;
    private String version;
    private Library parentLib;
    List<License> licenses;
    private List<Library> dependencies;


    public Library(String groupId, String artifactId, String version, Library parentLib) {
        this.groupId = groupId;
        this.artifactId = artifactId;
        this.version = version;
        this.parentLib = parentLib;
        this.licenses = new ArrayList<>();
        this.dependencies = new ArrayList<>();

    }
    public Library(String groupId, String artifactId, String version){
        this(groupId, artifactId, version, null);
    }
    public Library(String groupId, String artifactId){
        this(groupId, artifactId, "");
    }
    public Library(String groupId){
        this(groupId, "");
    }
    public Library(){
        this("");
    }

    public String getGroupId() {
        return groupId;
    }

    public String getArtifactId() {
        return artifactId;
    }

    public String getVersion() {
        return version;
    }

    public Library getParentLib() {
        return parentLib;
    }

    public List<License> getLicenses() {
        return licenses;
    }

    public List<Library> getDependencies() {
        return dependencies;
    }
    public void setParentLib(Library libName){
        this.parentLib = libName;
    }
    public void addLicense(License license) {
        this.licenses.add(license);
    }
    public void addDependencies(Library lib) {
        if (this.dependencies == null) {
            this.dependencies = new ArrayList<>();
        }
        this.dependencies.add(lib);
    }
}
