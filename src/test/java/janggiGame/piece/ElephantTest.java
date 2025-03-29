package janggiGame.piece;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

import janggiGame.piece.curveMovePiece.Elephant;
import janggiGame.position.Position;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class ElephantTest {
    @DisplayName("상은 목적지로 가는 경로를 구할 수 있다.")
    @ParameterizedTest
    @MethodSource("provideElephantOriginAndDestinationAndExpected")
    void elephantCanGetRoute(Position origin, Position destination, List<Position> expected) {
        // given
        Elephant elephant = new Elephant(Dynasty.HAN);

        // when
        List<Position> actual = elephant.getRoute(origin, destination);

        // then
        assertThat(actual).isEqualTo(expected);
    }

    public static Stream<Arguments> provideElephantOriginAndDestinationAndExpected() {
        return Stream.of(
                Arguments.of(
                        Position.getInstanceBy(5, 6), Position.getInstanceBy(7, 9),
                        List.of(Position.getInstanceBy(5, 7), Position.getInstanceBy(6, 8))),
                Arguments.of(
                        Position.getInstanceBy(5, 6), Position.getInstanceBy(3, 9),
                        List.of(Position.getInstanceBy(5, 7), Position.getInstanceBy(4, 8))),
                Arguments.of(
                        Position.getInstanceBy(5, 6), Position.getInstanceBy(8, 8),
                        List.of(Position.getInstanceBy(6, 6), Position.getInstanceBy(7, 7))),
                Arguments.of(
                        Position.getInstanceBy(5, 6), Position.getInstanceBy(8, 4),
                        List.of(Position.getInstanceBy(6, 6), Position.getInstanceBy(7, 5))),
                Arguments.of(
                        Position.getInstanceBy(5, 6), Position.getInstanceBy(3, 3),
                        List.of(Position.getInstanceBy(5, 5), Position.getInstanceBy(4, 4)))

        );
    }

    @DisplayName("상이 목적지로 갈 수 없다면 예외를 발생시킨다")
    @Test
    void elephantCannotGetRoute() {
        // given
        Position origin = Position.getInstanceBy(1, 1);
        Position destination = Position.getInstanceBy(3, 3);
        Elephant elephant = new Elephant(Dynasty.HAN);

        // when // then
        assertThatCode(() -> elephant.getRoute(origin, destination))
                .isInstanceOf(UnsupportedOperationException.class)
                .hasMessageStartingWith("[ERROR]");
    }


    @DisplayName("상은 이동 경로에 어떤 말도 없다면 이동 가능하다")
    @Test
    void elephantJudgeMovable() {
        // given
        Map<Position, Piece> routesWithPiece = new HashMap<>();
        Elephant elephant = new Elephant(Dynasty.HAN);

        routesWithPiece.put(Position.getInstanceBy(5, 7), null);
        routesWithPiece.put(Position.getInstanceBy(6, 8), null);

        // when // then
        assertThatCode(() -> elephant.validateMove(routesWithPiece, null))
                .doesNotThrowAnyException();
    }

    @DisplayName("상은 이동 경로에 기물이 존재한다면 이동할 수 없다")
    @Test
    void elephantJudgeMovable2() {
        // given
        Map<Position, Piece> routesWithPiece = new HashMap<>();
        Elephant elephant = new Elephant(Dynasty.HAN);

        routesWithPiece.put(Position.getInstanceBy(5, 7), null);
        routesWithPiece.put(Position.getInstanceBy(6, 8), new Elephant(Dynasty.HAN));

        // when // then
        assertThatCode(() -> elephant.validateMove(routesWithPiece, null))
                .isInstanceOf(UnsupportedOperationException.class)
                .hasMessageStartingWith("[ERROR]");
    }
}
