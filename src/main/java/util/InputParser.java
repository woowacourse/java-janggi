package util;

import domain.board.BoardPosition;
import java.util.Arrays;
import java.util.List;
import java.util.regex.PatternSyntaxException;

public class InputParser {

    public static BoardPosition parseBoardPosition(final String inputPosition) {
        final List<Integer> positions = splitToIntegersByComma(inputPosition);
        validatePositionSize(positions);
        final int x = positions.getFirst();
        final int y = positions.getLast();
        return new BoardPosition(x, y);
    }

    private static List<Integer> splitToIntegersByComma(final String inputPosition) {
        try {
            return Arrays.stream(inputPosition.split(",", -1))
                    .map(String::trim)
                    .map(Integer::parseInt)
                    .toList();
        } catch (PatternSyntaxException | NumberFormatException e) {
            throw new IllegalArgumentException("입력 형식이 잘못되었습니다.");
        }
    }

    private static void validatePositionSize(final List<Integer> positions) {
        if (positions.size() != 2) {
            throw new IllegalArgumentException("입력 형식이 잘못되었습니다.");
        }
    }
}
