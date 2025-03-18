package janggi.domain;

import janggi.domain.piece.Piece;
import java.util.Map;

public interface BoardSetUp {
    Map<Point, Piece> getPiecePositions();
}
