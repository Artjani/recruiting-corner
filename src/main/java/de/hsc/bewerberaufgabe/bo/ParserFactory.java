package de.hsc.bewerberaufgabe.bo;

public class ParserFactory {

    public static InputParser getParser(String format) {
        switch (format.toLowerCase()) {
            case "json":
                return new JSONParser();
            case "xml":
                return new XMLParser();
            case "dat":
                return new DATParser();
            default:
                throw new IllegalArgumentException("no parser found for this format: " + format);
        }
    }
}
