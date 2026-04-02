package janggi.domain.movestrategy;

import janggi.domain.board.Position;
import janggi.domain.piece.Piece;
import janggi.domain.piece.PieceType;

import java.util.List;

public class GuardStrategy implements MoveStrategy {

    @Override
    public boolean canMove(Position from, Position to) {
        return from.calculateManhattanDistance(to) == 1;
    }

    @Override
    public List<Position> findPath(Position from, Position to) {
        return List.of();
    }

    @Override
    public PieceType getIdentity() {
        return PieceType.GUARD;
    }

    @Override
    public boolean checkPathRule(List<Piece> pathPieces) {
        return pathPieces.isEmpty();
    }
}
