package route;

import static route.Direction.EAST;
import static route.Direction.EAST_NORTH;
import static route.Direction.EAST_SOUTH;
import static route.Direction.NORTH;
import static route.Direction.NORTH_EAST;
import static route.Direction.NORTH_WEST;
import static route.Direction.SOUTH;
import static route.Direction.SOUTH_EAST;
import static route.Direction.SOUTH_WEST;
import static route.Direction.WEST;
import static route.Direction.WEST_NORTH;
import static route.Direction.WEST_SOUTH;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import position.Board;
import position.Position;

public final class Routes {
    private final Set<Route> routes;

    public static Routes ofPalace(){
        return new Routes(Set.of(
            new Route(List.of(EAST)),
            new Route(List.of(WEST)),
            new Route(List.of(SOUTH)),
            new Route(List.of(NORTH))
        ));
    }

    public static Routes ofSoldier(){
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

    public static Routes ofElephant(){
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
                new Route(List.of(NORTH))
        ));
    }

    public static Routes ofChoPawn() {
        return new Routes(Set.of(
                new Route(List.of(EAST)),
                new Route(List.of(WEST)),
                new Route(List.of(SOUTH))
        ));
    }

    public static Routes ofBlank(){
        return new Routes(Set.of());
    }

    private Routes(Set<Route> routes) {
        this.routes = routes;
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


    public Set<Route> routes() {
        return routes;
    }

    public Routes possibleRoutes(Position source, Board board) {
        Set<Route> possibleRoutes = new HashSet<>();
        for (Route route : routes) {
            if(route.isPossibleRoute(source, board)){
                possibleRoutes.add(route);
            }
        }
        return new Routes(possibleRoutes);
    }

    public Routes possibleStraightRoutes(Position source, Board board) {
        Set<Route> possibleRoutes = new HashSet<>();
        for (Route route : routes) {
            Route straight = route;
            while(straight.isPossibleRoute(source, board)){
                possibleRoutes.add(straight);
                straight = straight.add(route);
            }
        }
        return new Routes(possibleRoutes);
    }
}