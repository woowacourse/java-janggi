package janggi.domain.board;

import janggi.domain.piece.BoardPiece;
import java.util.Set;

public interface BoardSetUp {
    Set<BoardPiece> getPiecePositions();
}
