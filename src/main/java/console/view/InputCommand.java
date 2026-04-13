package console.view;

public enum InputCommand {
    Y, N;

    public static InputCommand parse(String input) {
        try {
            return InputCommand.valueOf(input.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("잘못된 입력입니다.");
        }
    }
}
