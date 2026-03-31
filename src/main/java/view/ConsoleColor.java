package view;

public enum ConsoleColor {

    RED("\033[31m"),
    GREEN("\033[32m"),
    RESET("\033[0m");

    private final String code;

    ConsoleColor(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return code;
    }
}
