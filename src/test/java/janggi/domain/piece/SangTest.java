package janggi.domain.piece;

import janggi.domain.board.Intersection;
import janggi.domain.board.Location;
import janggi.domain.Side;
import janggi.exception.RouteResolveException;
import janggi.support.TestIntersectionUtil;
import java.util.List;
import java.util.stream.Stream;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class SangTest {

    @Nested
    class CalculateRouteTest {

        static Stream<Arguments> provideListsForTesting() {
            return Stream.of(
                    Arguments.of(
                            Location.of(2, 3),
                            List.of(Location.of(0, 1), Location.of(1, 2), Location.of(2, 3))
                    ),
                    Arguments.of(
                            Location.of(3, 2),
                            List.of(Location.of(1, 0), Location.of(2, 1), Location.of(3, 2))
                    )
            );
        }

        static List<Location> provideUnreachableCoordination() {
            return List.of(
                    Location.of(0, 4),
                    Location.of(-1, 3),
                    Location.of(0, 1)
            );
        }

        @ParameterizedTest
        @DisplayName("상이 이동할 수 있는 위치를 파라미터로 받으면 이동 경로를 반환한다.")
        @MethodSource("provideListsForTesting")
        void shouldReturnRouteForReachableLocation(Location to, List<Location> expected) {
            // given
            Piece piece = new Sang(Side.HAN);
            Intersection base = TestIntersectionUtil.getDefaultIntersection(Location.of(0, 0), piece);
            Intersection destination = TestIntersectionUtil.getDefaultEmptyPieceIntersection(to);

            // when & then
            Assertions.assertThat(piece.calculateRoute(base, destination))
                    .isEqualTo(expected);
        }

        @ParameterizedTest
        @DisplayName("상이 이동할 수 없는 위치를 파라미터로 받으면 예외가 발생한다.")
        @MethodSource("provideUnreachableCoordination")
        void shouldThrowExceptionForUnReachableLocation(Location to) {
            // given
            Piece piece = new Sang(Side.CHO);
            Intersection base = TestIntersectionUtil.getDefaultIntersection(Location.of(0, 0), piece);
            Intersection destination = TestIntersectionUtil.getDefaultEmptyPieceIntersection(to);

            // when & then
            Assertions.assertThatThrownBy(() -> piece.calculateRoute(base, destination))
                    .isInstanceOf(RouteResolveException.class);
        }
    }
}
