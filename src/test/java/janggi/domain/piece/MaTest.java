package janggi.domain.piece;

import janggi.domain.Intersection;
import janggi.domain.Location;
import janggi.domain.Side;
import janggi.exception.RouteResolveException;
import janggi.support.TestIntersectionUtil;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class MaTest {

    @Nested
    class CalculateRouteTest {

        static List<Location> provideUnreachableCoordination() {
            return List.of(
                    Location.of(0, 3),
                    Location.of(-1, 3),
                    Location.of(0, 1)
            );
        }

        @Test
        @DisplayName("마가 이동할 수 있는 위치를 파라미터로 받으면 이동 경로를 반환한다.")
        void shouldReturnRouteForReachableLocation() {
            // given
            Piece piece = new Ma(Side.HAN);
            Intersection base = TestIntersectionUtil.getDefaultIntersection(Location.of(0, 0), piece);
            Intersection destination = TestIntersectionUtil.getDefaultEmptyPieceIntersection(Location.of(1, 2));

            List<Location> expected = List.of(
                    Location.of(0, 1),
                    Location.of(1, 2)
            );

            // when & then
            Assertions.assertThat(piece.calculateRoute(base, destination))
                    .isEqualTo(expected);
        }

        @ParameterizedTest
        @DisplayName("마가 이동할 수 없는 위치를 파라미터로 받으면 예외가 발생한다.")
        @MethodSource("provideUnreachableCoordination")
        void shouldThrowExceptionForUnReachableLocation(Location to) {
            // given
            Piece piece = new Ma(Side.CHO);
            Intersection base = TestIntersectionUtil.getDefaultIntersection(Location.of(0, 0), piece);
            Intersection destination = TestIntersectionUtil.getDefaultEmptyPieceIntersection(to);

            // when & then
            Assertions.assertThatThrownBy(() -> piece.calculateRoute(base, destination))
                    .isInstanceOf(RouteResolveException.class);
        }
    }
}
