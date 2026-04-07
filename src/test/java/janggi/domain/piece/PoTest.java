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

public class PoTest {

    @Nested
    class CalculateRouteTest {

        static List<Location> provideUnreachableCoordination() {
            return List.of(
                    Location.of(1, 1),
                    Location.of(2, 2),
                    Location.of(6, 6)
            );
        }

        @Test
        @DisplayName("포가 이동할 수 있는 위치를 파라미터로 받으면 이동 경로를 반환한다.")
        void shouldReturnRouteForReachableLocation() {
            // given
            Piece piece = new Po(Side.HAN);
            Intersection base = TestIntersectionUtil.getDefaultIntersection(Location.of(0, 0), piece);
            Intersection destination = TestIntersectionUtil.getDefaultEmptyPieceIntersection(Location.of(0, 6));

            List<Location> expected = List.of(
                    Location.from(List.of(0, 1)),
                    Location.from(List.of(0, 2)),
                    Location.from(List.of(0, 3)),
                    Location.from(List.of(0, 4)),
                    Location.from(List.of(0, 5)),
                    Location.from(List.of(0, 6))
            );

            // when & then
            Assertions.assertThat(piece.calculateRoute(base, destination))
                    .isEqualTo(expected);
        }

        @ParameterizedTest
        @DisplayName("포가 이동할 수 없는 위치를 파라미터로 받으면 예외가 발생한다.")
        @MethodSource("provideUnreachableCoordination")
        void shouldThrowExceptionForUnReachableLocation(Location to) {
            // given
            Piece piece = new Po(Side.CHO);
            Intersection base = TestIntersectionUtil.getDefaultIntersection(Location.of(5, 5), piece);
            Intersection destination = TestIntersectionUtil.getDefaultEmptyPieceIntersection(to);

            // when & then
            Assertions.assertThatThrownBy(() -> piece.calculateRoute(base, destination))
                    .isInstanceOf(RouteResolveException.class);
        }
    }

    @Nested
    class GungSeqongCalculateRouteTest {

        @Test
        @DisplayName("차는 궁성 안에서 대각선 길이 있는 경우 대각선으로 이동할 수 있다.")
        void returnRoute_WhenMovingDiagonallyInPalace() {
            // given
            Piece piece = new Po(Side.CHO);
            Intersection base = TestIntersectionUtil.getPalaceLeftTopIntersection(Location.of(1, 4), piece);
            Intersection destination = TestIntersectionUtil.getDefaultEmptyPieceIntersection(Location.of(3, 6));

            List<Location> expected = List.of(Location.of(2, 5), Location.of(3, 6));

            // when & then
            Assertions.assertThat(piece.calculateRoute(base, destination))
                    .isEqualTo(expected);
        }

        @Test
        @DisplayName("차는 궁성 밖으로 대각선 이동을 시도하면 예외가 발생한다.")
        void throwException_WhenMovingDiagonallyOutOfPalace() {
            // given
            Piece piece = new Po(Side.CHO);
            Intersection base = TestIntersectionUtil.getPalaceLeftTopIntersection(Location.of(1, 4), piece);
            Intersection destination = TestIntersectionUtil.getDefaultEmptyPieceIntersection(Location.of(4, 7));

            // when & then
            Assertions.assertThatThrownBy(() -> piece.calculateRoute(base, destination))
                    .isInstanceOf(RouteResolveException.class);
        }

    }
}
