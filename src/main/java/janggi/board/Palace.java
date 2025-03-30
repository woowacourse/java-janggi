package janggi.board;

import janggi.position.Position;
import java.util.Arrays;

public enum Palace {
    HAN(3, 0),
    CHO(3, 7),
    ;

    public static final int WIDTH = 3;
    public static final int HEIGHT = 3;

    private final int startColumn;
    private final int startRow;

    Palace(int startColumn, int startRow) {
        this.startColumn = startColumn;
        this.startRow = startRow;
    }

    public static boolean canDiagonalInPalace(Position picked) {
        int pickedColumn = picked.getColumn();
        int pickedRow = picked.getRow();

        if (!isInPalace(picked)) {
            return false;
        }
        return Arrays.stream(values())
                .anyMatch(team -> isVertex(team, pickedColumn, pickedRow) ||
                        isCenterInPalace(team, pickedColumn, pickedRow));
    }

    private static boolean isVertex(Palace team, int pickedColumn, int pickedRow) {
        int endColumn = team.startColumn + WIDTH - 1;
        int endRow = team.startRow + HEIGHT - 1;

        return (pickedColumn == team.startColumn && pickedRow == team.startRow) ||
                (pickedColumn == endColumn && pickedRow == team.startRow) ||
                (pickedColumn == team.startColumn && pickedRow == endRow) ||
                (pickedColumn == endColumn && pickedRow == endRow);
    }

    private static boolean isCenterInPalace(Palace team, int pickedColumn, int pickedRow) {
        int centerColumn = team.startColumn + (WIDTH / 2);
        int centerRow = team.startRow + (HEIGHT / 2);

        return pickedColumn == centerColumn && pickedRow == centerRow;
    }

    public static boolean isInPalace(Position position) {
        for (Palace team : values()) {
            if (isInsidePalace(team, position.getColumn(), position.getRow())) {
                return true;
            }
        }
        return false;
    }

    private static boolean isInsidePalace(Palace team, int pickedColumn, int pickedRow) {
        int endRow = team.startRow + HEIGHT;
        int endColumn = team.startColumn + WIDTH;
        return pickedRow >= team.startRow && pickedRow < endRow
                && pickedColumn >= team.startColumn && pickedColumn < endColumn;
    }
}
