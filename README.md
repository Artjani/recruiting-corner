# HSC | Recruiting Corner - Übung

In diesem Repository wurde eine Übung für dich vorbereitet, die du innerhalb eines bestimmten Zeitraums bewältigen sollst.

Alle notwendigen Informationen zur Übung findest du in `docs/Bewerberaufgabe_Junior_GitHub.pdf` und `docs/Technische_Spezifikation_DAT.pdf`. Beispiele findest du im Ordner `examples`.

Schnapp dir ein erfrischendes Getränk :coffee::tropical_drink: und viel Spaß beim Coden! :hotsprings::rocket:


# Aufgabe

Erstellung eines Java-Consolen-Programms zur leichten Convertierung von gegebenen Dateiformaten (hier .dat, .json, .xml) in verschiedene Ausgabeformate (hier .html, .md oder in cmd)

## Anforderungen

-Java 17, keine externen Bibliotheken außer Gson, keine Frameworks, Maven als Verwaltungstool

## Nutzung

Die Nutzung soll über die Kommandozeile stattfinden mit entsprechenden Parametern.
Als ersten Parameter das Format der vorhandenen Lizenz-Datei eingeben.
Als zweiten Parameter das gewünschte Ausgabeformat eingeben.
Momentan verfügbare Parameter:

Eingabe: "dat", "json", "xml"

Ausgabe: "console", "markdown", "html"

Bei der Ausgabeauswahl "console" wird die Ausgabe direkt ausgeführt. Bei "markdown" und "html" wird eine entsprechende Datei im Quellverzeichnis erstellt.

## Idee und Klassenaufbau

Gewählt wurde das Strategie-Pattern, welches eine Erweiterung von neuen Formaten erleichtert.
Die Interfaces Inputparser und OutputConverter übergeben den erbenden Formatierungsklassen ihre Methode.
Die Klassen ParserFactory und ConverterFactory kümmern sich um die entsprechenden Klassen, welche durch die mitgelieferten Konsolen-Parameter gewählt werden.

Eingabe-Formatierungsklassen: DATParser, JSONParser, XMLParser

Ausgabe-Formatierungsklassen: ConsoleOutput, MarkdownOutput, HtmlOutput

Zusätzlich wurde die Klasse LizenzValidator hinzugefügt, welche Lizenzen auf Echtheit abgleicht. Als Referrenz für unerlaubte Lizensen wurde ein Enum BlockedLicenses erstellt, welches diese verwaltet. 
