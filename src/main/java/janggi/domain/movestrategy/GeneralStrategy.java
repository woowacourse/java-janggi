package janggi.domain.movestrategy;

import janggi.domain.board.Position;
import janggi.domain.piece.Piece;
import janggi.domain.piece.PieceType;

import java.util.List;
import java.util.Map;

public class GeneralStrategy implements MoveStrategy{

    @Override
    public boolean canMove(Position from, Position to) {
        return from.isApartFrom(to) == 1;
    }

    @Override
    public List<Position> findPath(Position from, Position to) {
        return List.of(to);
    }

    @Override
    public boolean determineMovingRule(Piece sourcePiece, Map<Position, Piece> positionPieces, Position to) {
        for (Piece piece : positionPieces.values()) {
            if (piece.isSameTeam(sourcePiece)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public PieceType getIdentity() {
        return PieceType.GENERAL;
    }
}
