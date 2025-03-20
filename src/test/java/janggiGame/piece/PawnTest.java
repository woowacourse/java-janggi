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

class PawnTest {
    public static Stream<Arguments> providePawnAndOriginAndDestination() {
        return Stream.of(
                Arguments.of(new Pawn(Dynasty.HAN), Board.findBy(0, 5), Board.findBy(0, 6)),
                Arguments.of(new Pawn(Dynasty.CHO), Board.findBy(0, 3), Board.findBy(0, 2))
        );
    }

    @DisplayName("병의 목적지로 가는 경로는 항상 비어있다.")
    @Test
    void pawnCanGetRoute() {
        // given
        Dot origin = Board.findBy(1, 1);
        Dot destination = Board.findBy(1, 0);
        Pawn pawn = new Pawn(Dynasty.HAN);

        // when
        List<Dot> actual = pawn.getRoute(origin, destination);

        // then
        assertThat(actual).isEmpty();
    }

    @DisplayName("병은 뒤로 이동할 수 없다.")
    @ParameterizedTest
    @MethodSource("providePawnAndOriginAndDestination")
    void pawnCannotMoveBack(Pawn pawn, Dot origin, Dot destination) {
        // when // then
        assertThatCode(() -> pawn.getRoute(origin, destination))
                .isInstanceOf(UnsupportedOperationException.class)
                .hasMessageStartingWith("[ERROR]");
    }

    @DisplayName("병은 목적지에 같은 나라의 기물이 존재한다면 이동할 수 없다")
    @Test
    void pawnJudgeMovable3() {
        // given
        Map<Dot, Piece> routesWithPiece = new LinkedHashMap<>();
        Pawn pawn = new Pawn(Dynasty.HAN);

        // when // then
        assertThatCode(() -> pawn.validateMove(routesWithPiece, new Chariot(Dynasty.HAN)))
                .isInstanceOf(UnsupportedOperationException.class)
                .hasMessageStartingWith("[ERROR] ");
    }


}