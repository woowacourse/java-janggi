package janggi.domain.piece;

import janggi.domain.position.Position;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Pawn extends Piece {

    private static final double SCORE = 2.0;

    public Pawn(Side side, int x, int y) {
        super(side, x, y);
    }

    @Override
    public boolean isKing() {
        return false;
    }

    @Override
    public boolean isCannon() {
        return false;
    }

    @Override
    public double getScore() {
        return SCORE;
    }

    @Override
    protected boolean isMoveablePosition(Position destination) {
        return getMovablePositions().contains(destination);
    }

    @Override
    protected boolean isMoveablePath(List<Piece> existingPieces, Position destination) {
        Set<Position> moveablePositions = getMovablePositions();

        List<Piece> pieces = existingPieces.stream()
                .filter(existingPiece -> existingPiece.isSamePosition(destination))
                .filter(existingPiece -> moveablePositions.contains(existingPiece.getPosition()))
                .toList();

        if (pieces.isEmpty()) {
            return true;
        }
        return pieces.getFirst().side != this.side;
    }

    private Set<Position> getMovablePositions() {
        Set<Position> moveablePositions = new HashSet<>();
        int x = getXPosition();
        int y = getYPosition();
        int moveY = getMoveDirection();

        addPosition(moveablePositions, x - 1, y);
        addPosition(moveablePositions, x + 1, y);
        addPosition(moveablePositions, x, y + moveY);
        computePalacePawnPosition(moveablePositions, x, y, moveY);
        return moveablePositions;
    }

    private void computePalacePawnPosition(Set<Position> moveablePositions, int x, int y, int moveY) {
        if (position.isPalace()) {
            addPosition(moveablePositions, x - 1, y + moveY);
            addPosition(moveablePositions, x + 1, y + moveY);
        }
    }

    private int getMoveDirection() {
        if (side == Side.HAN) return 1;
        return -1;
    }

    private void addPosition(Set<Position> positions, int x, int y) {
        if (x >= 0 && x <= 8 && y >= 0 && y <= 9) {
            positions.add(new Position(x, y));
        }
    }
}
