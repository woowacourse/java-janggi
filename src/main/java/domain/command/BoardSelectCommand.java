package domain.command;

import domain.command.exception.CommandException;

import static domain.command.exception.CommandError.*;

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
            throw new CommandException(BOARD_SELECT_IS_NOT_NUMERIC.getMessage());
        }
    }

    public static void validateCommandRange(long select) {
        if (select < EXIT_COMMAND) {
            throw new CommandException(INVALID_BOARD_SELECT.getMessage());
        }
    }

    public boolean isNewGameCommand() {
        return select == NEW_GAME_COMMAND;
    }

    public boolean isExitCommand() {
        return select == EXIT_COMMAND;
    }

}
