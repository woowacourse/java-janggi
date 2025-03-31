package janggi.domain.piece;

import janggi.domain.position.Position;

import java.util.List;

public class Rook extends Piece {

    private static final double SCORE = 13.0;

    public Rook(Side side, int x, int y) {
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
        if (position.hasSameX(destination) || position.hasSameY(destination)) {
            return true;
        }
        if (position.isPalace() && destination.isPalace()) {
            return position.getXDistance(destination) == position.getYDistance(destination);
        }
        return false;
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
        return isPieceBetweenInLine(other.getPosition(), destination) ||
            isPieceBetweenInDiagonal(other.getPosition(), destination);
    }

    private boolean isPieceBetweenInLine(Position other, Position destination) {
        if (hasSameX(other) && hasSameX(destination)) {
            return isBetween(other.getY(), position.getY(), destination.getY());
        }
        if (hasSameY(other) && hasSameY(destination)) {
            return isBetween(other.getX(), position.getX(), destination.getX());
        }
        return false;
    }

    private boolean isPieceBetweenInDiagonal(Position other, Position destination) {
        if (position.isPalace() && destination.isPalace() &&
            position.isDiagnose(destination) && position.isDiagnose(other)) {
            return isBetween(other.getX(), position.getX(), destination.getX()) &&
                isBetween(other.getY(), position.getY(), destination.getY());
        }
        return false;
    }

    private boolean hasSameX(Position pos) {
        return this.position.getX() == pos.getX();
    }

    private boolean hasSameY(Position pos) {
        return this.position.getY() == pos.getY();
    }

    private boolean isBetween(int value, int start, int end) {
        int min = Math.min(start, end);
        int max = Math.max(start, end);
        return value > min && value < max;
    }
}
