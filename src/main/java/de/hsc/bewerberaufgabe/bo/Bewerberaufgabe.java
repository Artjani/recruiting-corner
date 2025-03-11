/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package de.hsc.bewerberaufgabe.bo;

import java.util.List;

/**
 *
 * @author Bewerbung
 */
public class Bewerberaufgabe {

    public static void main(String[] args) {

        if (args.length < 2){
            System.out.println("plugin and outputFormat needed");
            return;
        }
        String plugin = args[0].toLowerCase();
        String outputFormat = args[1].toLowerCase();

        String path = "examples/input/example." + args[0];

        try {
            InputParser inputParser = ParserFactory.getParser(plugin);
            List<Library> libraries = inputParser.parseInput(path);

            OutputConverter outputConverter = ConverterFactory.getConverter(outputFormat);
            outputConverter.convert(libraries);

        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
