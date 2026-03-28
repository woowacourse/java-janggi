package domain.point;

import domain.point.exception.PointException;

import static domain.point.exception.ErrorMessage.*;

public record Command(
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

    public static Command from(String input) {
        validateBlank(input);
        String[] parts = input.split(COMMAND_DELIMITER);
        validateCommandSize(parts);

        Point start = parsePoint(parts[START_POINT]);
        Point end = parsePoint(parts[END_POINT]);

        return new Command(start, end);
    }

    private static Point parsePoint(String pointInput) {
        String[] coordinates = pointInput.split(POINT_DELIMITER);
        validatePointSize(coordinates);

        try {
            int y = Integer.parseInt(coordinates[COORDINATE_OF_Y]);
            int x = Integer.parseInt(coordinates[COORDINATE_OF_X]);
            return new Point(y, x);
        } catch (NumberFormatException e) {
            throw new PointException(POINT_IS_NOT_NUMERIC);
        }
    }

    private static void validateBlank(String input) {
        if (input.isBlank()) {
            throw new PointException(POINT_INPUT_IS_BLANK);
        }
    }

    private static void validateCommandSize(String[] parts) {
        if (parts.length != EXPECTED_COMMAND_SIZE) {
            throw new PointException(POINT_PAIR_FORMAT_IS_WRONG);
        }
    }

    private static void validatePointSize(String[] coordinates) {
        if (coordinates.length != EXPECTED_POINT_SIZE) {
            throw new PointException(POINT_FORMAT_IS_WRONG);
        }
    }
}
