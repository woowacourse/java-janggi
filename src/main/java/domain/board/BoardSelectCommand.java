package domain.board;

public record BoardSelectCommand(
        long select
) {

    private static final int SELECT_COMMAND_THRESHOLD = 0;

    public static BoardSelectCommand from(String input) {
        validateStringIsNumeric(input);
        validateCommandRange(Long.parseLong(input));
        return new BoardSelectCommand(Long.parseLong(input));
    }

    public static void validateStringIsNumeric(String input) {
        try {
            Integer.parseInt(input);
        } catch (NumberFormatException e) {
            // TODO 커스텀 예외로 변경할 것.
            throw new IllegalArgumentException(e);
        }
    }

    // TODO 커스텀 예외로 변경할 것.
    public static void validateCommandRange(long select) {
        if (select < SELECT_COMMAND_THRESHOLD) {
            throw new IllegalArgumentException();
        }
    }

    public boolean isNewGameCommand() {
        return select == SELECT_COMMAND_THRESHOLD;
    }

}
