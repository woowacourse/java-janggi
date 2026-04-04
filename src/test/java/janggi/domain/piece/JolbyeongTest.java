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

class JolbyeongTest {
    @Nested
    class CalculateRouteTest {
        static List<Location> provideUnreachableCoordination() {
            return List.of(
                    Location.of(3, 5), // 거리가 멀어서 도달할 수 없는 경우
                    Location.of(6, 6), // 대각선으로 이동하는 경우
                    Location.of(6, 5) // 뒤로 이동하는 경우
            );
        }

        @Test
        @DisplayName("한팀 졸병이 이동할 수 있는 위치를 파라미터로 받으면 이동 경로를 반환한다.")
        void shouldReturnRouteForReachableLocationWhenTeamHan() {
            // given
            Piece piece = new Jolbyeong(Side.HAN);
            Intersection base = TestIntersectionUtil.getDefaultIntersection(Location.of(0, 0), piece);
            Intersection destination = TestIntersectionUtil.getDefaultEmptyPieceIntersection(Location.of(0, 1));

            // when & then
            Assertions.assertThat(piece.calculateRoute(base, destination))
                    .isEqualTo(List.of(destination.getLocation()));
        }

        @Test
        @DisplayName("초팀 졸병이 이동할 수 있는 위치를 파라미터로 받으면 이동 경로를 반환한다.")
        void shouldReturnRouteForReachableLocationWhenTeamCho() {
            // given
            Piece piece = new Jolbyeong(Side.CHO);
            Intersection base = TestIntersectionUtil.getDefaultIntersection(Location.of(0, 1), piece);
            Intersection destination = TestIntersectionUtil.getDefaultEmptyPieceIntersection(Location.of(0, 0));

            // when & then
            Assertions.assertThat(piece.calculateRoute(base, destination))
                    .isEqualTo(List.of(destination.getLocation()));
        }

        @Test
        @DisplayName("한팀 졸병이 이동할 수 없는 위치를 파라미터로 받으면 예외가 발생한다.")
        void shouldThrowExceptionForUnReachableLocationWhenTeamHan() {
            // given
            Piece piece = new Jolbyeong(Side.HAN);
            Intersection base = TestIntersectionUtil.getDefaultIntersection(Location.of(5,5), piece);
            Intersection destination = TestIntersectionUtil.getDefaultEmptyPieceIntersection(Location.of(4,5));

            // when & then
            Assertions.assertThatThrownBy(() -> piece.calculateRoute(base, destination))
                    .isInstanceOf(RouteResolveException.class);
        }

        @ParameterizedTest
        @DisplayName("초팀 졸병이 이동할 수 없는 위치를 파라미터로 받으면 예외가 발생한다.")
        @MethodSource("provideUnreachableCoordination")
        void shouldThrowExceptionForUnReachableLocationWhenTeamCho(Location to) {
            // given
            Piece piece = new Jolbyeong(Side.CHO);
            Intersection base = TestIntersectionUtil.getDefaultIntersection(Location.of(5,5), piece);
            Intersection destination = TestIntersectionUtil.getDefaultEmptyPieceIntersection(to);

            // when & then
            Assertions.assertThatThrownBy(() -> piece.calculateRoute(base, destination))
                    .isInstanceOf(RouteResolveException.class);
        }
    }

    @Nested
    class GungSeqongCalculateRouteTest {

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

        @ParameterizedTest
        @DisplayName("차는 궁성 중앙에 있는 경우 궁성 내 모든 좌표로 이동할 수 있다.")
        @MethodSource("provideReachableCoordination")
        void returnRoute_WhenAtGungSeongCenter(Location to) {
            // given
            Piece piece = new Cha(Side.HAN);
            Intersection base = TestIntersectionUtil.getGungSeongCenterIntersection(Location.of(2, 5), piece);
            Intersection destination = TestIntersectionUtil.getDefaultEmptyPieceIntersection(to);

            List<Location> expected = List.of(to);

            // when & then
            Assertions.assertThat(piece.calculateRoute(base, destination))
                    .isEqualTo(expected);
        }

        @Test
        @DisplayName("차는 궁성 안에서 대각선 길이 있는 경우 대각선으로 이동할 수 있다.")
        void returnRoute_WhenMovingDiagonallyInGungSeong() {
            // given
            Piece piece = new Cha(Side.CHO);
            Intersection base = TestIntersectionUtil.getGungSeongLeftTopIntersection(Location.of(1, 4), piece);
            Intersection destination = TestIntersectionUtil.getDefaultEmptyPieceIntersection(Location.of(3, 6));

            List<Location> expected = List.of(Location.of(2, 5), Location.of(3, 6));

            // when & then
            Assertions.assertThat(piece.calculateRoute(base, destination))
                    .isEqualTo(expected);
        }

        @Test
        @DisplayName("차는 궁성 밖으로 대각선 이동을 시도하면 예외가 발생한다.")
        void throwException_WhenMovingDiagonallyOutOfGungSeong() {
            // given
            Piece piece = new Cha(Side.CHO);
            Intersection base = TestIntersectionUtil.getGungSeongLeftTopIntersection(Location.of(1, 4), piece);
            Intersection destination = TestIntersectionUtil.getDefaultEmptyPieceIntersection(Location.of(4, 7));

            // when & then
            Assertions.assertThatThrownBy(() -> piece.calculateRoute(base, destination))
                    .isInstanceOf(RouteResolveException.class);
        }

    }
}
