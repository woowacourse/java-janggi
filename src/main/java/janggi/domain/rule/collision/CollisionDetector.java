package janggi.domain.rule.collision;

import janggi.domain.piece.Piece;
import java.util.List;

public interface CollisionDetector {

    void check(Piece piece, List<Piece> piecesOnPath);
}
