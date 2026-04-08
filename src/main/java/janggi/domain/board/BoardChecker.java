package janggi.domain.board;

import janggi.domain.Position;
import janggi.domain.piece.Piece;
import janggi.exception.ExceptionMessage;
import java.util.List;

public interface BoardChecker {

    boolean hasPieceAt(Position position);

    Piece pieceAt(Position position);

    boolean isSamePieceRule(Position source, Position target);

    default void validateEmptyPath(List<Position> path) {
        if (path.stream().anyMatch(this::hasPieceAt)) {
            throw new IllegalArgumentException(ExceptionMessage.PATH_NOT_EMPTY.getMessage());
        }
    }
}
