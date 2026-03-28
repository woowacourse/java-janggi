package janggi.domain.piece;

import janggi.domain.Location;
import janggi.domain.Side;
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
        static List<List<Integer>> provideUnreachableCoordination() {
            return List.of(
                    List.of(0, 2), // 거리가 멀어서 도달할 수 없는 경우
                    List.of(1, 1), // 대각선으로 이동하는 경우
                    List.of(0, 1) // 뒤로 이동하는 경우
            );
        }

        @Test
        @DisplayName("한팀 졸병이 이동할 수 있는 위치를 파라미터로 받으면 이동 경로를 반환한다.")
        void shouldReturnRouteForReachableLocationWhenTeamHan() {
            // given
            Location from = Location.from(List.of(0, 0));
            Location to = Location.from(List.of(0, 1));
            Piece piece = new Jolbyeong(Side.HAN);

            // when & then
            Assertions.assertThat(piece.calculateRoute(from, to))
                    .isEqualTo(List.of(to));
        }

        @Test
        @DisplayName("초팀 졸병이 이동할 수 있는 위치를 파라미터로 받으면 이동 경로를 반환한다.")
        void shouldReturnRouteForReachableLocationWhenTeamCho() {
            // given
            Location from = Location.from(List.of(0, 1));
            Location to = Location.from(List.of(0, 0));
            Piece piece = new Jolbyeong(Side.CHO);

            // when & then
            Assertions.assertThat(piece.calculateRoute(from, to))
                    .isEqualTo(List.of(to));
        }

        @Test
        @DisplayName("한팀 졸병이 이동할 수 없는 위치를 파라미터로 받으면 예외가 발생한다.")
        void shouldThrowExceptionForUnReachableLocationWhenTeamHan() {
            // given
            Location from = Location.from(List.of(0, 1));
            Location to = Location.from(List.of(0, 0));
            Piece piece = new Jolbyeong(Side.HAN);

            // when & then
            Assertions.assertThatThrownBy(() -> piece.calculateRoute(from, to))
                    .isInstanceOf(IllegalArgumentException.class);
        }

        @ParameterizedTest
        @DisplayName("초팀 졸병이 이동할 수 없는 위치를 파라미터로 받으면 예외가 발생한다.")
        @MethodSource("provideUnreachableCoordination")
        void shouldThrowExceptionForUnReachableLocationWhenTeamCho(List<Integer> coordination) {
            // given
            Location from = Location.from(List.of(0, 0));
            Location to = Location.from(coordination);
            Piece piece = new Jolbyeong(Side.CHO);

            // when & then
            Assertions.assertThatThrownBy(() -> piece.calculateRoute(from, to))
                    .isInstanceOf(IllegalArgumentException.class);
        }
    }
}
