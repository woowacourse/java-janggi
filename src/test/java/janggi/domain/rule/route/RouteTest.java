package janggi.domain.rule.route;

import janggi.domain.Location;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class RouteTest {

    @Test
    @DisplayName("현재 위치에서 주어진 Direction 들을 적용했을 때 지나는 경로를 반환한다.")
    void shouldReturnRouteLocationsCalculatedByCurrentLocation() {
        // given
        Location current = new Location(0, 0);
        Route route = Route.from(
                List.of(
                        Direction.FRONT, // + (-1, 0)
                        Direction.FRONT_LEFT, // + (-1, -1)
                        Direction.FRONT_RIGHT, // + (-1, 1)
                        Direction.BACK // + (1, 0)
                )
        );
        List<Location> expected = List.of(
                new Location(-1, 0),
                new Location(-2, -1),
                new Location(-3, 0),
                new Location(-2, 0)
        );

        // when
        List<Location> routeLocations = route.calculateLocationsOnPath(current);

        // then
        Assertions.assertThat(routeLocations).isEqualTo(expected);
    }
}
