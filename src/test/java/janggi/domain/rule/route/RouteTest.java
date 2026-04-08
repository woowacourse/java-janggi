package janggi.domain.rule.route;

import janggi.domain.board.Location;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class RouteTest {

    @Test
    @DisplayName("현재 위치에서 주어진 Direction 들을 적용했을 때 지나는 경로를 반환한다.")
    void shouldReturnRouteLocationsCalculatedByCurrentLocation() {
        // given
        Location current = Location.from(List.of(0, 0));
        Route route = Route.of(
                List.of(
                        Direction.FRONT, // 0,1
                        Direction.FRONT_LEFT, // -1, 2
                        Direction.FRONT_RIGHT, // 0, 3
                        Direction.BACK // 0, 2
                )
        );
        List<Location> expected = List.of(
                Location.from(List.of(1, 0)),
                Location.from(List.of(2, -1)),
                Location.from(List.of(3, 0)),
                Location.from(List.of(2, 0))
        );

        // when
        List<Location> routeLocations = route.apply(current);

        // then
        Assertions.assertThat(routeLocations).isEqualTo(expected);
    }
}
