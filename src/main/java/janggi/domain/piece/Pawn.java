package janggi.domain.piece;

import janggi.domain.Position;
import janggi.domain.Side;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Pawn extends Piece {

    protected Pawn(Side side, Position position) {
        super(side, position);
    }

    @Override
    protected boolean isMoveablePosition(Position destination) {
        Set<Position> moveablePositions = new HashSet<>();
        moveablePositions.add(new Position(getXPosition() - 1, getYPosition()));
        moveablePositions.add(new Position(getXPosition() + 1, getYPosition()));

        if (getSide() == Side.HAN) {
            moveablePositions.add(new Position(getXPosition(), getYPosition() + 1));
            return moveablePositions.contains(destination);
        }
        moveablePositions.add(new Position(getXPosition(), getYPosition() - 1));
        return moveablePositions.contains(destination);
    }

    @Override
    protected boolean isMoveablePath(List<Piece> existingPieces, Position destination) {
        Set<Position> moveablePositions = new HashSet<>();
        if (getSide() == Side.HAN) {
            moveablePositions.add(new Position(getXPosition() - 1, getYPosition()));
            moveablePositions.add(new Position(getXPosition() + 1, getYPosition()));
            moveablePositions.add(new Position(getXPosition(), getYPosition() + 1));

            return existingPieces.stream()
                .anyMatch(piece -> moveablePositions.contains(piece.getPosition())
                    && piece.getSide() != getSide());
        }
        moveablePositions.add(new Position(getXPosition() - 1, getYPosition()));
        moveablePositions.add(new Position(getXPosition() + 1, getYPosition()));
        moveablePositions.add(new Position(getXPosition(), getYPosition() - 1));

        return existingPieces.stream()
            .anyMatch(piece -> moveablePositions.contains(piece.getPosition())
            && piece.getSide() != getSide());
    }
}
