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
        Location from = Location.from(List.of(2, 2));

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
    @DisplayName("직선으로 이동이 불가능한 위치로 경로를 계산하면, 빈 결과를 반환한다.")
    @MethodSource("provideUnreachableCoordination")
    void shouldReturnEmptyForUnReachableLocation(List<Integer> coordination) {
        // given
        Location from = Location.from(List.of(1, 1));
        Location to = Location.from(coordination);

        // when & then
        Assertions.assertThat(STRAIGHT_ROUTE_PROVIDER.calculateRoute(from, to)).isEmpty();
    }

    static List<List<Integer>> provideUnreachableCoordination() {
        return List.of(
                List.of(3, 3),
                List.of(3, 2),
                List.of(2, 2)
        );
    }
}
