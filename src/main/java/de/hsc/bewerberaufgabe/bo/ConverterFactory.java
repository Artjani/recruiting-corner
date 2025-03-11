package de.hsc.bewerberaufgabe.bo;

public class ConverterFactory {

    public static OutputConverter getConverter(String format){
        switch (format.toLowerCase()){
            case "console":
                return new ConsoleOutput();
            case "html":
                return new HtmlOutput();
            case "markdown":
                return new MarkdownOutput();
            default:
                throw new IllegalArgumentException("no converter found for this format: " + format);
        }
    }

}
