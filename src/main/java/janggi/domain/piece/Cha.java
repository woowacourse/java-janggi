package janggi.domain.piece;

import janggi.domain.board.Intersection;
import janggi.domain.board.Location;
import janggi.domain.Side;
import janggi.domain.rule.collision.CollisionDetector;
import janggi.domain.rule.collision.DefaultCollisionDetector;
import janggi.domain.rule.route.RouteProvider;
import janggi.domain.rule.route.StraightRouteProvider;
import janggi.exception.RouteResolveException;
import java.util.List;

public class Cha extends ActivePiece {

    private static final PieceType PIECE_TYPE = PieceType.CHA;
    private static final RouteProvider ROUTE_PROVIDER = StraightRouteProvider.getInstance();
    private static final CollisionDetector COLLISION_DETECTOR = DefaultCollisionDetector.getInstance();

    public Cha(Side side) {
        super(PIECE_TYPE, side);
    }

    @Override
    public List<Location> calculateRoute(Intersection from, Intersection to) {
        try {
            return ROUTE_PROVIDER.calculateRoute(from, to);
        } catch (RouteResolveException e) {
            throw new RouteResolveException(pieceType, from.getLocation(), to.getLocation());
        }
    }

    @Override
    public void detectCollision(List<Piece> piecesOnPath) {
        COLLISION_DETECTOR.check(this, piecesOnPath);
    }
}
