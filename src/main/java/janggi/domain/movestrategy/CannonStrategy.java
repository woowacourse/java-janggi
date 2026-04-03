package janggi.domain.movestrategy;

import janggi.domain.board.Position;
import janggi.domain.piece.Piece;
import janggi.domain.piece.PieceType;

import java.util.ArrayList;
import java.util.List;

public class CannonStrategy implements MoveStrategy {

    @Override
    public boolean canMove(Position from, Position to) {
        return from.isInSameLine(to);
    }

    @Override
    public List<Position> findPath(Position from, Position to) {
        List<Position> path = new ArrayList<>();
        Position nextPosition = from.moveStraight(to);
        while (!nextPosition.equals(to)) {
            path.add(nextPosition);
            nextPosition = nextPosition.moveStraight(to);
        }
        return path;
    }

    @Override
    public boolean checkPathRule(List<Piece> pathPieces) {
        return pathPieces.size() == 1 &&
                !pathPieces.getFirst().isSameType(PieceType.CANNON);
    }

    @Override
    public boolean canCapture(Piece from, Piece to) {
        if (to == null) {
            return true;
        }
        if (from.isSameTeam(to)) {
            return false;
        }
        return !to.isSameType(PieceType.CANNON);
    }
}
