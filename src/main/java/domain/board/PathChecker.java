package domain.board;

import domain.piece.Camp;
import domain.piece.Piece;
import domain.piece.PieceType;

import java.util.List;

public interface PathChecker {
    List<Piece> findPiecesInPath(List<Position> path);

    boolean isSameCamp(Position from, Position to);

    Camp findCamp(Position position);

    PieceType findPieceType(Position position);
}
