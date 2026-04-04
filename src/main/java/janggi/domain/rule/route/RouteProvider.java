package janggi.domain.rule.route;

import janggi.domain.Intersection;
import janggi.domain.Location;
import janggi.exception.RouteResolveException;
import java.util.List;

public interface RouteProvider {

    /**
     * @throws RouteResolveException from 에서 to 까지의 경로가 존재하지 않는 경우 발생
     */
    List<Location> calculateRoute(Intersection from, Intersection to);
}
