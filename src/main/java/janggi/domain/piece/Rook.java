package janggi.domain.piece;

import janggi.domain.Position;
import janggi.domain.Side;
import java.util.List;

public class Rook extends Piece {

    public Rook(Side side, Position position) {
        super(side, position);
    }

    @Override
    protected boolean isMoveablePosition(Position destination) {
        if (getPosition().hasSameX(destination)) {
            return !getPosition().hasSameY(destination);
        }
        return getPosition().hasSameY(destination);
    }

    @Override
    protected boolean isMoveablePath(List<Piece> existingPieces, Position destination) {
        for (Piece existingPiece : existingPieces) {
            if (hasPieceBetween(existingPiece, destination)) {
                return false;
            }
            if (existingPiece.isSamePosition(destination)) {
                return !existingPiece.getSide().equals(getSide());
            }
        }
        return true;
    }

    private boolean hasPieceBetween(Piece other, Position destination) {
        if (this.hasSameX(other)) {
            return getPosition().getYDistance(destination) > getPosition().getYDistance(other.getPosition());
        }
        if (this.hasSameY(other)) {
            return getPosition().getXDistance(destination) > getPosition().getXDistance(other.getPosition());
        }
        return false;
    }

    private boolean hasSameX(Piece piece) {
        return getXPosition() == piece.getXPosition();
    }

    private boolean hasSameY(Piece piece) {
        return getYPosition() == piece.getYPosition();
    }
}
