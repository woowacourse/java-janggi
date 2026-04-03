package janggi.domain.movestrategy;

import janggi.domain.board.BoardDirection;
import janggi.domain.board.Position;
import janggi.domain.piece.Piece;

import java.util.List;

public class SoliderStrategy implements MoveStrategy {
    private final BoardDirection direction;

    public SoliderStrategy(BoardDirection direction) {
        this.direction = direction;
    }

    @Override
    public boolean canMove(Position from, Position to) {
        int xDistance = from.calculateX(to);
        int yDistance = from.calculateY(to);
        return (xDistance == 0 && direction.isForward(yDistance))
                || (Math.abs(xDistance) == 1 && yDistance == 0);
    }

    @Override
    public List<Position> findPath(Position from, Position to) {
        return List.of();
    }

    @Override
    public boolean checkPathRule(List<Piece> pathPieces) {
        return pathPieces.isEmpty();
    }
}
