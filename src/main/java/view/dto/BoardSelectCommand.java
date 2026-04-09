package view.dto;

public record BoardSelectCommand(
        long select
) {

    private static final int NEW_GAME_COMMAND = 0;
    private static final int EXIT_COMMAND = -1;

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
        if (select < EXIT_COMMAND) {
            throw new IllegalArgumentException();
        }
    }

    public boolean isNewGameCommand() {
        return select == NEW_GAME_COMMAND;
    }

    public boolean isExitCommand() {
        return select == EXIT_COMMAND;
    }

}
