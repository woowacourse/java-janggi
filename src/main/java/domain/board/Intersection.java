package domain.board;

import domain.direction.MoveAmount;
import domain.game.Side;
import java.util.List;

public record Intersection(int row, int file) {

    private static final int LOWER_BOUND_ROW = 1;
    private static final int UPPER_BOUND_ROW = 10;
    private static final int LOWER_BOUND_FILE = 1;
    private static final int UPPER_BOUND_FILE = 9;

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
}
