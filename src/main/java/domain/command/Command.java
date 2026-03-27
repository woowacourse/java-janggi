package domain.command;

public class Command {
    private final String value;

    public Command(String input) {
        this.value = input;
    }

    public String getValue() {
        return value;
    }
}
