package janggi.domain.rule.route;

import janggi.domain.Location;
import java.util.List;
import java.util.Optional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class DefaultRouteProviderTest {

    @Test
    @DisplayName("시작 위치에서 도착 위치까지 도달 가능하다면, 그 사이 경로 좌표를 반환한다.")
    void shouldReturnLocationsWhenValidPathIsFound() {
        // given
        Location from = new Location(1,1);
        Location to = new Location(1,5);
        List<Route> possibleRoutes = List.of(Route.of(Direction.FRONT, 4));
        RouteProvider routeProvider = new DefaultRouteProvider(possibleRoutes);

        // when
        Optional<List<Location>> locationsOfValidPath = routeProvider.calculateRoute(from, to);

        // then
        Assertions.assertThat(locationsOfValidPath).isPresent();
        Assertions.assertThat(locationsOfValidPath.get()).containsExactly(
                new Location(1,2),
                new Location(1,3),
                new Location(1,4),
                new Location(1,5)
        );
    }

    @Test
    @DisplayName("시작 위치에서 도착 위치까지 도달 불가능한 경우 빈 결과를 반환한다.")
    void shouldReturnEmptyWhenValidPathIsNotFound() {
        // given
        Location from = new Location(1,1);
        Location to = new Location(5,1);
        List<Route> possibleRoutes = List.of(Route.of(Direction.FRONT, 4));
        RouteProvider routeProvider = new DefaultRouteProvider(possibleRoutes);

        // when & then
        Assertions.assertThat(routeProvider.calculateRoute(from, to)).isEmpty();
    }
}
