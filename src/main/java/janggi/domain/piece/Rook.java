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
        Position currentPosition = this.getPosition();
        if (currentPosition.hasSameX(x)) {
            for (Piece existingPiece : existingPieces) {
                if (Math.abs(y - currentPosition.getY()) > Math.abs(currentPosition.getY() - existingPiece.getPosition().getY())) {
                    return false;
                }
            }
        }
        if (currentPosition.hasSameY(y)) {
            for (Piece existingPiece : existingPieces) {
                if (Math.abs(x - currentPosition.getX()) > Math.abs(currentPosition.getX() - existingPiece.getPosition().getX())) {
                    return false;
                }
            }
        }
        for (Piece existingPiece : existingPieces) {
            if (existingPiece.getPosition().isSameCoordinate(x, y)) {
                if (existingPiece.getSide().equals(getSide())) {
                    return false;
                }
                return true;
            }
        }
        return true;
    }
}
