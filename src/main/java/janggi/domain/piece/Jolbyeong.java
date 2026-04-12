package janggi.domain.piece;

import janggi.domain.Side;
import janggi.domain.board.Intersection;
import janggi.domain.board.Location;
import janggi.domain.rule.collision.CollisionDetector;
import janggi.domain.rule.collision.DefaultCollisionDetector;
import janggi.domain.rule.route.Direction;
import janggi.domain.rule.route.RouteProvider;
import janggi.domain.rule.route.StraightRouteProvider;
import janggi.exception.ErrorCode;
import janggi.exception.RouteResolveException;
import java.util.List;

public class Jolbyeong extends ActivePiece {

    private static final int MAX_MOVE_DISTANCE = 1;
    private static final PieceType PIECE_TYPE = PieceType.JOLBYEOUNG;

    private static final RouteProvider ROUTE_PROVIDER = StraightRouteProvider.getInstance();
    private static final CollisionDetector COLLISION_DETECTOR = DefaultCollisionDetector.getInstance();

    public Jolbyeong(Side side) {
        super(PIECE_TYPE, side);
    }

    @Override
    protected List<Location> resolveRoute(Intersection from, Intersection to) {
        List<Location> moveRoutes = ROUTE_PROVIDER.calculateRoute(from, to);
        validateMoveBack(from.getLocation(), to.getLocation());
        validateMoveDistance(moveRoutes, from, to);
        return moveRoutes;
    }

    @Override
    public void detectCollision(List<Piece> piecesOnPath) {
        COLLISION_DETECTOR.check(this, piecesOnPath);
    }

    private void validateMoveDistance(List<Location> moveRoutes, Intersection from, Intersection to) {
        if (moveRoutes.size() > MAX_MOVE_DISTANCE) {
            throw new RouteResolveException(pieceType, from.getLocation(), to.getLocation());
        }
    }

    private void validateMoveBack(Location from, Location to) {
        Direction sideRealBack = side.getBackDirection();
        Direction locationDirection = Direction.getDirection(from, to);

        if (sideRealBack.isSameDirection(locationDirection)) {
            throw new RouteResolveException(ErrorCode.JOLBYEONG_MOVE_BACK_ERROR);
        }
    }
}
