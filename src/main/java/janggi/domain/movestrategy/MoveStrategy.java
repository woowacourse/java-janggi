package janggi.domain.movestrategy;

import janggi.domain.board.Position;
import janggi.domain.piece.Piece;
import janggi.domain.piece.PieceType;

import java.util.List;
import java.util.Map;

public interface MoveStrategy {
    boolean canMove(Position from, Position to);

    List<Position> findPath(Position from, Position to);

    boolean determineMovingRule(Piece sourcePiece, Map<Position, Piece> positionPieces, Position to);

    PieceType getIdentity();

    boolean checkPathRule(List<Piece> pathPieces);

    default boolean canCapture(Piece from, Piece to) {
        return to == null || !from.isSameTeam(to);
    }
}

