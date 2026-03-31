package parser;

public class AnswerParser {

    private static final String ONE = "1";
    private static final String TWO = "2";

    private AnswerParser() {
    }

    public static boolean parse(String input) {
        if (ONE.equals(input)) {
            return true;
        }
        if (TWO.equals(input)) {
            return false;
        }

        throw new IllegalArgumentException("[ERROR] 잘못된 입력입니다.");
    }
}