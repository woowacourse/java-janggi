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

class AdvisorTest {
    private static Stream<Arguments> provideRowAndColumn() {
        return Stream.of(
                Arguments.of(3, 2),
                Arguments.of(4, 2),
                Arguments.of(5, 2),
                Arguments.of(5, 1),
                Arguments.of(5, 0)
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

    private static Stream<Arguments> providePositionOutOfPalace() {
        return Stream.of(
                Arguments.of(Position.of(3, 7), Position.of(3, 6)),
                Arguments.of(Position.of(3, 9), Position.of(2, 9)),
                Arguments.of(Position.of(5, 7), Position.of(5, 6))
        );
    }

    @DisplayName("사가 목적지로 갈 수 없다면 예외를 발생 시킨다")
    @ParameterizedTest
    @MethodSource("provideRowAndColumn")
    void advisorCanValidateDestination(int row, int column) {
        // given
        Position origin = Position.of(3, 0);
        Position destination = Position.of(row, column);
        Advisor advisor = new Advisor(Dynasty.HAN);

        // when // then
        assertThatCode(() -> advisor.getIntermediatePoints(origin, destination))
                .isInstanceOf(UnsupportedOperationException.class)
                .hasMessageStartingWith("[ERROR] ");
    }

    @DisplayName("사의 목적지로 가는 경로는 항상 비어 있다.")
    @Test
    void advisorHasEmptyIntermediatePoints() {
        // given
        Position origin = Position.of(3, 1);
        Position destination = Position.of(4, 1);
        Advisor advisor = new Advisor(Dynasty.HAN);

        // when
        List<Position> actual = advisor.getIntermediatePoints(origin, destination);

        // then
        assertThat(actual).isEmpty();
    }

    @DisplayName("사는 목적지에 같은 나라의 기물이 존재 한다면 이동할 수 없다")
    @Test
    void advisorCanNotMoveToSameDynastyPiece() {
        // given
        Map<Position, Piece> intermediatePointsWithPiece = new LinkedHashMap<>();
        Advisor advisor = new Advisor(Dynasty.HAN);

        // when // then
        assertThatCode(() -> advisor.validateMove(intermediatePointsWithPiece, new Chariot(Dynasty.HAN)))
                .isInstanceOf(UnsupportedOperationException.class)
                .hasMessageStartingWith("[ERROR] ");
    }

    @DisplayName("사는 목적지에 같은 나라의 기물이 존재하지 않는다면 이동할 수 있다.")
    @Test
    void advisorCanMove() {
        // given
        Map<Position, Piece> intermediatePointsWithPiece = new LinkedHashMap<>();
        Advisor advisor = new Advisor(Dynasty.HAN);

        // when // then
        assertThatCode(() -> advisor.validateMove(intermediatePointsWithPiece, new Advisor(Dynasty.CHO)))
                .doesNotThrowAnyException();
    }

    @DisplayName("사의 위치가 궁성 안 대각선의 위치인 경우, 대각선 이동이 가능하다.")
    @ParameterizedTest
    @MethodSource("providePositionInPalaceDiagonal")
    void advisorCanMoveDiagonalInPalaceDiagonal(Position origin, Position destination) {
        // given
        Advisor advisor = new Advisor(Dynasty.HAN);

        // when
        List<Position> actual = advisor.getIntermediatePoints(origin, destination);

        // then
        assertThat(actual).isEmpty();
    }

    @DisplayName("사는 같은 위치로 이동할 수 없다.")
    @Test
    void advisorCannotMoveToSamePosition_Test() {
        // given
        Advisor advisor = new Advisor(Dynasty.HAN);

        // when // then
        assertThatCode(() -> advisor.getIntermediatePoints(Position.of(4, 8), Position.of(4, 8)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageStartingWith("[ERROR] ");
    }

    @DisplayName("사는 궁 밖으로 움직일 수 없다.")
    @ParameterizedTest
    @MethodSource("providePositionOutOfPalace")
    void advisorCanMoveInPalace_Test(Position origin, Position destination) {
        // given
        Advisor advisor = new Advisor(Dynasty.HAN);

        // when // then
        assertThatCode(() -> advisor.getIntermediatePoints(origin, destination))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageStartingWith("[ERROR] ");
    }
}