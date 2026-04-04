package janggi.domain.rule.route;

import janggi.domain.Location;
import java.util.List;
import java.util.stream.Stream;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class StraightRouteProviderTest {

    private static final StraightRouteProvider STRAIGHT_ROUTE_PROVIDER = StraightRouteProvider.getInstance();

    @ParameterizedTest
    @DisplayName("직선으로 이동할 위치로 경로를 계산하면, 이동 경로를 반환한다.")
    @MethodSource("provideReachableCoordination")
    void shouldReturnRouteForReachableLocation(Location destination, List<Location> route) {
        // given
        Location from = new Location(2, 2);

        // when & then
        Assertions.assertThat(STRAIGHT_ROUTE_PROVIDER.calculateRoute(from, destination)).hasValue(route);
    }

    static Stream<Arguments> provideReachableCoordination() {
        return Stream.of(
                Arguments.of(
                        new Location(0, 2), // 상
                        List.of(new Location(1, 2), new Location(0, 2))
                ),
                Arguments.of(
                        new Location(4, 2), // 하
                        List.of(new Location(3, 2), new Location(4, 2))
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
    @DisplayName("직선으로 이동이 불가능한 위치로 경로를 계산하면, 빈 결과를 반환한다.")
    @MethodSource("provideUnreachableCoordination")
    void shouldReturnEmptyForUnReachableLocation(Location destination) {
        // given
        Location from = new Location(1, 1);

        // when & then
        Assertions.assertThat(STRAIGHT_ROUTE_PROVIDER.calculateRoute(from, destination)).isEmpty();
    }

    static List<Location> provideUnreachableCoordination() {
        return List.of(
                new Location(3, 3),
                new Location(3, 2),
                new Location(2, 2)
        );
    }
}
