package janggi.domain.rule.route;

import janggi.domain.Side;
import janggi.domain.board.Intersection;
import janggi.domain.board.Location;
import janggi.domain.piece.Piece;
import janggi.exception.RouteResolveException;
import janggi.support.TestIntersectionUtil;
import janggi.support.TestPiece;
import java.util.List;
import java.util.stream.Stream;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class StraightRouteProviderTest {

    static Stream<Arguments> provideReachableCoordination() {
        return Stream.of(
                Arguments.of(
                        Location.of(0, 2), // 상
                        List.of(Location.of(1, 2), Location.of(0, 2))
                ),
                Arguments.of(
                        Location.of(5, 2), // 하
                        List.of(Location.of(3, 2), Location.of(4, 2), Location.of(5, 2))
                ),
                Arguments.of(
                        Location.of(2, 0), // 좌
                        List.of(Location.of(2, 1), Location.of(2, 0))
                ),
                Arguments.of(
                        Location.of(2, 5), // 우
                        List.of(Location.of(2, 3), Location.of(2, 4), Location.of(2, 5))
                )
        );
    }

    static List<Location> provideUnreachableCoordination() {
        return List.of(
                Location.of(3, 3),
                Location.of(3, 2),
                Location.of(2, 2)
        );
    }

    @ParameterizedTest
    @DisplayName("직선으로 이동할 위치를 파라미터로 받으면 이동 경로를 반환한다.")
    @MethodSource("provideReachableCoordination")
    void shouldReturnRouteForReachableLocation(Location to, List<Location> route) {
        // given
        Piece piece = new TestPiece(Side.CHO);
        Intersection base = TestIntersectionUtil.getDefaultIntersection(Location.of(2, 2), piece);
        Intersection destination = TestIntersectionUtil.getDefaultEmptyPieceIntersection(to);

        RouteProvider routeProvider = StraightRouteProvider.getInstance();

        // when & then
        Assertions.assertThat(routeProvider.calculateRoute(base, destination))
                .isEqualTo(route);
    }

    @ParameterizedTest
    @DisplayName("직선으로 이동이 불가능한 위치를 파라미터로 받으면 예외가 발생한다.")
    @MethodSource("provideUnreachableCoordination")
    void shouldThrowExceptionForUnReachableLocation(Location to) {
        // given
        Piece piece = new TestPiece(Side.CHO);
        Intersection base = TestIntersectionUtil.getDefaultIntersection(Location.of(1, 1), piece);
        Intersection destination = TestIntersectionUtil.getDefaultEmptyPieceIntersection(to);

        RouteProvider routeProvider = StraightRouteProvider.getInstance();

        // when & then
        Assertions.assertThatThrownBy(() -> routeProvider.calculateRoute(base, destination))
                .isInstanceOf(RouteResolveException.class);
    }
}
