package janggiGame.piece;

import janggiGame.board.Board;
import janggiGame.board.Dot;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

class HorseTest {
    public static Stream<Arguments> provideHorseOriginAndDestinationAndExpected() {
        return Stream.of(
                Arguments.of(Board.findBy(5, 6), Board.findBy(6, 8), List.of(Board.findBy(5, 7))),
                Arguments.of(Board.findBy(5, 6), Board.findBy(4, 8), List.of(Board.findBy(5, 7))),
                Arguments.of(Board.findBy(5, 6), Board.findBy(7, 7), List.of(Board.findBy(6, 6))),
                Arguments.of(Board.findBy(5, 6), Board.findBy(7, 5), List.of(Board.findBy(6, 6))),
                Arguments.of(Board.findBy(5, 6), Board.findBy(4, 4), List.of(Board.findBy(5, 5)))
        );
    }

    @DisplayName("마는 목적지로 가는 경로를 구할 수 있다.")
    @ParameterizedTest
    @MethodSource("provideHorseOriginAndDestinationAndExpected")
    void horseCanGetRoute(Dot origin, Dot destination, List<Dot> expected) {
        // given
        Horse horse = new Horse(Dynasty.HAN);

        // when
        List<Dot> actual = horse.getRoute(origin, destination);

        // then
        assertThat(actual).isEqualTo(expected);
    }

    @DisplayName("마는 목적지로 갈 수 없다면 예외를 발생시킨다")
    @Test
    void horseCannotGetRoute() {
        // given
        Dot origin = Board.findBy(1, 1);
        Dot destination = Board.findBy(3, 3);
        Horse horse = new Horse(Dynasty.HAN);

        // when // then
        assertThatCode(() -> horse.getRoute(origin, destination))
                .isInstanceOf(UnsupportedOperationException.class)
                .hasMessageStartingWith("[ERROR]");
    }

    @DisplayName("마는 이동 경로에 어떤 말도 없다면 이동 가능하다")
    @Test
    void horseJudgeMovable() {
        // given
        Map<Dot, Piece> routesWithPiece = new LinkedHashMap<>();
        Horse horse = new Horse(Dynasty.HAN);

        routesWithPiece.put(Board.findBy(5, 7), null);

        // when // then
        assertThatCode(() -> horse.validateMove(routesWithPiece, null))
                .doesNotThrowAnyException();
    }

    @DisplayName("마는 이동 경로에 기물이 존재한다면 이동할 수 없다")
    @Test
    void horseJudgeMovable2() {
        // given
        Map<Dot, Piece> routesWithPiece = new LinkedHashMap<>();
        Horse horse = new Horse(Dynasty.HAN);

        routesWithPiece.put(Board.findBy(6, 8), new Horse(Dynasty.HAN));

        // when // then
        assertThatCode(() -> horse.validateMove(routesWithPiece, null))
                .isInstanceOf(UnsupportedOperationException.class)
                .hasMessageStartingWith("[ERROR]");
    }

}