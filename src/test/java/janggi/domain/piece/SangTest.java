package janggi.domain.piece;

import janggi.domain.Location;
import janggi.domain.Side;
import janggi.exception.RouteResolveException;
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

        static List<List<Integer>> provideUnreachableCoordination() {
            return List.of(
                    List.of(0, 4),
                    List.of(-1, 3),
                    List.of(0, 1)
            );
        }

        @ParameterizedTest
        @DisplayName("상이 이동할 수 있는 위치를 파라미터로 받으면 이동 경로를 반환한다.")
        @MethodSource("provideListsForTesting")
        void shouldReturnRouteForReachableLocation(Location destination, List<Location> expected) {
            // given
            Location from = Location.of(0, 0);
            Piece piece = new Sang(Side.HAN);

            // when & then
            Assertions.assertThat(piece.calculateRoute(from, destination))
                    .isEqualTo(expected);
        }

        @ParameterizedTest
        @DisplayName("상이 이동할 수 없는 위치를 파라미터로 받으면 예외가 발생한다.")
        @MethodSource("provideUnreachableCoordination")
        void shouldThrowExceptionForUnReachableLocation(List<Integer> destination) {
            // given
            Location from = Location.of(0, 0);
            Location to = Location.from(destination);
            Piece piece = new Sang(Side.CHO);

            // when & then
            Assertions.assertThatThrownBy(() -> piece.calculateRoute(from, to))
                    .isInstanceOf(RouteResolveException.class);
        }
    }
}
