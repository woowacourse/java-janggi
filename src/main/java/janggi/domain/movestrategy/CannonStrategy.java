package janggi.domain.movestrategy;

import janggi.domain.board.Position;
import janggi.domain.piece.Piece;
import janggi.domain.piece.PieceType;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
        path.add(to);
        return path;
    }

    @Override
    public boolean determineMovingRule(Piece sourcePiece, Map<Position, Piece> positionPieces, Position to) {
        if (positionPieces.size() >= 3) {
            return false;
        }
        long count = positionPieces.values().stream()
                .filter(piece -> sourcePiece.getPieceName().equals(piece.getPieceName()))
                .count();
        if (count != 0) {
            return false;
        }

        if (positionPieces.size() == 2) {
            if (positionPieces.containsKey(to)) {
                Piece piece = positionPieces.get(to);
                return !piece.isSameTeam(sourcePiece);
            }
            return false;
        }

        return !positionPieces.containsKey(to);
    }

    @Override
    public PieceType getIdentity() {
        return PieceType.CANNON;
    }
}
