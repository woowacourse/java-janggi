package domain.position;

import java.util.List;
import java.util.function.Predicate;

public class Routes {
    private final List<Route> routes;

    private Routes(List<Route> routes) {
        this.routes = routes;
    }

    public static Routes of(List<Route> routes) {
        return new Routes(routes);
    }

    public boolean hasRouteTo(Position source, Position destination) {
        return routes.stream()
                .map(route -> route.searchDestination(source))
                .anyMatch(position -> position.equals(destination));
    }

    public Routes filterByCondition(Predicate<Route> condition) {
        return Routes.of(
                routes.stream()
                        .filter(condition)
                        .toList()
        );
    }

    public List<Route> getRoutes() {
        return routes;
    }
}
