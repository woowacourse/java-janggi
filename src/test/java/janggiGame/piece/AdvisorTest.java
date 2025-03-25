package janggiGame.piece;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

import janggiGame.Position;
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
                Arguments.of(1, 1),
                Arguments.of(5, 5),
                Arguments.of(1, 3),
                Arguments.of(3, 1),
                Arguments.of(2, 5),
                Arguments.of(5, 2)
        );
    }

    @DisplayName("사가 목적지로 갈 수 없다면 예외를 발생 시킨다")
    @ParameterizedTest
    @MethodSource("provideRowAndColumn")
    void advisorCanValidateDestination(int row, int column) {
        // given
        Position origin = Position.of(3, 3);
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
        Position origin = Position.of(1, 1);
        Position destination = Position.of(1, 0);
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
}