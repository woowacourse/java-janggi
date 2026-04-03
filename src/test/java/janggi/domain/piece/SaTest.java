package janggi.domain.piece;

import janggi.domain.Location;
import janggi.domain.Side;
import janggi.exception.RouteResolveException;
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

        static List<List<Integer>> provideUnreachableCoordination() {
            return List.of(
                    List.of(1, 1),
                    List.of(4, 5),
                    List.of(2, 7)
            );
        }

        @Test
        @DisplayName("사가 이동할 수 있는 위치를 파라미터로 받으면 이동 경로를 반환한다.")
        void shouldReturnRouteForReachableLocation() {
            // given
            Location from = Location.from(List.of(2, 5));
            Location to = Location.from(List.of(1, 6));
            Piece piece = new Sa(Side.HAN);

            List<Location> expected = List.of(
                    Location.from(List.of(1, 6))
            );

            // when & then
            Assertions.assertThat(piece.calculateRoute(from, to))
                    .isEqualTo(expected);
        }

        @ParameterizedTest
        @DisplayName("사가 이동할 수 없는 위치를 파라미터로 받으면 예외가 발생한다.")
        @MethodSource("provideUnreachableCoordination")
        void shouldThrowExceptionForUnReachableLocation(List<Integer> destination) {
            // given
            Location from = Location.from(List.of(2, 5));
            Location to = Location.from(destination);
            Piece piece = new Sa(Side.CHO);

            // when & then
            Assertions.assertThatThrownBy(() -> piece.calculateRoute(from, to))
                    .isInstanceOf(RouteResolveException.class);
        }
    }
}
