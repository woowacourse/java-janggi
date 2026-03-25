package janggi;

import janggi.util.FileParser;

public class JanggiApplication {
    public static void main(String[] args) {
        FileParser.readCsvFile("src/main/resources/position.csv");
    }
}
