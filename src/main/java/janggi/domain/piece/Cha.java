package janggi.domain.piece;

import janggi.domain.Side;
import janggi.domain.board.Intersection;
import janggi.domain.board.Location;
import janggi.domain.rule.collision.CollisionDetector;
import janggi.domain.rule.collision.DefaultCollisionDetector;
import janggi.domain.rule.route.RouteProvider;
import janggi.domain.rule.route.StraightRouteProvider;
import java.util.List;

public class Cha extends ActivePiece {

    private static final PieceType PIECE_TYPE = PieceType.CHA;
    private static final RouteProvider ROUTE_PROVIDER = StraightRouteProvider.getInstance();
    private static final CollisionDetector COLLISION_DETECTOR = DefaultCollisionDetector.getInstance();

    public Cha(Side side) {
        super(PIECE_TYPE, side);
    }

    @Override
    protected List<Location> resolveRoute(Intersection from, Intersection to) {
        return ROUTE_PROVIDER.calculateRoute(from, to);
    }

    @Override
    public void detectCollision(List<Piece> piecesOnPath) {
        COLLISION_DETECTOR.check(this, piecesOnPath);
    }
}
