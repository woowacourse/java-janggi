package route;

import static route.Direction.EAST;
import static route.Direction.NORTH;
import static route.Direction.SOUTH;
import static route.Direction.WEST;

import java.util.List;
import java.util.Set;
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

    public static Routes ofBlank(){
        return new Routes(Set.of());
    }

    private Routes(Set<Route> routes) {
        this.routes = routes;
    }

    public Set<Route> routes() {
        return routes;
    }

    public Routes possibleRoutes() {
        for (Route route : routes) {

        }
        return null;
    }
}