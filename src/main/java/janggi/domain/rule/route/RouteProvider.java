package janggi.domain.rule.route;

import janggi.domain.Location;
import java.util.List;

public interface RouteProvider {

    List<Location> calculateRoute(Location from, Location to);
}
