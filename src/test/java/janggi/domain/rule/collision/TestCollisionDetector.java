package janggi.domain.rule.collision;

import janggi.domain.piece.Piece;
import java.util.List;

public class TestCollisionDetector implements CollisionDetector{
    @Override
    public void check(Piece piece, List<Piece> piecesOnPath) {
        throw new UnsupportedOperationException();
    }
}
