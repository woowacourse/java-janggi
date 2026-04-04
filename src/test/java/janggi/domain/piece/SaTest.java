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

public class SaTest {

    @Nested
    class CalculateRouteTest {
        static List<Location> provideReachableCoordination() {
            return List.of(
                    Location.of(1, 4), //좌상
                    Location.of(1, 5), //상
                    Location.of(1, 6), //우상
                    Location.of(2, 4), //좌
                    Location.of(2, 6), //우
                    Location.of(3, 4), //좌하
                    Location.of(3, 5), //하
                    Location.of(3, 6) //우하
            );
        }

        static List<Location> provideUnreachableCoordination() {
            return List.of(
                    Location.of(1, 1),
                    Location.of(4, 5),
                    Location.of(2, 7)
            );
        }

        @ParameterizedTest
        @DisplayName("사가 이동할 수 있는 위치를 파라미터로 받으면 이동 경로를 반환한다.")
        @MethodSource("provideReachableCoordination")
        void shouldReturnRouteForReachableLocation(Location to) {
            // given
            Piece piece = new Sa(Side.HAN);
            Intersection base = TestIntersectionUtil.getGungSeongCenterIntersection(Location.of(2, 5), piece);
            Intersection destination = TestIntersectionUtil.getDefaultEmptyPieceIntersection(to);

            List<Location> expected = List.of(to);

            // when & then
            Assertions.assertThat(piece.calculateRoute(base, destination))
                    .isEqualTo(expected);
        }

        @Test
        @DisplayName("사는 궁성 안에서 대각선 길이 있는 경우 대각선으로 이동할 수 있다.")
        void returnRoute_WhenAtoGungSeongLeftTop() {
            // given
            Piece piece = new Sa(Side.HAN);
            Intersection base = TestIntersectionUtil.getGungSeongLeftTopIntersection(Location.of(1,4), piece);
            Intersection destination = TestIntersectionUtil.getDefaultEmptyPieceIntersection(Location.of(2,5));

            List<Location> expected = List.of(Location.of(2,5));

            // when & then
            Assertions.assertThat(piece.calculateRoute(base, destination))
                    .isEqualTo(expected);
        }


        @ParameterizedTest
        @DisplayName("사가 이동할 수 없는 위치를 파라미터로 받으면 예외가 발생한다.")
        @MethodSource("provideUnreachableCoordination")
        void shouldThrowExceptionForUnReachableLocation(Location to) {
            // given
            Piece piece = new Sa(Side.CHO);
            Intersection base = TestIntersectionUtil.getDefaultIntersection(Location.of(2, 5), piece);
            Intersection destination = TestIntersectionUtil.getDefaultEmptyPieceIntersection(to);

            // when & then
            Assertions.assertThatThrownBy(() -> piece.calculateRoute(base, destination))
                    .isInstanceOf(RouteResolveException.class);
        }
    }
}
