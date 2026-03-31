package janggi.domain.movestrategy;

import janggi.domain.board.Position;
import janggi.domain.piece.Piece;
import janggi.domain.piece.PieceType;

import java.util.List;
import java.util.Map;

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

    @Override
    public PieceType getIdentity() {
        return PieceType.HORSE;
    }

    @Override
    public boolean checkPathRule(List<Piece> pathPieces) {
        return pathPieces.isEmpty();
    }
}
