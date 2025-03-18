package janggi.domain.piece;

import janggi.domain.Position;
import janggi.domain.Side;
import java.util.List;

public class Rook extends Piece {

    public Rook(Side side, Position position) {
        super(side, position);
    }

    @Override
    protected boolean isMoveablePosition(int x, int y) {
        if (getPosition().hasSameX(x)) {
            return !getPosition().hasSameY(y);
        }
        return getPosition().hasSameY(y);
    }

    @Override
    protected boolean isMoveablePath(List<Piece> existingPieces, int x, int y) {
        Position positionToMove = new Position(x, y);

        for (Piece existingPiece : existingPieces) {
            if (hasPositionBetween(existingPiece, positionToMove)) {
                return false;
            }
            if (existingPiece.isSamePosition(x, y)) {
                return !existingPiece.getSide().equals(getSide());
            }
        }
        return true;
    }

    private boolean hasPositionBetween(Piece other, Position position) {
        if (this.hasSameX(other)) {
            return getYDistance(position) > getYDistance(other.getPosition());
        }
        if (this.hasSameY(other)) {
            return getXDistance(position) > getXDistance(other.getPosition());
        }
        return false;
    }

    private boolean hasSameX(Piece piece) {
        return getXPosition() == piece.getXPosition();
    }

    private boolean hasSameY(Piece piece) {
        return getYPosition() == piece.getYPosition();
    }

    private int getXDistance(Position destination) {
        return Math.abs(destination.getX() - getXPosition());
    }

    private int getYDistance(Position destination) {
        return Math.abs(destination.getY() - getYPosition());
    }
}
