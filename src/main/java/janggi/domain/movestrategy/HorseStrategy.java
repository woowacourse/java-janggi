package janggi.domain.movestrategy;

import janggi.domain.board.Position;
import janggi.domain.piece.Piece;

import java.util.List;

public class HorseStrategy implements MoveStrategy{

    @Override
    public boolean canMove(Position from, Position to) {
        return from.isMatchDistance(to, 1, 2);
    }

    @Override
    public List<Position> findPath(Position from, Position to) {
        Position mid = from.moveStraight(to);
        return List.of(mid);
    }

    @Override
    public boolean checkPathRule(List<Piece> pathPieces) {
        return pathPieces.isEmpty();
    }
}
