package app;

import converters.jsonconverter.JsonConverter;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static converters.xmlconverter.XmlUtils.XML_OPEN_TAG;

public class Main {

    public static void main(String[] args) throws IOException {
        //Scanner scanner = new Scanner(System.in);
        String input = new String(Files.readAllBytes(Paths.get("test.txt")));
        //String input = scanner.nextLine();
        if (input.startsWith(XML_OPEN_TAG)) {
            JsonConverter jsonConverter = new JsonConverter();
            jsonConverter.convert(input);
        } else {
            System.out.println("Incorrect Input");
        }
    }
}


