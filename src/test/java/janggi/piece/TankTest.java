package janggi.piece;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import janggi.Position;
import janggi.Side;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

class TankTest {

    @ParameterizedTest
    @DisplayName("시작점과 끝점이 주어졌을 때, 이동 경로를 반환한다.")
    @MethodSource("pathArguments")
    void shouldReturnFalseWhenRouteExists(Position end, List<Position> expectedPath) {
        // given
        Piece tank = new Tank(Side.RED);
        Position start = new Position(5, 5);

        // when
        List<Position> path = tank.calculatePath(start, end);

        // then
        assertThat(path).containsExactlyElementsOf(expectedPath);
    }

    @ParameterizedTest
    @DisplayName("말의 이동 규칙이 어긋나면 예외를 발생한다.")
    @CsvSource(value = {
            "6, 6",
            "4, 6",
            "6, 4",
            "4, 4"
    })
    void shouldReturnTrueWhenUnfollowMovingRule(int destX, int destY) {
        // given
        Tank tank = new Tank(Side.RED);
        Position start = new Position(5, 5);
        Position end = new Position(destX, destY);

        // when
        assertThatThrownBy(() -> tank.calculatePath(start, end))
                .isInstanceOf(IllegalArgumentException.class);
    }

    private static Stream<Arguments> pathArguments() {
        return Stream.of(
                Arguments.of(
                        new Position(8, 5),
                        List.of(
                                new Position(6, 5),
                                new Position(7, 5)
                        )
                ),
                Arguments.of(
                        new Position(2, 5),
                        List.of(
                                new Position(4, 5),
                                new Position(3, 5)
                        )
                ),
                Arguments.of(
                        new Position(5, 8),
                        List.of(
                                new Position(5, 6),
                                new Position(5, 7)
                        )
                ),
                Arguments.of(
                        new Position(5, 2),
                        List.of(
                                new Position(5, 4),
                                new Position(5, 3)
                        )
                )
        );
    }
}
