package janggi.domain.piece;

import janggi.domain.position.Position;

public class Palace {

    public boolean isPalaceMove(Position from, Position to) {
        return (isInHanPalace(from) && isInHanPalace(to))
                || (isInChoPalace(from) && isInChoPalace(to));
    }

    private boolean isInHanPalace(Position position) {
        int row = position.getRowValue();
        int col = position.getColumnValue();
        return row >= 1 && row <= 3 && col >= 4 && col <= 6;
    }

    private boolean isInChoPalace(Position position) {
        int row = position.getRowValue();
        int col = position.getColumnValue();
        return row >= 8 && row <= 10 && col >= 4 && col <= 6;
    }
}
