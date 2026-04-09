package domain.command;

import domain.command.exception.CommandException;
import domain.point.Point;

import static domain.command.exception.CommandError.*;

public record MoveCommand(
        Point start,
        Point end
) {
    private static final String COMMAND_DELIMITER = " ";
    private static final String POINT_DELIMITER = ",";
    private static final int EXPECTED_COMMAND_SIZE = 2;
    private static final int EXPECTED_POINT_SIZE = 2;
    private static final int START_POINT = 0;
    private static final int END_POINT = 1;
    private static final int COORDINATE_OF_Y = 0;
    private static final int COORDINATE_OF_X = 1;

    public static MoveCommand from(String input) {
        validateBlank(input);
        String[] parts = input.split(COMMAND_DELIMITER);
        validateCommandSize(parts);

        Point start = parsePoint(parts[START_POINT]);
        Point end = parsePoint(parts[END_POINT]);

        return new MoveCommand(start, end);
    }

    private static Point parsePoint(String pointInput) {
        String[] coordinates = pointInput.split(POINT_DELIMITER);
        validatePointSize(coordinates);

        try {
            int y = Integer.parseInt(coordinates[COORDINATE_OF_Y]);
            int x = Integer.parseInt(coordinates[COORDINATE_OF_X]);
            return new Point(y, x);
        } catch (NumberFormatException e) {
            throw new CommandException(MOVE_COMMAND_IS_NOT_NUMERIC.getMessage());
        }
    }

    private static void validateBlank(String input) {
        if (input.isBlank()) {
            throw new CommandException(MOVE_COMMAND_INPUT_IS_BLANK.getMessage());
        }
    }

    private static void validateCommandSize(String[] parts) {
        if (parts.length != EXPECTED_COMMAND_SIZE) {
            throw new CommandException(MOVE_COMMAND_FORMAT_IS_WRONG.getMessage());
        }
    }

    private static void validatePointSize(String[] coordinates) {
        if (coordinates.length != EXPECTED_POINT_SIZE) {
            throw new CommandException(MOVE_COMMAND_FORMAT_IS_WRONG.getMessage());
        }
    }
}
