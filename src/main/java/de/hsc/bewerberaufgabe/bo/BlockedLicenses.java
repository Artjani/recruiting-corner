package de.hsc.bewerberaufgabe.bo;

public enum BlockedLicenses {
    GPL("GPL");

    private final String name;

    BlockedLicenses(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
