package janggi.domain.rule.route;

import janggi.domain.Location;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

class GungSeongRouteProviderTest {

    private static final GungSeongRouteProvider GUNG_SEONG_ROUTE_PROVIDER = GungSeongRouteProvider.getInstance();

    @ParameterizedTest
    @DisplayName("궁성 안에서 이동할 수 있는 위치로 경로를 계산하면, 이동 경로를 반환한다.")
    @MethodSource("provideReachableCoordination")
    void shouldReturnRouteForReachableLocation(List<Integer> destination) {
        // given
        Location from = Location.from(List.of(4, 1));
        Location to = Location.from(destination);

        // when & then
        Assertions.assertThat(GUNG_SEONG_ROUTE_PROVIDER.calculateRoute(from, to)).hasValue(List.of(to));
    }

    static List<List<Integer>> provideReachableCoordination() {
        return List.of(
                List.of(4, 2), //상
                List.of(4, 0), //하
                List.of(3, 1), //좌
                List.of(5, 1), //우
                List.of(5, 2), //우대각
                List.of(3, 2) //좌대각
        );
    }

    @ParameterizedTest
    @DisplayName("궁성을 벗어난 위치로 경로를 계산하면, 빈 결과를 반환한다.")
    @MethodSource("provideUnreachableCoordination")
    void shouldReturnEmptyForUnReachableLocation(List<Integer> coordination) {
        // given
        Location from = Location.from(List.of(3, 2));
        Location to = Location.from(coordination);

        // when & then
        Assertions.assertThat(GUNG_SEONG_ROUTE_PROVIDER.calculateRoute(from, to)).isEmpty();
    }

    static List<List<Integer>> provideUnreachableCoordination() {
        return List.of(
                List.of(3, 4),
                List.of(5, 2),
                List.of(3, 5)
        );
    }
}
