package janggiGame.piece;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

import janggiGame.piece.oneMovePiece.Pawn;
import janggiGame.piece.straightMovePiece.Chariot;
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

class PawnTest {
    @DisplayName("병의 목적지로 가는 경로는 항상 비어있다.")
    @Test
    void pawnCanGetRoute() {
        // given
        Position origin = Position.getInstanceBy(1, 1);
        Position destination = Position.getInstanceBy(1, 0);
        Pawn pawn = new Pawn(Dynasty.HAN);

        // when
        List<Position> actual = pawn.getRoute(origin, destination);

        // then
        assertThat(actual).isEmpty();
    }

    @DisplayName("병은 뒤로 이동할 수 없다.")
    @ParameterizedTest
    @MethodSource("providePawnAndOriginAndDestination")
    void pawnCannotMoveBack(Pawn pawn, Position origin, Position destination) {
        // when // then
        assertThatCode(() -> pawn.getRoute(origin, destination))
                .isInstanceOf(UnsupportedOperationException.class)
                .hasMessageStartingWith("[ERROR]");
    }

    public static Stream<Arguments> providePawnAndOriginAndDestination() {
        return Stream.of(
                Arguments.of(new Pawn(Dynasty.HAN), Position.getInstanceBy(0, 5), Position.getInstanceBy(0, 6)),
                Arguments.of(new Pawn(Dynasty.CHO), Position.getInstanceBy(0, 3), Position.getInstanceBy(0, 2))
        );
    }

    @DisplayName("병은 목적지에 같은 나라의 기물이 존재한다면 이동할 수 없다")
    @Test
    void pawnJudgeMovable3() {
        // given
        Map<Position, Piece> routesWithPiece = new HashMap<>();
        Pawn pawn = new Pawn(Dynasty.HAN);

        // when // then
        assertThatCode(() -> pawn.validateMove(routesWithPiece, new Chariot(Dynasty.HAN)))
                .isInstanceOf(UnsupportedOperationException.class)
                .hasMessageStartingWith("[ERROR] ");
    }

    @DisplayName("병은 궁성 안에서 중심을 포함한 대각선 이동이 가능하다")
    @Test
    void canMoveDiagonalThroughCenter() {
        // given
        Pawn pawn = new Pawn(Dynasty.HAN);
        Position origin = Position.getInstanceBy(4, 1);
        Position destination = Position.getInstanceBy(3, 0);

        // when // then
        assertThatCode(() -> pawn.getRoute(origin, destination))
                .doesNotThrowAnyException();
    }

    @DisplayName("병은 궁성 안에서 중심을 포함한 대각선 이동이라도 뒤로는 이동할 수 없다.")
    @Test
    void cannotBackDiagonalThroughCenter() {
        // given
        Pawn pawn = new Pawn(Dynasty.HAN);
        Position origin = Position.getInstanceBy(3, 0);
        Position destination = Position.getInstanceBy(4, 1);

        // when // then
        assertThatCode(() -> pawn.getRoute(origin, destination))
                .isInstanceOf(UnsupportedOperationException.class)
                .hasMessageStartingWith("[ERROR]");
    }

}
