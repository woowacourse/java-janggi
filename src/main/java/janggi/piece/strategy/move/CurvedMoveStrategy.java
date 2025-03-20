package janggi.piece.strategy.move;

import janggi.Board;
import janggi.CurvedMovement;
import janggi.Position;

public class CurvedMoveStrategy implements MoveStrategy {

    private final CurvedMovement movement;

    public CurvedMoveStrategy(final CurvedMovement movement) {
        this.movement = movement;
    }

    @Override
    public void validate(final Board board, final Position departure, final Position destination) {
        int differenceOfRow = Math.abs(destination.subtractRow(departure));
        int differenceOfColumn = Math.abs(destination.subtractColumn(departure));

        int straightDistance = Math.min(differenceOfRow, differenceOfColumn);
        int diagonalDistance = Math.max(differenceOfRow, differenceOfColumn);

        if (!movement.matches(straightDistance, diagonalDistance)) {
            throw new IllegalArgumentException(exceptionMessage);
        }
    }
}
