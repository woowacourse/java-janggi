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

class ChariotTest {
    private static Stream<Arguments> provideRowAndColumn() {
        return Stream.of(
                Arguments.of(3, 3),
                Arguments.of(1, 1),
                Arguments.of(1, 3),
                Arguments.of(3, 1)
        );
    }

    private static Stream<Arguments> providePositionInPalaceDiagonal() {
        return Stream.of(
                Arguments.of(Position.of(3, 7), Position.of(5, 9)),
                Arguments.of(Position.of(3, 9), Position.of(5, 7)),
                Arguments.of(Position.of(5, 7), Position.of(3, 9)),
                Arguments.of(Position.of(5, 9), Position.of(3, 7)),

                Arguments.of(Position.of(4, 8), Position.of(3, 7)),
                Arguments.of(Position.of(4, 8), Position.of(3, 9)),
                Arguments.of(Position.of(4, 8), Position.of(5, 7)),
                Arguments.of(Position.of(4, 8), Position.of(5, 9))

        );
    }

    @DisplayName("차가 목적지로 갈 수 없다면 예외를 발생 시킨다")
    @ParameterizedTest
    @MethodSource("provideRowAndColumn")
    void chariotCanValidateDestination(int row, int column) {
        // given
        Position origin = Position.of(2, 2);
        Position destination = Position.of(row, column);
        Chariot chariot = new Chariot(Dynasty.HAN);

        // when // then
        assertThatCode(() -> chariot.getIntermediatePoints(origin, destination))
                .isInstanceOf(UnsupportedOperationException.class)
                .hasMessageStartingWith("[ERROR] ");
    }

    @DisplayName("차는 목적지로 가는 경로를 구할 수 있다.")
    @Test
    void chariotCanGetIntermediatePoints() {
        // given
        Position origin = Position.of(1, 1);
        Position destination = Position.of(1, 3);
        Chariot chariot = new Chariot(Dynasty.HAN);

        // when
        List<Position> actual = chariot.getIntermediatePoints(origin, destination);

        List<Position> expected = List.of(Position.of(1, 2));

        // then
        assertThat(actual).isEqualTo(expected);
    }

    @DisplayName("차는 이동 경로에 기물이 존재 한다면 이동할 수 없다")
    @Test
    void chariotCanNotMoveIfIntermediatePointsHasPiece() {
        // given
        Map<Position, Piece> intermediatePointsWithPiece = new LinkedHashMap<>();
        Chariot chariot = new Chariot(Dynasty.HAN);

        intermediatePointsWithPiece.put(Position.of(1, 2), new EmptyPiece());
        intermediatePointsWithPiece.put(Position.of(1, 3), new Chariot(Dynasty.HAN));

        // when // then
        assertThatCode(() -> chariot.validateMove(intermediatePointsWithPiece, new EmptyPiece()))
                .isInstanceOf(UnsupportedOperationException.class)
                .hasMessageStartingWith("[ERROR] ");
    }

    @DisplayName("차는 목적지에 같은 나라의 기물이 존재 한다면 이동할 수 없다")
    @Test
    void chariotCanNotMoveToSameDynastyPiece() {
        // given
        Map<Position, Piece> intermediatePointsWithPiece = new LinkedHashMap<>();
        Chariot chariot = new Chariot(Dynasty.HAN);

        intermediatePointsWithPiece.put(Position.of(1, 2), new EmptyPiece());
        intermediatePointsWithPiece.put(Position.of(1, 3), new EmptyPiece());

        // when // then
        assertThatCode(() -> chariot.validateMove(intermediatePointsWithPiece, new Chariot(Dynasty.HAN)))
                .isInstanceOf(UnsupportedOperationException.class)
                .hasMessageStartingWith("[ERROR] ");
    }

    @DisplayName("차는 이동 경로에 어떤 말도 없다면 이동 가능 하다")
    @Test
    void chariotCanMove() {
        // given
        Map<Position, Piece> intermediatePointsWithPiece = new LinkedHashMap<>();
        Chariot chariot = new Chariot(Dynasty.HAN);

        intermediatePointsWithPiece.put(Position.of(1, 2), new EmptyPiece());
        intermediatePointsWithPiece.put(Position.of(1, 3), new EmptyPiece());

        // when // then
        assertThatCode(() -> chariot.validateMove(intermediatePointsWithPiece, new EmptyPiece()))
                .doesNotThrowAnyException();
    }

    @DisplayName("치의 위치가 궁성 안 대각선의 위치인 경우, 대각선 이동이 가능하다.")
    @ParameterizedTest
    @MethodSource("providePositionInPalaceDiagonal")
    void chariotCanMoveDiagonalInPalaceDiagonal(Position origin, Position destination) {
        // given
        Chariot chariot = new Chariot(Dynasty.HAN);

        // when // then
        assertThatCode(() -> chariot.getIntermediatePoints(origin, destination))
                .doesNotThrowAnyException();
    }

    @DisplayName("차는 같은 위치로 이동할 수 없다.")
    @Test
    void chariotCannotMoveToSamePosition_Test() {
        // given
        Chariot chariot = new Chariot(Dynasty.HAN);

        // when // then
        assertThatCode(() -> chariot.getIntermediatePoints(Position.of(4, 8), Position.of(4, 8)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageStartingWith("[ERROR] ");
    }
}