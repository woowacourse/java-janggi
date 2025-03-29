package janggi.domain.piece;

import janggi.domain.position.Position;

import java.util.List;

public class King extends Piece {

    public King(Side side, int x, int y) {
        super(side, x, y);
    }

    @Override
    public boolean isKing() {
        return true;
    }

    @Override
    public boolean isCannon() {
        return false;
    }

    @Override
    public double getScore() {
        return 0;
    }

    @Override
    protected boolean isMoveablePosition(Position destination) {
        if (!destination.isPalace()) return false;
        int xDistance = position.getXDistance(destination);
        int yDistance = position.getYDistance(destination);
        return xDistance <= 1 && yDistance <= 1;
    }

    @Override
    protected boolean isMoveablePath(List<Piece> existingPieces, Position destination) {
        return existingPieces.stream()
            .filter(piece -> piece.isSamePosition(destination))
            .noneMatch(piece -> piece.getSide() == side);
    }
}
