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

class ChariotTest {

    static Stream<Arguments> canMoveChariot() {
        return Stream.of(
                Arguments.of(Position.of(4, 3), true),
                Arguments.of(Position.of(5, 3), true),
                Arguments.of(Position.of(6, 3), true),
                Arguments.of(Position.of(7, 3), true),
                Arguments.of(Position.of(8, 3), true),
                Arguments.of(Position.of(2, 3), true),
                Arguments.of(Position.of(1, 3), true),
                Arguments.of(Position.of(0, 3), true),
                Arguments.of(Position.of(3, 2), true),
                Arguments.of(Position.of(3, 1), true),
                Arguments.of(Position.of(3, 0), true),
                Arguments.of(Position.of(3, 4), true),
                Arguments.of(Position.of(3, 5), true),
                Arguments.of(Position.of(3, 6), true),
                Arguments.of(Position.of(3, 7), true),
                Arguments.of(Position.of(3, 8), true),
                Arguments.of(Position.of(4, 4), false),
                Arguments.of(Position.of(5, 5), false)
        );
    }

    @ParameterizedTest
    @MethodSource
    @DisplayName("이동하려는 경로가 비었을 때 정상적으로 이동할 수 있다")
    void canMoveChariot(Position movePosition, boolean expected) {
        Position startPosition = Position.of(3, 3);
        Piece chariot = new Chariot(startPosition, TeamType.CHO);

        Assertions.assertThat(chariot.canMove(movePosition, List.of())).isEqualTo(expected);
    }

    @Test
    @DisplayName("이동 경로에 다른 기물이 있으면 false를 반환한다")
    void canMoveChariot2() {
        Position startPosition = Position.of(2, 2);
        Position expectedPosition = Position.of(4, 2);
        Piece chariot = new Chariot(startPosition, TeamType.HAN);

        Position otherPosition = Position.of(3, 2);
        Piece soldier = new Soldier(otherPosition, TeamType.HAN);

        boolean result = chariot.canMove(expectedPosition, List.of(soldier, chariot));

        Assertions.assertThat(result).isFalse();
    }

    @Test
    @DisplayName("도착 지점에 아군이 있으면 false를 반환한다")
    void canMoveChariot3() {
        Position startPosition = Position.of(2, 2);
        Position expectedPosition = Position.of(4, 2);
        Piece chariot = new Chariot(startPosition, TeamType.HAN);

        Position otherPosition = Position.of(4, 2);
        Piece soldier = new Soldier(otherPosition, TeamType.HAN);

        boolean result = chariot.canMove(expectedPosition, List.of(soldier, chariot));

        Assertions.assertThat(result).isFalse();
    }

    @Test
    @DisplayName("도착 지점에 적이 있으면 true를 반환한다")
    void canMoveChariot4() {
        Position startPosition = Position.of(2, 2);
        Position expectedPosition = Position.of(4, 2);
        Piece chariot = new Chariot(startPosition, TeamType.HAN);

        Position otherPosition = Position.of(4, 2);
        Piece soldier = new Soldier(otherPosition, TeamType.CHO);

        boolean result = chariot.canMove(expectedPosition, List.of(soldier, chariot));

        Assertions.assertThat(result).isTrue();
    }
}
