package janggiGame.piece;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

import janggiGame.Dot;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class HorseTest {
    public static Stream<Arguments> provideHorseOriginAndDestinationAndExpected() {
        return Stream.of(
                Arguments.of(Dot.of(5, 6), Dot.of(6, 8), List.of(Dot.of(5, 7))),
                Arguments.of(Dot.of(5, 6), Dot.of(4, 8), List.of(Dot.of(5, 7))),
                Arguments.of(Dot.of(5, 6), Dot.of(7, 7), List.of(Dot.of(6, 6))),
                Arguments.of(Dot.of(5, 6), Dot.of(7, 5), List.of(Dot.of(6, 6))),
                Arguments.of(Dot.of(5, 6), Dot.of(4, 4), List.of(Dot.of(5, 5)))
        );
    }

    @DisplayName("마는 목적지로 가는 경로를 구할 수 있다.")
    @ParameterizedTest
    @MethodSource("provideHorseOriginAndDestinationAndExpected")
    void horseCanGetIntermediatePoints(Dot origin, Dot destination, List<Dot> expected) {
        // given
        Horse horse = new Horse(Dynasty.HAN);

        // when
        List<Dot> actual = horse.getIntermediatePoints(origin, destination);

        // then
        assertThat(actual).isEqualTo(expected);
    }

    @DisplayName("마는 목적지로 갈 수 없다면 예외를 발생시킨다")
    @Test
    void horseCannotGetIntermediatePoints() {
        // given
        Dot origin = Dot.of(1, 1);
        Dot destination = Dot.of(3, 3);
        Horse horse = new Horse(Dynasty.HAN);

        // when // then
        assertThatCode(() -> horse.getIntermediatePoints(origin, destination))
                .isInstanceOf(UnsupportedOperationException.class)
                .hasMessageStartingWith("[ERROR]");
    }

    @DisplayName("마는 이동 경로에 어떤 말도 없다면 이동 가능하다")
    @Test
    void horseJudgeMovable() {
        // given
        Map<Dot, Piece> routesWithPiece = new LinkedHashMap<>();
        Horse horse = new Horse(Dynasty.HAN);

        routesWithPiece.put(Dot.of(5, 7), null);

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

        routesWithPiece.put(Dot.of(6, 8), new Horse(Dynasty.HAN));

        // when // then
        assertThatCode(() -> horse.validateMove(routesWithPiece, null))
                .isInstanceOf(UnsupportedOperationException.class)
                .hasMessageStartingWith("[ERROR]");
    }

}