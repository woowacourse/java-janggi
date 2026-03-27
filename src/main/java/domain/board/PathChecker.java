package domain.board;

import domain.piece.Piece;

import java.util.List;

public interface PathChecker {
    List<Piece> findPiecesInPath(List<Position> path);

    boolean isSameCamp(Position from, Position to);
}
