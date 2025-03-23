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

class ElephantTest {
    public static Stream<Arguments> provideElephantOriginAndDestinationAndExpected() {
        return Stream.of(
                Arguments.of(Dot.of(5, 6), Dot.of(7, 9), List.of(Dot.of(5, 7), Dot.of(6, 8))),
                Arguments.of(Dot.of(5, 6), Dot.of(3, 9), List.of(Dot.of(5, 7), Dot.of(4, 8))),
                Arguments.of(Dot.of(5, 6), Dot.of(8, 8), List.of(Dot.of(6, 6), Dot.of(7, 7))),
                Arguments.of(Dot.of(5, 6), Dot.of(8, 4), List.of(Dot.of(6, 6), Dot.of(7, 5))),
                Arguments.of(Dot.of(5, 6), Dot.of(3, 3), List.of(Dot.of(5, 5), Dot.of(4, 4)))

        );
    }

    @DisplayName("상은 목적지로 가는 경로를 구할 수 있다.")
    @ParameterizedTest
    @MethodSource("provideElephantOriginAndDestinationAndExpected")
    void elephantCanGetIntermediatePoints(Dot origin, Dot destination, List<Dot> expected) {
        // given
        Elephant elephant = new Elephant(Dynasty.HAN);

        // when
        List<Dot> actual = elephant.getIntermediatePoints(origin, destination);

        // then
        assertThat(actual).isEqualTo(expected);
    }

    @DisplayName("상이 목적지로 갈 수 없다면 예외를 발생시킨다")
    @Test
    void elephantCannotGetIntermediatePoints() {
        // given
        Dot origin = Dot.of(1, 1);
        Dot destination = Dot.of(3, 3);
        Elephant elephant = new Elephant(Dynasty.HAN);

        // when // then
        assertThatCode(() -> elephant.getIntermediatePoints(origin, destination))
                .isInstanceOf(UnsupportedOperationException.class)
                .hasMessageStartingWith("[ERROR]");
    }


    @DisplayName("상은 이동 경로에 어떤 말도 없다면 이동 가능하다")
    @Test
    void elephantJudgeMovable() {
        // given
        Map<Dot, Piece> routesWithPiece = new LinkedHashMap<>();
        Elephant elephant = new Elephant(Dynasty.HAN);

        routesWithPiece.put(Dot.of(5, 7), null);
        routesWithPiece.put(Dot.of(6, 8), null);

        // when // then
        assertThatCode(() -> elephant.validateMove(routesWithPiece, null))
                .doesNotThrowAnyException();
    }

    @DisplayName("상은 이동 경로에 기물이 존재한다면 이동할 수 없다")
    @Test
    void elephantJudgeMovable2() {
        // given
        Map<Dot, Piece> routesWithPiece = new LinkedHashMap<>();
        Elephant elephant = new Elephant(Dynasty.HAN);

        routesWithPiece.put(Dot.of(5, 7), null);
        routesWithPiece.put(Dot.of(6, 8), new Elephant(Dynasty.HAN));

        // when // then
        assertThatCode(() -> elephant.validateMove(routesWithPiece, null))
                .isInstanceOf(UnsupportedOperationException.class)
                .hasMessageStartingWith("[ERROR]");
    }
}