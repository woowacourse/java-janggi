package janggi.support;

import janggi.domain.piece.Piece;
import janggi.domain.rule.collision.CollisionDetector;
import java.util.List;

public class TestCollisionDetector implements CollisionDetector {
    @Override
    public void check(Piece piece, List<Piece> piecesOnPath) {
        throw new UnsupportedOperationException();
    }
}
