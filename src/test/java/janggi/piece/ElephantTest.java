package janggi.piece;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import janggi.board.Position;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

class ElephantTest {

    @ParameterizedTest
    @DisplayName("시작점과 끝점이 주어졌을 때, 이동 경로를 반환한다.")
    @MethodSource("pathArguments")
    void shouldReturnTrueWhenValidateMovingRule(Position end, List<Position> expected) {
        // given
        Elephant elephant = new Elephant(Side.RED);
        Position start = new Position(5, 5);

        // when
        List<Position> path = elephant.calculatePath(start, end);

        // then
        assertThat(path).containsExactlyElementsOf(expected);
    }

    private static Stream<Arguments> pathArguments() {
        return Stream.of(
                Arguments.of(
                        new Position(3, 8),
                        List.of(
                                new Position(5, 6),
                                new Position(4, 7)
                        )
                ),
                Arguments.of(
                        new Position(7, 8),
                        List.of(
                                new Position(5, 6),
                                new Position(6, 7)
                        )
                ),
                Arguments.of(
                        new Position(8, 7),
                        List.of(
                                new Position(6, 5),
                                new Position(7, 6)
                        )
                ),
                Arguments.of(
                        new Position(8, 3),
                        List.of(
                                new Position(6, 5),
                                new Position(7, 4)
                        )
                ),
                Arguments.of(
                        new Position(7, 2),
                        List.of(
                                new Position(5, 4),
                                new Position(6, 3)
                        )
                ),
                Arguments.of(
                        new Position(3, 2),
                        List.of(
                                new Position(5, 4),
                                new Position(4, 3)
                        )
                ),
                Arguments.of(
                        new Position(2, 3),
                        List.of(
                                new Position(4, 5),
                                new Position(3, 4)
                        )
                ),
                Arguments.of(
                        new Position(2, 7),
                        List.of(
                                new Position(4, 5),
                                new Position(3, 6)
                        )
                )
        );
    }

    @DisplayName("시작점, 끝점, 현재 장기말 위치들이 주어졌을 때, 이동 가능하면 true를 반환한다.")
    @ParameterizedTest
    @CsvSource(value = {
            "3, 8",
            "7, 8",
            "8, 7",
            "8, 3",
            "7, 2",
            "2, 7",
            "3, 2",
            "2, 3"
    })
    void shouldReturnTrueWhenCanMove(int destX, int destY) {
        // given
        Elephant elephant = new Elephant(Side.RED);
        Position start = new Position(5, 5);
        Position end = new Position(destX, destY);

        // when
        boolean canMove = elephant.canMove(start, end, Map.of());

        // then
        assertThat(canMove).isTrue();
    }

    @DisplayName("시작점, 끝점, 현재 장기말 위치들이 주어졌을 때, 경로 상에 말이 존재하면 false를 반환한다.")
    @Test
    void shouldReturnFalseWhenPieceOnPath() {
        // given
        Elephant elephant = new Elephant(Side.RED);
        Position start = new Position(5, 5);
        Position end = new Position(8, 5);

        // when
        boolean canMove = elephant.canMove(start, end, Map.of(new Position(7,5), new Tank(Side.BLUE)));

        // then
        assertThat(canMove).isFalse();
    }

    @DisplayName("시작점, 끝점, 현재 장기말 위치들이 주어졌을 때, 이동 규칙과 다르면 false를 반환한다.")
    @ParameterizedTest
    @CsvSource(value = {
            "6, 6",
            "4, 6",
            "6, 4",
            "4, 4"
    })
    void shouldReturnFalseWhenInvalidMovingRule(int destX, int destY) {
        // given
        Elephant elephant = new Elephant(Side.RED);
        Position start = new Position(5, 5);
        Position end = new Position(destX, destY);

        // when
        boolean canMove = elephant.canMove(start, end, Map.of());

        // then
        assertThat(canMove).isFalse();
    }


    @ParameterizedTest
    @DisplayName("말의 이동 규칙이 어긋나면 예외를 발생한다.")
    @CsvSource(value = {
            "3, 5",
            "7, 5",
            "5, 7",
            "5, 3",
            "4, 6",
            "6, 4",
            "4, 4",
            "6, 6"
    })
    void shouldReturnTrueWhenUnfollowMovingRule(int destX, int destY) {
        // given
        Elephant elephant = new Elephant(Side.RED);
        Position start = new Position(5, 5);
        Position end = new Position(destX, destY);

        // when
        assertThatThrownBy(() -> elephant.calculatePath(start, end))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
