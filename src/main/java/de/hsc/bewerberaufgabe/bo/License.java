package de.hsc.bewerberaufgabe.bo;

public class License {

    private String name;
    private String url;

    public License(String licenseName, String licenseUrl){
        this.name = licenseName;
        this.url = licenseUrl;
    }
    public License(String licenseName){
        this("licenseName","");
    }
    public License(){
        this("","");
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

}
