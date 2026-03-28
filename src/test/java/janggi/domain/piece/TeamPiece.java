package janggi.domain.piece;

import janggi.domain.Location;
import janggi.domain.Side;
import janggi.domain.rule.collision.CollisionDetector;
import janggi.domain.rule.collision.TestCollisionDetector;
import java.util.List;

public class TeamPiece extends Piece {

    private static final String PIECE_NAME = "test";
    private final CollisionDetector detector;

    public TeamPiece(Side side) {
        this(side, new TestCollisionDetector());
    }

    public TeamPiece(Side side, CollisionDetector detector) {
        super(PIECE_NAME, side);
        this.detector = detector;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public List<Location> calculateRoute(Location from, Location to) {
        return List.of();
    }

    @Override
    public void detectCollision(List<Piece> piecesOnPath) {
        detector.check(this, piecesOnPath);
    }
}
