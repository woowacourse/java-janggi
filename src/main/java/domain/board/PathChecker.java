package domain.board;

import domain.piece.Camp;
import domain.piece.Piece;
import domain.piece.PieceType;

import java.util.List;

public interface PathChecker {
    List<Piece> findPiecesInPath(List<Position> path);

    boolean isSameCamp(Position from, Position to);

    Camp findCamp(Position position);

    boolean isTargetType(Position position, PieceType pieceType);

    boolean isInPalace(Position position);

    boolean isOnPalaceCenter(Position position);

    boolean isInDifferencePalace(Position from, Position to);

    Position findPalaceCenter(Position from);
}
