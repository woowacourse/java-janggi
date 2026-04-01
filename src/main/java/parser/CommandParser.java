package parser;

public class CommandParser {

    private CommandParser(){}

    public static boolean parse(String input) {
        return input.equals("save");
    }
}
