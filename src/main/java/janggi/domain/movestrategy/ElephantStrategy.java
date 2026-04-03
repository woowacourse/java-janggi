package janggi.domain.movestrategy;

import janggi.domain.board.Position;
import janggi.domain.piece.Piece;

import java.util.List;

public class ElephantStrategy implements MoveStrategy {

    @Override
    public boolean canMove(Position from, Position to) {
        return from.isMatchDistance(to, 2, 3);
    }

    @Override
    public List<Position> findPath(Position from, Position to) {
        Position first = from.moveStraight(to);
        Position second = first.moveDiagonal(to);
        return List.of(first, second);
    }

    @Override
    public boolean checkPathRule(List<Piece> pathPieces) {
        return pathPieces.isEmpty();
    }
}
