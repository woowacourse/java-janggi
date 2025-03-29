package janggiGame.piece;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

import janggiGame.Position;
import janggiGame.piece.character.Dynasty;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class PawnTest {
    public static Stream<Arguments> providePawnAndOriginAndDestination() {
        return Stream.of(
                Arguments.of(new Pawn(Dynasty.HAN), Position.of(0, 5), Position.of(0, 6)),
                Arguments.of(new Pawn(Dynasty.CHO), Position.of(0, 3), Position.of(0, 2))
        );
    }

    private static Stream<Arguments> providePositionInPalaceDiagonal() {
        return Stream.of(
                Arguments.of(Position.of(3, 7), Position.of(4, 8)),
                Arguments.of(Position.of(3, 9), Position.of(4, 8)),
                Arguments.of(Position.of(5, 7), Position.of(4, 8)),
                Arguments.of(Position.of(5, 9), Position.of(4, 8)),

                Arguments.of(Position.of(4, 8), Position.of(3, 7)),
                Arguments.of(Position.of(4, 8), Position.of(3, 9)),
                Arguments.of(Position.of(4, 8), Position.of(5, 7)),
                Arguments.of(Position.of(4, 8), Position.of(5, 9))
        );
    }

    @DisplayName("병은 뒤로 이동할 수 없다.")
    @ParameterizedTest
    @MethodSource("providePawnAndOriginAndDestination")
    void pawnCannotMoveBack(Pawn pawn, Position origin, Position destination) {
        // when // then
        assertThatCode(() -> pawn.getIntermediatePoints(origin, destination))
                .isInstanceOf(UnsupportedOperationException.class)
                .hasMessageStartingWith("[ERROR] ");
    }

    @DisplayName("병의 목적지로 가는 경로는 항상 비어 있다.")
    @Test
    void pawnCanGetIntermediatePoints() {
        // given
        Position origin = Position.of(1, 1);
        Position destination = Position.of(1, 0);
        Pawn pawn = new Pawn(Dynasty.HAN);

        // when
        List<Position> actual = pawn.getIntermediatePoints(origin, destination);

        // then
        assertThat(actual).isEmpty();
    }

    @DisplayName("병은 목적지에 같은 나라의 기물이 존재 한다면 이동할 수 없다.")
    @Test
    void pawnCanNotMoveToSameDynastyPiece() {
        // given
        Map<Position, Piece> intermediatePointsWithPiece = new LinkedHashMap<>();
        Pawn pawn = new Pawn(Dynasty.HAN);

        // when // then
        assertThatCode(() -> pawn.validateMove(intermediatePointsWithPiece, new Chariot(Dynasty.HAN)))
                .isInstanceOf(UnsupportedOperationException.class)
                .hasMessageStartingWith("[ERROR] ");
    }

    @DisplayName("병은 목적지에 같은 나라의 기물이 존재하지 않는다면 이동할 수 있다.")
    @Test
    void pawnCanMove() {
        // given
        Map<Position, Piece> intermediatePointsWithPiece = new LinkedHashMap<>();
        Pawn pawn = new Pawn(Dynasty.HAN);

        // when // then
        assertThatCode(() -> pawn.validateMove(intermediatePointsWithPiece, new Advisor(Dynasty.CHO)))
                .doesNotThrowAnyException();
    }

    @DisplayName("병의 위치가 궁성 안 대각선의 위치인 경우, 대각선 이동이 가능하다.")
    @ParameterizedTest
    @MethodSource("providePositionInPalaceDiagonal")
    void pawnCanMoveDiagonalInPalaceDiagonal(Position origin, Position destination) {
        // given
        Pawn pawn = new Pawn(Dynasty.HAN);

        // when
        List<Position> actual = pawn.getIntermediatePoints(origin, destination);

        // then
        assertThat(actual).isEmpty();
    }

    @DisplayName("병은 같은 위치로 이동할 수 없다.")
    @Test
    void pawnCannotMoveToSamePosition_Test() {
        // given
        Pawn pawn = new Pawn(Dynasty.HAN);

        // when // then
        assertThatCode(() -> pawn.getIntermediatePoints(Position.of(1, 1), Position.of(1, 1)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageStartingWith("[ERROR] ");
    }
}