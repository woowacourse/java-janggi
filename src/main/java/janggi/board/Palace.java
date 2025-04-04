package janggi.board;

import janggi.position.Position;
import java.util.Arrays;

public enum Palace {
    HAN(3, 0),
    CHO(3, 7),
    ;

    private static final int PALACE_WIDTH = 3;
    private static final int PALACE_HEIGHT = 3;

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
        int endColumn = team.startColumn + PALACE_WIDTH - 1;
        int endRow = team.startRow + PALACE_HEIGHT - 1;

        return (pickedColumn == team.startColumn && pickedRow == team.startRow) ||
                (pickedColumn == endColumn && pickedRow == team.startRow) ||
                (pickedColumn == team.startColumn && pickedRow == endRow) ||
                (pickedColumn == endColumn && pickedRow == endRow);
    }

    private static boolean isCenterInPalace(Palace team, int pickedColumn, int pickedRow) {
        int centerColumn = team.startColumn + (PALACE_WIDTH / 2);
        int centerRow = team.startRow + (PALACE_HEIGHT / 2);

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
        int endRow = team.startRow + PALACE_HEIGHT;
        int endColumn = team.startColumn + PALACE_WIDTH;
        return pickedRow >= team.startRow && pickedRow < endRow
                && pickedColumn >= team.startColumn && pickedColumn < endColumn;
    }
}
