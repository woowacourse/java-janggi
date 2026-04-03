package janggi.domain.rule;

import static janggi.domain.rule.route.Direction.LEFT;
import static janggi.domain.rule.route.Direction.RIGHT;

import janggi.domain.Location;
import janggi.domain.Side;
import janggi.domain.piece.Piece;
import janggi.domain.rule.collision.CollisionDetector;
import janggi.domain.rule.collision.DefaultCollisionDetector;
import janggi.domain.rule.route.DefaultRouteProvider;
import janggi.domain.rule.route.Direction;
import janggi.domain.rule.route.Route;
import janggi.domain.rule.route.RouteProvider;
import java.util.List;
import java.util.Optional;

@SuppressWarnings("java:S6548")
public class JolbyeongMovement implements Movement {

    private static final JolbyeongMovement JOL_MOVEMENT_INSTANCE = new JolbyeongMovement(Side.CHO);
    private static final JolbyeongMovement BYEONG_MOVEMENT_INSTANCE = new JolbyeongMovement(Side.HAN);

    private final RouteProvider routeProvider;
    private final CollisionDetector collisionDetector = DefaultCollisionDetector.getInstance();

    private JolbyeongMovement(Side side) {
        List<Route> possibleRoutes = List.of(
                Route.from(List.of(getFrontDirection(side))),
                Route.from(List.of(LEFT)),
                Route.from(List.of(RIGHT))
        );

        this.routeProvider = new DefaultRouteProvider(possibleRoutes);
    }

    private static Direction getFrontDirection(Side side) {
        if (side == Side.HAN) {
            return Direction.FRONT;
        }
        if (side == Side.CHO) {
            return Direction.BACK;
        }
        throw new IllegalStateException("진영이 존재하지 않아 전진 방향을 정할 수 없습니다.");
    }

    public static JolbyeongMovement getInstanceBySide(Side side) {
        if (side == Side.HAN) {
            return BYEONG_MOVEMENT_INSTANCE;
        }
        if (side == Side.CHO) {
            return JOL_MOVEMENT_INSTANCE;
        }
        throw new IllegalArgumentException("진영이 존재하지 않아 이동 규칙을 정할 수 없습니다.");
    }

    @Override
    public Optional<List<Location>> calculateRoute(Location from, Location to) {
        return routeProvider.calculateRoute(from, to);
    }

    @Override
    public void detectCollision(Side side, List<Piece> piecesOnPath) {
        collisionDetector.check(side, piecesOnPath);
    }
}
