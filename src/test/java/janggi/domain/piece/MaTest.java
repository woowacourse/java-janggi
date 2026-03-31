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

public class MaTest {

    @Nested
    class CalculateRouteTest {

        static List<List<Integer>> provideUnreachableCoordination() {
            return List.of(
                    List.of(0, 3),
                    List.of(-1, 3),
                    List.of(0, 1)
            );
        }

        @Test
        @DisplayName("마가 이동할 수 있는 위치를 파라미터로 받으면 이동 경로를 반환한다.")
        void shouldReturnRouteForReachableLocation() {
            // given
            Location from = Location.from(List.of(0, 0));
            Location to = Location.from(List.of(1, 2));
            Piece piece = new Ma(Side.HAN);

            List<Location> expected = List.of(
                    Location.from(List.of(0, 1)),
                    Location.from(List.of(1, 2))
            );

            // when & then
            Assertions.assertThat(piece.calculateRoute(from, to))
                    .isEqualTo(expected);
        }

        @ParameterizedTest
        @DisplayName("마가 이동할 수 없는 위치를 파라미터로 받으면 예외가 발생한다.")
        @MethodSource("provideUnreachableCoordination")
        void shouldThrowExceptionForUnReachableLocation(List<Integer> destination) {
            // given
            Location from = Location.from(List.of(0, 0));
            Location to = Location.from(destination);
            Piece piece = new Ma(Side.CHO);

            // when & then
            Assertions.assertThatThrownBy(() -> piece.calculateRoute(from, to))
                    .isInstanceOf(RouteResolveException.class);
        }
    }
}
