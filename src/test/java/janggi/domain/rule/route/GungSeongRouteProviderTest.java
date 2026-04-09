package janggi.domain.rule.route;

import janggi.domain.Location;
import janggi.domain.board.GungSeong;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

class GungSeongRouteProviderTest {

    private static final GungSeongRouteProvider GUNG_SEONG_ROUTE_PROVIDER =
            GungSeongRouteProvider.of(GungSeong.of(10, 9));

    @ParameterizedTest
    @DisplayName("궁성 안에서 이동할 수 있는 위치로 경로를 계산하면, 이동 경로를 반환한다.")
    @MethodSource("provideReachableCoordination")
    void shouldReturnRouteForReachableLocation(Location to) {
        // given
        Location from = new Location(1, 4);

        // when & then
        Assertions.assertThat(GUNG_SEONG_ROUTE_PROVIDER.calculateRoute(from, to)).hasValue(List.of(to));
    }

    static List<Location> provideReachableCoordination() {
        return List.of(
                new Location(0, 3),
                new Location(0, 4),
                new Location(0, 5),
                new Location(1, 3),
                new Location(1, 5),
                new Location(2, 3),
                new Location(2, 4),
                new Location(2, 5)
        );
    }

    @ParameterizedTest
    @DisplayName("궁성을 벗어난 위치로 경로를 계산하면, 빈 결과를 반환한다.")
    @MethodSource("provideUnreachableCoordination")
    void shouldReturnEmptyForUnReachableLocation(Location to) {
        // given
        Location from = new Location(2, 3);

        // when & then
        Assertions.assertThat(GUNG_SEONG_ROUTE_PROVIDER.calculateRoute(from, to)).isEmpty();
    }

    static List<Location> provideUnreachableCoordination() {
        return List.of(
                new Location(1, 2),
                new Location(2, 2),
                new Location(3, 2),
                new Location(3, 3),
                new Location(3, 4)
        );
    }
}
