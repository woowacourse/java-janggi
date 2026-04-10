package janggi.domain.movestrategy;

import janggi.domain.board.Position;
import janggi.domain.piece.Piece;

import java.util.List;

public interface MoveStrategy {
    boolean canMove(Position from, Position to);

    List<Position> findPath(Position from, Position to);

    boolean checkPathRule(List<Piece> pathPieces);

    boolean canCapture(Piece from, Piece to);
}

