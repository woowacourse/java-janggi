package parser;

import dto.InputMoveDto;
import dto.InputPointDto;
import exception.ErrorMessage;

public class MoveInputParser {
    private MoveInputParser() {
    }

    public static InputMoveDto parse(String from, String to) {
        return new InputMoveDto(parsePoint(from), parsePoint(to));
    }

    private static InputPointDto parsePoint(String input) {
        String[] tokens = input.trim().split("\\s+");
        if (tokens.length != 2) {
            throw new exception.InvalidMoveCoordinateFormatException(ErrorMessage.MOVE_COORDINATE_YX_FORMAT);
        }
        try {
            int y = Integer.parseInt(tokens[0]);
            int x = Integer.parseInt(tokens[1]);
            return new InputPointDto(y, x);
        } catch (RuntimeException e) {
            throw new exception.InvalidMoveCoordinateFormatException(ErrorMessage.INVALID_MOVE_FORMAT);
        }
    }
}
