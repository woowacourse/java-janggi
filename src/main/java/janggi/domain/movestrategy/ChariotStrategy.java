package janggi.domain.movestrategy;

import janggi.domain.board.Position;
import janggi.domain.piece.Piece;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ChariotStrategy implements MoveStrategy{

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
        path.add(to);
        return path;
    }

    @Override
    public boolean determineMovingRule(Piece sourcePiece, Map<Position, Piece> positionPieces, Position to) {
        if (positionPieces.size() >= 2) {
            return false;
        }
        for (Position position : positionPieces.keySet()) {
            if (position.equals(to)) {
                return !sourcePiece.isSameTeam(positionPieces.get(position));
            }
            return false;
        }
        return true;
    }
}
