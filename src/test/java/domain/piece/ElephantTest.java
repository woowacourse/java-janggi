package domain.piece;

import domain.Position;
import domain.TeamType;
import java.util.List;
import java.util.stream.Stream;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class ElephantTest {

    static Stream<Arguments> canMoveElephant1() {
        return Stream.of(
                Arguments.of(Position.of(0, 1), true),
                Arguments.of(Position.of(1, 0), true),
                Arguments.of(Position.of(5, 0), true),
                Arguments.of(Position.of(6, 1), true),
                Arguments.of(Position.of(6, 5), true),
                Arguments.of(Position.of(5, 6), true),
                Arguments.of(Position.of(1, 6), true),
                Arguments.of(Position.of(0, 5), true),
                Arguments.of(Position.of(3, 2), false)
        );
    }

    @ParameterizedTest
    @MethodSource
    @DisplayName("이동하려는 경로가 비었을 때 정상적으로 이동할 수 있다")
    void canMoveElephant1(Position movePosition, boolean expected) {
        Position startPosition = Position.of(3, 3);
        Piece elephant = new Elephant(startPosition, TeamType.CHO);

        Assertions.assertThat(elephant.canMove(movePosition, List.of())).isEqualTo(expected);
    }

    @Test
    @DisplayName("이동 경로에 다른 기물이 있으면 false를 반환한다")
    void canMoveElephant2() {
        Position startPosition = Position.of(2, 2);
        Position expectedPosition = Position.of(5, 4);
        Piece elephant = new Elephant(startPosition, TeamType.HAN);

        Position otherPosition = Position.of(4, 3);
        Piece soldier = new Soldier(otherPosition, TeamType.HAN);

        boolean result = elephant.canMove(expectedPosition, List.of(soldier, elephant));

        Assertions.assertThat(result).isFalse();
    }

    @Test
    @DisplayName("도착 지점에 아군이 있으면 false를 반환한다")
    void canMoveElephant3() {
        Position startPosition = Position.of(2, 2);
        Position expectedPosition = Position.of(5, 4);
        Piece elephant = new Elephant(startPosition, TeamType.HAN);

        Position otherPosition = Position.of(5, 4);
        Piece soldier = new Soldier(otherPosition, TeamType.HAN);

        boolean result = elephant.canMove(expectedPosition, List.of(soldier, elephant));

        Assertions.assertThat(result).isFalse();
    }

    @Test
    @DisplayName("도착 지점에 적이 있으면 true를 반환한다")
    void canMoveElephant4() {
        Position startPosition = Position.of(2, 2);
        Position expectedPosition = Position.of(5, 4);
        Piece elephant = new Elephant(startPosition, TeamType.HAN);

        Position otherPosition = Position.of(5, 4);
        Piece soldier = new Soldier(otherPosition, TeamType.CHO);

        boolean result = elephant.canMove(expectedPosition, List.of(soldier, elephant));

        Assertions.assertThat(result).isTrue();
    }
}
