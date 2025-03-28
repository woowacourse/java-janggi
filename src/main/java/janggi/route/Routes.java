package janggi.route;

import static janggi.route.Direction.EAST;
import static janggi.route.Direction.EAST_NORTH;
import static janggi.route.Direction.EAST_SOUTH;
import static janggi.route.Direction.NORTH;
import static janggi.route.Direction.NORTH_EAST;
import static janggi.route.Direction.NORTH_WEST;
import static janggi.route.Direction.SOUTH;
import static janggi.route.Direction.SOUTH_EAST;
import static janggi.route.Direction.SOUTH_WEST;
import static janggi.route.Direction.WEST;
import static janggi.route.Direction.WEST_NORTH;
import static janggi.route.Direction.WEST_SOUTH;

import janggi.position.Board;
import janggi.position.Position;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public final class Routes {
    private final Set<Route> routes;

    public static Routes ofPalace() {
        return new Routes(Set.of(
                new Route(List.of(EAST)),
                new Route(List.of(WEST)),
                new Route(List.of(SOUTH)),
                new Route(List.of(NORTH))
        ));
    }

    public static Routes ofSoldier() {
        return new Routes(Set.of(
                new Route(List.of(EAST)),
                new Route(List.of(WEST)),
                new Route(List.of(SOUTH)),
                new Route(List.of(NORTH))
        ));
    }

    public static Routes ofHorse() {
        return new Routes(Set.of(
                new Route(List.of(EAST, EAST_NORTH)),
                new Route(List.of(EAST, EAST_SOUTH)),

                new Route(List.of(WEST, WEST_NORTH)),
                new Route(List.of(WEST, WEST_SOUTH)),

                new Route(List.of(SOUTH, SOUTH_EAST)),
                new Route(List.of(SOUTH, SOUTH_WEST)),

                new Route(List.of(NORTH, NORTH_EAST)),
                new Route(List.of(NORTH, NORTH_WEST))
        ));
    }

    public static Routes ofElephant() {
        return new Routes(Set.of(
                new Route(List.of(EAST, EAST_NORTH, EAST_NORTH)),
                new Route(List.of(EAST, EAST_SOUTH, EAST_SOUTH)),

                new Route(List.of(WEST, WEST_NORTH, WEST_NORTH)),
                new Route(List.of(WEST, WEST_SOUTH, WEST_SOUTH)),

                new Route(List.of(SOUTH, SOUTH_EAST, SOUTH_EAST)),
                new Route(List.of(SOUTH, SOUTH_WEST, SOUTH_WEST)),

                new Route(List.of(NORTH, NORTH_EAST, NORTH_EAST)),
                new Route(List.of(NORTH, NORTH_WEST, NORTH_WEST))
        ));
    }

    public static Routes ofHanPawn() {
        return new Routes(Set.of(
                new Route(List.of(EAST)),
                new Route(List.of(WEST)),
                new Route(List.of(SOUTH))
        ));
    }

    public static Routes ofChoPawn() {
        return new Routes(Set.of(
                new Route(List.of(EAST)),
                new Route(List.of(WEST)),
                new Route(List.of(NORTH))
        ));
    }

    public static Routes ofBlank() {
        return new Routes(Set.of());
    }

    public static Routes ofChariot() {
        return new Routes(Set.of(
                new Route(List.of(EAST)),
                new Route(List.of(WEST)),
                new Route(List.of(SOUTH)),
                new Route(List.of(NORTH))
        ));
    }

    public static Routes ofCannon() {
        return new Routes(Set.of(
                new Route(List.of(EAST)),
                new Route(List.of(WEST)),
                new Route(List.of(SOUTH)),
                new Route(List.of(NORTH))
        ));
    }

    public Routes(Set<Route> routes) {
        this.routes = routes;
    }

    public Set<Route> routes() {
        return routes;
    }

    public Set<Position> possibleRoutes(Position source, Board board) {
        Set<Position> target = new HashSet<>();
        for (Route route : routes) {
            if (route.isPossibleRoute(source, board)) {
                target.add(source.move(route));
            }
        }
        return target;
    }

    public Set<Position> possibleStraightRoutes(Position source, Board board) {
        Set<Position> target = new HashSet<>();
        for (Route route : routes) {
            Route straight = route;
            while (straight.isPossibleRoute(source, board)) {
                target.add(source.move(straight));
                straight = straight.add(route);
            }
        }
        return target;
    }

    public Set<Position> possibleJumpingRoutes(Position source, Board board) {
        Set<Position> target = new HashSet<>();
        for (Route route : routes) {

            Route straightBeforeJump = route;
            while (true) {
                if (straightBeforeJump.canJump(source, board)) {
                    Position jump = source.move(straightBeforeJump);

                    Route straight = route;
                    while (straight.isPossibleRouteForCannon(jump, board)) {
                        target.add(jump.move(straight));
                        straight = straight.add(route);
                    }
                }
                if (!source.canMove(straightBeforeJump, board)) {
                    break;
                }
                straightBeforeJump = straightBeforeJump.add(route);
            }
        }
        return target;
    }
}
