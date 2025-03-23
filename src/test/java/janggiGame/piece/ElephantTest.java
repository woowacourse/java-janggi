package janggiGame.piece;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

import janggiGame.board.Dot;
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
                Arguments.of(Dot.findBy(5, 6), Dot.findBy(7, 9), List.of(Dot.findBy(5, 7), Dot.findBy(6, 8))),
                Arguments.of(Dot.findBy(5, 6), Dot.findBy(3, 9), List.of(Dot.findBy(5, 7), Dot.findBy(4, 8))),
                Arguments.of(Dot.findBy(5, 6), Dot.findBy(8, 8), List.of(Dot.findBy(6, 6), Dot.findBy(7, 7))),
                Arguments.of(Dot.findBy(5, 6), Dot.findBy(8, 4), List.of(Dot.findBy(6, 6), Dot.findBy(7, 5))),
                Arguments.of(Dot.findBy(5, 6), Dot.findBy(3, 3), List.of(Dot.findBy(5, 5), Dot.findBy(4, 4)))

        );
    }

    @DisplayName("상은 목적지로 가는 경로를 구할 수 있다.")
    @ParameterizedTest
    @MethodSource("provideElephantOriginAndDestinationAndExpected")
    void elephantCanGetRoute(Dot origin, Dot destination, List<Dot> expected) {
        // given
        Elephant elephant = new Elephant(Dynasty.HAN);

        // when
        List<Dot> actual = elephant.getRoute(origin, destination);

        // then
        assertThat(actual).isEqualTo(expected);
    }

    @DisplayName("상이 목적지로 갈 수 없다면 예외를 발생시킨다")
    @Test
    void elephantCannotGetRoute() {
        // given
        Dot origin = Dot.findBy(1, 1);
        Dot destination = Dot.findBy(3, 3);
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
        Map<Dot, Piece> routesWithPiece = new LinkedHashMap<>();
        Elephant elephant = new Elephant(Dynasty.HAN);

        routesWithPiece.put(Dot.findBy(5, 7), null);
        routesWithPiece.put(Dot.findBy(6, 8), null);

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

        routesWithPiece.put(Dot.findBy(5, 7), null);
        routesWithPiece.put(Dot.findBy(6, 8), new Elephant(Dynasty.HAN));

        // when // then
        assertThatCode(() -> elephant.validateMove(routesWithPiece, null))
                .isInstanceOf(UnsupportedOperationException.class)
                .hasMessageStartingWith("[ERROR]");
    }
}