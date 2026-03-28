package janggi.domain.direction;

import janggi.domain.Location;
import java.util.List;
import java.util.stream.Stream;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class StraightRouteRuleTest {

    @ParameterizedTest
    @DisplayName("직선으로 이동할 위치를 파라미터로 받으면 이동 경로를 반환한다.")
    @MethodSource("provideReachableCoordination")
    void shouldReturnRouteForReachableLocation(Location destination, List<Location> route) {
        // given
        Location from = Location.from(List.of(2, 2));
        RouteRule routeRule = new StraightRouteRule();

        // when & then
        Assertions.assertThat(routeRule.calculateRoute(from, destination))
                .isEqualTo(route);
    }

    static Stream<Arguments> provideReachableCoordination() {
        return Stream.of(
                Arguments.of(
                        new Location(0, 2), // 상
                        List.of(new Location(1, 2), new Location(0, 2))
                ),
                Arguments.of(
                        new Location(5, 2), // 하
                        List.of(new Location(3, 2), new Location(4, 2), new Location(5, 2))
                ),
                Arguments.of(
                        new Location(2, 0), // 좌
                        List.of(new Location(2, 1), new Location(2, 0))
                ),
                Arguments.of(
                        new Location(2, 5), // 우
                        List.of(new Location(2, 3), new Location(2, 4), new Location(2, 5))
                )
        );
    }

    @ParameterizedTest
    @DisplayName("직선으로 이동이 불가능한 위치를 파라미터로 받으면 예외가 발생한다.")
    @MethodSource("provideUnreachableCoordination")
    void shouldThrowExceptionForUnReachableLocation(List<Integer> coordination) {
        // given
        Location from = Location.from(List.of(1, 1));
        Location to = Location.from(coordination);
        RouteRule routeRule = new StraightRouteRule();

        // when & then
        Assertions.assertThatThrownBy(() -> routeRule.calculateRoute(from, to))
                .isInstanceOf(IllegalArgumentException.class);
    }

    static List<List<Integer>> provideUnreachableCoordination() {
        return List.of(
                List.of(3, 3),
                List.of(3, 2),
                List.of(2, 2)
        );
    }
}
