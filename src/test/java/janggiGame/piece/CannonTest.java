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

class CannonTest {
    private static Stream<Arguments> provideRowAndColumn() {
        return Stream.of(
                Arguments.of(3, 3),
                Arguments.of(1, 1),
                Arguments.of(1, 3),
                Arguments.of(3, 1)
        );
    }

    @DisplayName("포가 목적지로 갈 수 없다면 예외를 발생 시킨다.")
    @ParameterizedTest
    @MethodSource("provideRowAndColumn")
    void cannonCanValidateDestination(int row, int column) {
        // given
        Position origin = Position.of(2, 2);
        Position destination = Position.of(row, column);
        Cannon cannon = new Cannon(Dynasty.HAN);

        // when // then
        assertThatCode(() -> cannon.getIntermediatePoints(origin, destination))
                .isInstanceOf(UnsupportedOperationException.class)
                .hasMessageStartingWith("[ERROR] ");
    }

    @DisplayName("포는 목적지로 가는 경로를 구할 수 있다.")
    @Test
    void cannonCanGetIntermediatePoints() {
        Position origin = Position.of(1, 1);
        Position destination = Position.of(1, 3);
        Cannon cannon = new Cannon(Dynasty.HAN);

        // when
        List<Position> actual = cannon.getIntermediatePoints(origin, destination);

        List<Position> expected = List.of(Position.of(1, 2));

        // then
        assertThat(actual).isEqualTo(expected);
    }

    @DisplayName("포가 자신을 제외한 다른 포를 넘으려고 할 때 예외를 발생 시킨다.")
    @Test
    void cannonCanNotJumpCannon() {
        // given
        Map<Position, Piece> intermediatePointsWithPiece = new LinkedHashMap<>();
        Cannon cannon = new Cannon(Dynasty.HAN);

        intermediatePointsWithPiece.put(Position.of(1, 2), new EmptyPiece());
        intermediatePointsWithPiece.put(Position.of(1, 3), new Cannon(Dynasty.HAN));

        // when // then
        assertThatCode(() -> cannon.validateMove(intermediatePointsWithPiece, new EmptyPiece()))
                .isInstanceOf(UnsupportedOperationException.class)
                .hasMessageStartingWith("[ERROR] ");
    }

    @DisplayName("포가 다른 포를 공격 하려고 할 때 예외를 발생 시킨다.")
    @Test
    void cannonCanNotAttackCannon() {
        // given
        Map<Position, Piece> intermediatePointsWithPiece = new LinkedHashMap<>();
        Cannon cannon = new Cannon(Dynasty.HAN);

        intermediatePointsWithPiece.put(Position.of(1, 2), new EmptyPiece());
        intermediatePointsWithPiece.put(Position.of(1, 3), new Chariot(Dynasty.HAN));

        // when // then
        assertThatCode(() -> cannon.validateMove(intermediatePointsWithPiece, new Cannon(Dynasty.CHO))).isInstanceOf(
                        UnsupportedOperationException.class)
                .hasMessageStartingWith("[ERROR] ");
    }

    @DisplayName("포는 이동 경로에 단 하나의 말만 존재 한다면 이동 가능 하다.")
    @Test
    void cannonCanMoveIfIntermediatePointsHasOnePiece() {
        // given
        Map<Position, Piece> intermediatePointsWithPiece = new LinkedHashMap<>();
        Cannon cannon = new Cannon(Dynasty.HAN);

        intermediatePointsWithPiece.put(Position.of(1, 2), new EmptyPiece());
        intermediatePointsWithPiece.put(Position.of(1, 3), new Chariot(Dynasty.HAN));

        // when // then
        assertThatCode(() -> cannon.validateMove(intermediatePointsWithPiece, new EmptyPiece()))
                .doesNotThrowAnyException();
    }
}