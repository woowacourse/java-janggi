package janggi.domain.piece;

import static janggi.domain.rule.route.Direction.LEFT;
import static janggi.domain.rule.route.Direction.RIGHT;

import janggi.domain.Location;
import janggi.domain.Side;
import janggi.domain.rule.collision.CollisionDetector;
import janggi.domain.rule.collision.DefaultCollisionDetector;
import janggi.domain.rule.route.Direction;
import janggi.domain.rule.route.Route;
import janggi.domain.rule.route.RouteProvider;
import java.util.List;

public class Jolbyeong extends Piece {

    private static final CollisionDetector COLLISION_DETECTOR = DefaultCollisionDetector.getInstance();

    public Jolbyeong(Side side) {
        super(determineType(side), side);
    }

    private static PieceType determineType(Side side) {
        if (side == Side.HAN) {
            return PieceType.BYEONG;
        }
        if (side == Side.CHO) {
            return PieceType.JOL;
        }
        throw new IllegalArgumentException("진영이 존재하지 않아 기물명을 정할 수 없습니다.");
    }

    @Override
    public List<Location> calculateRoute(Location from, Location to) {
        List<Route> possibleRoutes = List.of(
                Route.from(List.of(getFrontDirection())),
                Route.from(List.of(LEFT)),
                Route.from(List.of(RIGHT))
        );

        return RouteProvider.findValidPath(pieceType, from, to, possibleRoutes);
    }

    private Direction getFrontDirection() {
        if (side == Side.HAN) {
            return Direction.FRONT;
        }
        if (side == Side.CHO) {
            return Direction.BACK;
        }
        throw new IllegalStateException("진영이 존재하지 않아 전진 방향을 정할 수 없습니다.");
    }

    @Override
    public void detectCollision(List<Piece> piecesOnPath) {
        COLLISION_DETECTOR.check(this, piecesOnPath);
    }
}
