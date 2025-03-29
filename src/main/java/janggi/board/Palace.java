package janggi.board;

import janggi.position.Direction;
import janggi.position.Position;
import janggi.position.Route;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
        for (Palace team : values()) {
            if (isVertex(team, pickedColumn, pickedRow)) {
                return true;
            }
        }
        return isCenterInPalace(pickedColumn, pickedRow);
    }

    private static boolean isVertex(Palace team, int pickedColumn, int pickedRow) {
        if (team.startColumn == pickedColumn && team.startRow == pickedRow) {
            return true;
        }
        if (team.startColumn + WIDTH - 1 == pickedColumn && team.startRow + HEIGHT - 1 == pickedRow) {
            return true;
        }
        if (team.startColumn == pickedColumn && team.startRow + HEIGHT - 1 == pickedRow) {
            return true;
        }
        if (team.startColumn + WIDTH - 1 == pickedColumn && team.startRow == pickedRow) {
            return true;
        }
        return false;
    }

    private static boolean isCenterInPalace(int pickedColumn, int pickedRow) {
        return Arrays.stream(values())
                .anyMatch(team -> team.startColumn + 1 == pickedColumn && team.startRow + 1 == pickedRow);
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
