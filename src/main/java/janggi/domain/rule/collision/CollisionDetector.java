package janggi.domain.rule.collision;

import janggi.domain.Side;
import janggi.domain.piece.Piece;
import java.util.List;

public interface CollisionDetector {

    void check(Side side, List<Piece> piecesOnPath);
}
