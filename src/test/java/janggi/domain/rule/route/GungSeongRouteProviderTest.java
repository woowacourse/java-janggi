package janggi.domain.rule.route;

import janggi.domain.Location;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

class GungSeongRouteProviderTest {

    @ParameterizedTest
    @DisplayName("궁성 안에서 이동할 수 있는 위치를 파라미터로 받으면 이동 경로를 반환한다.")
    @MethodSource("provideReachableCoordination")
    void shouldReturnRouteForReachableLocation(List<Integer> destination) {
        // given
        Location from = Location.from(List.of(4, 1));
        Location to = Location.from(destination);
        RouteProvider routeProvider = new GungSeongRouteProvider();

        // when & then
        Assertions.assertThat(routeProvider.calculateRoute(from, to))
                .isEqualTo(List.of(to));
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
    @DisplayName("궁성을 벗어난 위치를 파라미터로 받으면 예외가 발생한다.")
    @MethodSource("provideUnreachableCoordination")
    void shouldThrowExceptionForUnReachableLocation(List<Integer> coordination) {
        // given
        Location from = Location.from(List.of(3, 2));
        Location to = Location.from(coordination);
        RouteProvider routeProvider = new GungSeongRouteProvider();

        // when & then
        Assertions.assertThatThrownBy(() -> routeProvider.calculateRoute(from, to))
                .isInstanceOf(IllegalArgumentException.class);
    }

    static List<List<Integer>> provideUnreachableCoordination() {
        return List.of(
                List.of(3, 4),
                List.of(5, 2),
                List.of(3, 5)
        );
    }
}
