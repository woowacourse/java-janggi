package janggi.domain.piece;

import janggi.domain.Position;
import janggi.domain.Side;

import java.util.List;

public class Elephant extends Piece {

    private static final int VERTICAL_BASE_X_MOVEABLE_DISTANCE = 2;
    private static final int VERTICAL_BASE_Y_MOVEABLE_DISTANCE = 3;

    private static final int HORIZONTAL_BASE_X_MOVEABLE_DISTANCE = 3;
    private static final int HORIZONTAL_BASE_Y_MOVEABLE_DISTANCE = 2;

    public Elephant(Side side, Position position) {
        super(side, position);
    }

    @Override
    protected boolean isMoveablePosition(int x, int y) {
        Position position = getPosition();

        if (Math.abs(position.getX() - x) == VERTICAL_BASE_X_MOVEABLE_DISTANCE) {
            return Math.abs(position.getY() - y) == VERTICAL_BASE_Y_MOVEABLE_DISTANCE;
        }
        if (Math.abs(position.getX() - x) == HORIZONTAL_BASE_X_MOVEABLE_DISTANCE) {
            return Math.abs(position.getY() - y) == HORIZONTAL_BASE_Y_MOVEABLE_DISTANCE;
        }
        return false;
    }

    @Override
    protected boolean isMoveablePath(List<Piece> existingPieces, int x, int y) {
        return false;
    }
}
