package domain.board;

import domain.direction.MoveAmount;
import domain.game.Side;
import java.util.List;

public record Intersection(int row, int file) {

    private static final String ERROR_WRONG_INPUT = "잘못된 입력입니다. (7,2)처럼 좌표를 구분자(쉼표)로 구분해주세요.";
    private static final int LOWER_BOUND_ROW = 1;
    private static final int UPPER_BOUND_ROW = 10;
    private static final int LOWER_BOUND_FILE = 1;
    private static final int UPPER_BOUND_FILE = 9;

    public static Intersection parse(String rowAndFile) {
        final String delimiter = ",";
        if (!rowAndFile.contains(delimiter)) {
            throw new IllegalArgumentException(ERROR_WRONG_INPUT);
        }

        String[] split = rowAndFile.split(delimiter);

        if (split.length != 2) {
            throw new IllegalArgumentException(ERROR_WRONG_INPUT);
        }

        try {
            int row = Integer.parseInt(split[0].trim());
            int file = Integer.parseInt(split[1].trim());

            return new Intersection(row, file);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("좌표에는 숫자만 입력할 수 있습니다.", e);
        }
    }

    public boolean isOutOfBounds() {
        return isOutOfRow() || isOutOfFile();
    }

    public boolean isInBounds() {
        return !isOutOfBounds();
    }

    private boolean isOutOfRow() {
        return row < LOWER_BOUND_ROW || row > UPPER_BOUND_ROW;
    }

    private boolean isOutOfFile() {
        return file < LOWER_BOUND_FILE || file > UPPER_BOUND_FILE;
    }

    public boolean isInPalace(Side side) {
        List<Integer> rowsInPalace = List.of(
                side.getRowAt(new MoveAmount(0)),
                side.getRowAt(new MoveAmount(1)),
                side.getRowAt(new MoveAmount(2))
        );

        List<Integer> filesInPalace = List.of(
                side.getFileAt(new MoveAmount(3)),
                side.getFileAt(new MoveAmount(4)),
                side.getFileAt(new MoveAmount(5))
        );

        return rowsInPalace.contains(row) && filesInPalace.contains(file);
    }

    public boolean isPalaceCenter() {
        return (row == 2 || row == 9)
                && file == 5;
    }

    public boolean isPalaceCorner() {
        return (row == 1 || row == 3 || row == 8 || row == 10)
                && (file == 4 || file == 6);
    }
}
