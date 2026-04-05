package janggi.domain.palace;

import janggi.domain.board.Position;

import java.util.Set;

public class Palace {
    private final static int MIN_X = 4;
    private final static int MAX_X = 6;

    private final int minY;
    private final int maxY;
    private final Set<Position> diagonalPositions;

    public Palace(int minY, int maxY, Set<Position> positions) {
        this.minY = minY;
        this.maxY = maxY;
        this.diagonalPositions = positions;
    }

    public boolean isInRange(Position position) {
        return position.isInRange(MIN_X, MAX_X, minY, maxY);
    }

    public boolean isOnDiagonal(Position position) {
        return diagonalPositions.contains(position);
    }

    public boolean areBothOnDiagonal(Position from, Position to) {
        return isOnDiagonal(from) && isOnDiagonal(to);
    }
}
