package janggi.domain.board;

import janggi.domain.piece.PointPiece;
import java.util.Set;

public interface BoardSetUp {
    Set<PointPiece> getPiecePositions();
}
