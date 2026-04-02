package domain.board;

import domain.piece.Piece;
import domain.piece.PieceType;

import java.util.List;

public interface BoardChecker {
    List<Piece> findPiecesInPath(List<Position> path);

    boolean isSameCamp(Position from, Position to);

    boolean isTargetType(Position position, PieceType pieceType);
}
