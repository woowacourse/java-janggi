package janggi.domain.movestrategy;

import janggi.domain.board.Position;
import janggi.domain.piece.Piece;
import janggi.domain.piece.PieceType;

import java.util.List;
import java.util.Map;

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
        return PieceType.ELEPHANT;
    }

    @Override
    public boolean checkPathRule(List<Piece> pathPieces) {
        return pathPieces.isEmpty();
    }
}
