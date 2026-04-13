package janggi.domain;

public class ResumeCommand {

    private final String command;

    public ResumeCommand(String input) {
        validateEmptyInput(input);
        validateInputCommand(input);
        this.command = input;
    }

    public boolean isResume() {
        return command.equals("y");
    }

    private void validateEmptyInput(String input) {
        if (input.isBlank()) {
            throw new IllegalArgumentException("입력값이 공백입니다.");
        }
    }

    private void validateInputCommand(String input) {
        if (!input.equals("y") && !input.equals("n")) {
            throw new IllegalArgumentException("입력값은 y 또는 n만 입력할 수 있습니다.");
        }
    }
}
